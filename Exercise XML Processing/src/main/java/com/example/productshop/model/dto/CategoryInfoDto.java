package com.example.productshop.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@Data
@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class CategoryInfoDto {
    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "products-count")
    private Integer productsCount;
    @XmlElement(name = "average-price")
    private BigDecimal averagePrice;
    @XmlElement(name = "total-revenue")
    private BigDecimal totalRevenue;
}
