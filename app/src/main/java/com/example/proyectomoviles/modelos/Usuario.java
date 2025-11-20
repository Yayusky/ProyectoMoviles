package com.example.proyectomoviles.modelos;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String id;
    private String name;
    private String email;
    private String pa$$;
    private String created_at;

    public Usuario() {}

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id;}

    public String getName() { return name;}
    public void setName(String name) { this.name = name;}

    public String getEmail() { return email;}
    public void setEmail(String email) { this.email = email;}

    public String getPa$$() { return pa$$;}
    public void setPa$$(String pa$$) { this.pa$$ = pa$$;}

    public String getCreated_at() { return created_at;}
    public void setCreated_at(String created_at) { this.created_at = created_at;}
}
