package com.philippe.bouchonnr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;

@ToString
@Entity
@Table(name="Alert", indexes = {
        @Index(columnList = "minPrice, maxPrice")
})
public class Alert{

    @Id
    @Getter
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ToString.Exclude
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wine_id")
    @Getter
    @Setter
    private Wine wine;

    @Column(name = "minPrice")
    @Getter
    @Setter
    @Min(0)
    private double minPrice;

    @Column(name = "maxPrice")
    @Getter
    @Setter
    @Min(0) //TODO: should be equal or higher than minPrice. Possibly needs a custom annotation or separate validation
    private double maxPrice;

    @Column(name = "notification_url")
    @Getter
    @Setter
    private String notificationUrl;
}
