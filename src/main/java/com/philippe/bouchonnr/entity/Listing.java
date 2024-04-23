package com.philippe.bouchonnr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.validation.constraints.Min;
import java.util.Date;

@ToString
@Entity
@Table(name = "Listing")
@EntityListeners(AuditingEntityListener.class) //audits database changes, used to enable @CreatedDate on creationDate
public class Listing {

    @Id
    @Getter
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ToString.Exclude
    private Long id;

    @ManyToOne()
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

    //UTC timezone, see application.properties; no setter, as this will be automatically filled in when creating resource
    @Getter
    @CreatedDate
    @Column(name = "creationDate")
    private Date creationDate;
}
