package com.philippe.bouchonnr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.hateoas.RepresentationModel;
@ToString
@Entity
public class Rating extends RepresentationModel<Rating> {

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

    @Column(name = "score")
    @Getter
    @Setter
    @Min(0)
    @Max(5)
    private int score;
}
