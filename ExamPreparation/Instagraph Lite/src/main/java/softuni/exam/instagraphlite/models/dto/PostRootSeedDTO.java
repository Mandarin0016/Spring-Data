package softuni.exam.instagraphlite.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "posts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostRootSeedDTO {
    @XmlElement(name = "post")
    private List<PostSeedDTO> posts;

    public List<PostSeedDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostSeedDTO> posts) {
        this.posts = posts;
    }
}
