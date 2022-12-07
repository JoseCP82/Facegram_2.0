package com.facegram.utils.message;

public abstract class Message {

    /**
     * Atributo de clase
     */
    protected String message;

    /**
     * Constructor con parametros
     * @param message Mensaje a mostrar
     */
    public Message(String message) {
        this.message = message;
    }

    /**
     * Constructor por defecto
     */
    public Message() {
        this("Contenido no disponible.");
    }

    /**
     * Metodo abstrato en el que se mostrará un mensaje
     */
    abstract public void showMessage();
}
