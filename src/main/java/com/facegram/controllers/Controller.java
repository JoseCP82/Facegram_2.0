package com.facegram.controllers;

import com.facegram.model.dataobject.User;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public abstract class Controller {

    /**
     * Atributos de clase
     */
    protected static User permanentUser;
    protected static BorderPane borderPane;
    protected static ScrollPane scrollPane;
    protected static int permanentIdPost;
}
