package softuni.exam.instagraphlite.models.entity;

import softuni.exam.instagraphlite.models.entity.Picture;

import javax.persistence.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @OneToOne(targetEntity = Picture.class)
    @JoinColumn(name = "profile_picture_id", nullable = false)
    private Picture profilePicture;
    @OneToMany(mappedBy = "user", targetEntity = Post.class, fetch = FetchType.EAGER)
    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Picture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("User: %s", username)).append(System.lineSeparator());
        sb.append(String.format("Post count: %s", posts.size())).append(System.lineSeparator());
        posts.sort((Comparator.comparing(o -> o.getPicture().getSize())));
        posts.forEach(p -> sb.append(p.toString()));
        return sb.toString().trim();
    }
}
