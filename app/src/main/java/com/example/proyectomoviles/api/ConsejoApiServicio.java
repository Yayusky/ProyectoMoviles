package com.example.proyectomoviles.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ConsejoApiServicio {
    @GET("consejos/random")
    Call<ConsejoRespuesta> obtenerConsejoAleatorio();



    public static class ConsejoRespuesta {
        private String id;
        private String consejo;

        public String getId() { return id; }
        public String getConsejo() { return consejo;
}
    }

}




