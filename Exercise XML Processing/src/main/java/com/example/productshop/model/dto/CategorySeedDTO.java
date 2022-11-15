package com.example.productshop.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class CategorySeedDTO {
    @XmlElement(name = "name")
    @Size(min = 3, max = 15)
    private String name;
}
