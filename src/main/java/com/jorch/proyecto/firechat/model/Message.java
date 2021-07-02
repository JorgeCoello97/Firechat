package com.jorch.proyecto.firechat.model;

/**
 * Created by JORCH on 18/03/2017.
 */

public class Message {
    private String fecha;
    private String username;
    private String message;

    public Message() {
    }

    public Message(String fecha, String username, String message) {
        this.fecha = fecha;
        this.username = username;
        this.message = message;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
