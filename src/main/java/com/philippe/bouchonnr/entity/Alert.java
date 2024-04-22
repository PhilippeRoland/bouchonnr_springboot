package com.philippe.bouchonnr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
public class Alert {

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

    @Column(name = "minPrice")
    @Getter
    @Setter
    private double minPrice;

    @Column(name = "maxPrice")
    @Getter
    @Setter
    private double maxPrice;

    @Column(name = "notification_url")
    @Getter
    @Setter
    private String notificationUrl;
}
