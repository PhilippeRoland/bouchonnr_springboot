package com.philippe.bouchonnr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;

@ToString
@Entity
public class Listing {

    @Id
    @Getter
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ToString.Exclude
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wine_id")
    @Getter
    @Setter
    private Wine wine;

    @Column(name = "seller")
    @Getter
    @Setter
    private String seller;

    @Column(name = "price")
    @Getter
    @Setter
    @Min(0)
    private double price;
}
