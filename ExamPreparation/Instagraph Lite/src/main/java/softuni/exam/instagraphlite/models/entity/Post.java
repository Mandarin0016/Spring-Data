package softuni.exam.instagraphlite.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caption;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Picture picture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("==Post Details:").append(System.lineSeparator());
        sb.append(String.format("----Caption: %s", caption)).append(System.lineSeparator());
        sb.append(String.format("----Picture Size: %s", picture.getSize())).append(System.lineSeparator());
        return sb.toString();
    }
}
