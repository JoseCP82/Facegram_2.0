package com.facegram.model.dataobject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "POST")
public class Post implements Serializable {

    /**
     * Atributos de clase
     */
    private static final long serialVersionUID = 42L;
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "TEXT")
    private String text;
    @Column(name = "DATE")
    private Date date;
    @Column(name = "EDITDATE")
    private Date editDate;
    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private User owner;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    /**
     * Constructor parametrizado
     * @param id Identificación asignada al post
     * @param text Texto que tiene el post
     * @param date Fecha de creación del post
     * @param editDate Fecha de actualización del post
     */
    public Post(int id, String text, Date date, Date editDate) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.editDate = editDate;
    }

    /**
     * Constructor por defecto
     */
    public Post() {
        this(-1,"",null,null);
    }

    /**
     * Obtiene el id del post
     * @return Id del post
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
     * Obtiene el text o mensaje del post
     * @return Texto del posteado
     */
    public String getText() {
        return text;
    }

    /**
     * Setea el texto a postear
     * @param text Texto del post
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Obtiene la fecha de creacion del post
     * @return Fecha del post
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setea la fecha de creación del post
     * @param date Fecha a postear
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Obtiene la fecha en que se actualizó el post
     * @return Fecha actualizada del post
     */
    public Date getEditDate() {
        return editDate;
    }

    /**
     * Setea la fecha de actualización del post
     * @param editDate Fecha a setear
     */
    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    /**
     * Obtiene el usuario que creó el post
     * @return Usuario
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Setea usuario que crea el post
     * @param owner Usuario
     */
    public void setOwner(User owner) {
        if(owner.equals(this.owner)) return;
        this.owner = owner;
        this.owner.addPosts(this);
    }

    /**
     * Obtiene la lista de comentarios
     * @return Lista de comentarios
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Setea la lista de comentarios
     * @param comments Lista de comentarios
     */
    public void setComments(List<Comment> comments) {
        if(comments==null) return;
        for(Comment comment : comments) {
            this.addComment(comment);
        }
    }

    /**
     * Añade al array de Comments un nuevo comentario en el caso de que esté vacio
     * @param comment Comentairio a añadir
     */
    public void addComment(Comment comment) {
        if(comment==null) return;
        if(this.comments==null) this.comments = new ArrayList<>();
        if(!this.comments.contains(comment)) {
            comment.setPost(this);
            this.comments.add(comment);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && Objects.equals(text, post.text) &&
                Objects.equals(date, post.date) &&
                Objects.equals(editDate, post.editDate) &&
                Objects.equals(owner, post.owner) &&
                Objects.equals(comments, post.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, date, editDate, owner, comments);
    }
}
