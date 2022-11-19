package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "team")
public class TeamNameDTO {
    @XmlElement(name = "name")
    private String name;

    public String getName() {
        return name;
    }
}
