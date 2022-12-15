package com.facegram.model.dataobject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table (name = "COMMENT")
public class Comment implements Serializable {

    private static long serialVersionUID = 1L;

    @Id
    @Column (name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    //@OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "id_user")
    private User user;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "id_post")
    private Post post;
    @Column (name = "TEXT")
    private String text;
    @Column (name = "DATE")
    private Date date;

    /**
     * Constructor parametrizado
     * @param id Identificacion asignada al comentario
     * @param text Texto que contiene el comentario
     * @param date Fecha de publicación del comentario
     */
    public Comment(String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    /**
     * Constructor por defecto
     */
    public Comment() {this("",null);
    }

    public Comment(int anInt) {

    }


    /**
     * Obtiene el id del comentario
     * @return Id del comentario
     */
    public int getId() {
        return id;
    }

    /**
     * Setea el id del post
     * @param id Id a setear
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el Usuario del comentario
     * @return el Usuario del comentario
     */
    public User getUser() {
        return user;
    }

    /**
     * Setea el Usuario del comentario
     * @param user Usuario a setear
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtiene el Post del comentario
     * @return el Post del comentario
     */
    public Post getPost() {
        return post;
    }

    /**
     * Setea el Post del comentario
     * @param post Post a setear
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Obtiene el Texto del comentario
     * @return el Texto del comentario
     */
    public String getText() {
        return text;
    }

    /**
     * Setea el Texto del comentario
     * @param text Texto a setear
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Obtiene la Fecha del comentario
     * @return la Fecha del comentario
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setea la Fecha del comentario
     * @param date Fecha a setear
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
