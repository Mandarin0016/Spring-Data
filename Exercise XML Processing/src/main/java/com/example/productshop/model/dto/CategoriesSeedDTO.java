package com.example.productshop.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class CategoriesSeedDTO {
    @XmlElement(name = "category")
    private List<CategorySeedDTO> categories;
}
