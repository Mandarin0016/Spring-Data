package com.example.football.models.dto;

import com.example.football.models.enums.Position;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerSeedDTO {
    @XmlElement(name = "first-name")
    @Size(min = 3)
    private String firstName;
    @XmlElement(name = "last-name")
    @Size(min = 3)
    private String lastName;
    @XmlElement(name = "email")
    @Email
    private String email;
    @XmlElement(name = "birth-date")
    private String birthDate;
    @XmlElement(name = "position")
    @NotNull
    private Position position;
    @XmlElement(name = "town")
    private TownNameDTO townName;
    @XmlElement(name = "team")
    private TeamNameDTO teamName;
    @XmlElement(name = "stat")
    private StatIdDTO statId;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Position getPosition() {
        return position;
    }

    public TownNameDTO getTownName() {
        return townName;
    }

    public TeamNameDTO getTeamName() {
        return teamName;
    }

    public StatIdDTO getStatId() {
        return statId;
    }
}
