package com.example.football.models.dto;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stat")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatSeedDTO {
    @Positive
    @XmlElement(name = "passing")
    private Float passing;
    @Positive
    @XmlElement(name = "shooting")
    private Float shooting;
    @Positive
    @XmlElement(name = "endurance")
    private Float endurance;

    public Float getPassing() {
        return passing;
    }

    public Float getShooting() {
        return shooting;
    }

    public Float getEndurance() {
        return endurance;
    }
}
