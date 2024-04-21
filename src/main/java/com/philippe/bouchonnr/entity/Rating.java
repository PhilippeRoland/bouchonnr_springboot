package com.philippe.bouchonnr.entity;

import jakarta.persistence.*;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
@Entity
public class Rating extends RepresentationModel<Rating> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ToString.Exclude
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wine_id")
    private Wine wine;
}
