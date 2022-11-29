package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostSeedDTO {
    @XmlElement(name = "caption")
    @Size(min = 21)
    @NotNull
    private String caption;
    @XmlElement(name = "user")
    @NotNull
    private UsernameDTO user;
    @XmlElement(name = "picture")
    @NotNull
    private PicturePathDTO picture;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UsernameDTO getUser() {
        return user;
    }

    public void setUser(UsernameDTO user) {
        this.user = user;
    }

    public PicturePathDTO getPicture() {
        return picture;
    }

    public void setPicture(PicturePathDTO picture) {
        this.picture = picture;
    }
}
