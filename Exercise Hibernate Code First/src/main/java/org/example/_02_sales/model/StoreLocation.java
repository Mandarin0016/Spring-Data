package org.example._02_sales.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "store_locations")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StoreLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "location_name")
    private String name;
    @OneToMany(mappedBy = "storeLocation", targetEntity = Sale.class, cascade = CascadeType.ALL)
    private Set<Sale> sales;
}
