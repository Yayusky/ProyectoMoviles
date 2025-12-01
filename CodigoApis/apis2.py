from fastapi import FastAPI, HTTPException, Body
import pymongo
from fastapi.responses import JSONResponse
from datetime import datetime
from bson import ObjectId
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()
cliente = pymongo.MongoClient("mongodb://127.0.0.1:27017/")
db = cliente["PlantArte"]
coleccionPlantas = db["plantas"]
coleccionPlagas = db["plagas"]
usersColeccion = db["usuarios"]
huertosColeccion = db["huertos"]
consejosColeccion = db["consejos"]

def fix_id(doc):
    doc['_id'] = str(doc['_id'])
    return doc

@app.get("/consejos/random")
def obtener_consejo_aleatorio():
    try:
        pipeline = [
            {"$sample": {"size": 1}}
        ]
        docs = list(consejosColeccion.aggregate(pipeline))
        if not docs:
            raise HTTPException(status_code=404, detail="No hay consejos registrados")

        doc = docs[0]
        consejo = {
            "id": str(doc.get("_id")),
            "consejo": doc.get("consejo", "")
        }
        return JSONResponse(content=consejo)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    

@app.get("/getPlantas")
def get_plantas():
    documentos = list(coleccionPlantas.find())
    documentos = [fix_id(doc) for doc in documentos]
    return JSONResponse(content=documentos)

@app.get("/getPlagas")
def get_plagas():
    documentos = list(coleccionPlagas.find())
    documentos = [fix_id(doc) for doc in documentos]
    return JSONResponse(content=documentos)

@app.post("/registrarUsuario")
def register_user(user: dict = Body(...)):
    if usersColeccion.find_one({"email": user.get("email")}):
        return JSONResponse(status_code=409, content={"message": "El correo ya está registrado"})
    user["created_at"] = datetime.utcnow().isoformat()
    usersColeccion.insert_one(user)
    user["_id"] = str(user["_id"]) if "_id" in user else ""
    return JSONResponse(content={"message": "Usuario registrado correctamente", "user": user})

@app.post("/loginUsuario")
def login_user(data: dict = Body(...)):
    email = data.get("email")
    password = data.get("pa$$")
    user = usersColeccion.find_one({"email": email, "pa$$": password})
    if user:
        user["_id"] = str(user["_id"])
        return JSONResponse(content={"message": "Login correcto", "user": user})
    else:
        return JSONResponse(status_code=401, content={"message": "Credenciales incorrectas"})

@app.get("/plantas-por-mes")
def obtener_plantas_por_mes(mes: int):
    documentos = list(coleccionPlantas.find({"mesesSiembra": mes}))
    documentos = [fix_id(doc) for doc in documentos]
    return JSONResponse(content=documentos)

@app.post("/registrarHuerto")
def registrar_huerto(huerto: dict = Body(...)):
    # Validar que llegue usuario_id
    if "usuario_id" not in huerto:
        return JSONResponse(status_code=400, content={"message": "usuario_id es requerido"})
    huerto["fechaRegistro"] = huerto.get("fechaRegistro", datetime.utcnow().isoformat())
    res = huertosColeccion.insert_one(huerto)
    huerto["_id"] = str(res.inserted_id)
    print("RECIBIDO:", huerto)
    return JSONResponse(content={"mensaje": "Huerto registrado correctamente", "huerto": huerto})

@app.get("/huertos/{usuario_id}")
def obtener_huertos_por_usuario(usuario_id: str):
    huertos = list(huertosColeccion.find({"usuario_id": usuario_id}))
    huertos = [fix_id(doc) for doc in huertos]
    return JSONResponse(content=huertos)

@app.delete("/huertos/{huerto_id}")
def eliminar_huerto(huerto_id: str):
    try:
        res = huertosColeccion.delete_one({"_id": ObjectId(huerto_id)})
        if res.deleted_count == 0:
            raise HTTPException(status_code=404, detail="Huerto no encontrado")
        return {"mensaje": "Huerto eliminado correctamente"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@app.put("/actualizarUsuario/{usuario_id}")
def update_user(usuario_id: str, data: dict = Body(...)):
    nuevo_login = data.get("login")      # puede ser email o username
    nueva_pass = data.get("pa$$")

    if not nuevo_login or not nueva_pass:
        return JSONResponse(
            status_code=400,
            content={"mensaje": "login y pa$$ son requeridos"}
        )

    user = usersColeccion.find_one({"_id": ObjectId(usuario_id)})
    if not user:
        return JSONResponse(
            status_code=404,
            content={"mensaje": "Usuario no encontrado"}
        )

    existe = usersColeccion.find_one({
        "email": nuevo_login,
        "_id": {"$ne": ObjectId(usuario_id)}
    })
    if existe:
        return JSONResponse(
            status_code=409,
            content={"mensaje": "El correo/usuario ya está en uso"}
        )

    usersColeccion.update_one(
        {"_id": ObjectId(usuario_id)},
        {"$set": {"email": nuevo_login, "pa$$": nueva_pass}}
    )

    user_actualizado = usersColeccion.find_one({"_id": ObjectId(usuario_id)})
    user_actualizado["_id"] = str(user_actualizado["_id"])

    return JSONResponse(content={
        "mensaje": "Usuario actualizado correctamente",
        "user": user_actualizado
    })


@app.get("/estadisticas/tipos-cultivo/{usuario_id}")
def obtener_tipos_cultivo(usuario_id: str):
    try:
        pipeline = [
            {"$match": {"usuario_id": usuario_id}},
            {"$unwind": "$plantasSembradas"},
            {
                "$group": {
                    "_id": "$plantasSembradas.tipo", 
                    "cantidad": {"$sum": 1}
                }
            },
            {"$sort": {"cantidad": -1}}
        ]

        resultados = list(huertosColeccion.aggregate(pipeline))

        tipos = []
        total_plantas = 0
        for r in resultados:
            tipo = r["_id"] if r["_id"] is not None else "Desconocido"
            cantidad = r["cantidad"]
            total_plantas += cantidad
            tipos.append({
                "tipo": tipo,
                "cantidad": cantidad
            })

        respuesta = {
            "usuario_id": usuario_id,
            "total_plantas": total_plantas,
            "tipos": tipos
        }
        return JSONResponse(content=respuesta)

    except Exception as e:
        print("Error en agregación:", e)
        return JSONResponse(
            status_code=500,
            content={"message": "Error al calcular estadísticas de tipos de cultivo"})
    
    
    

    

    