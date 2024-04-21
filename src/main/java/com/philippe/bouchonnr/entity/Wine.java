package com.philippe.bouchonnr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.Set;

@ToString
@Entity
//TODO : clean up annotations, indexes, entitylisteners
@Table(name = "Wine")/*, indexes = {
        @Index(columnList = "author"),
        @Index(columnList = "creationDate")})*/
@EntityListeners(AuditingEntityListener.class) //audits database changes, used to enable @CreatedDate on creationDate
public class Wine {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    @ToString.Exclude
    private Long id;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @OneToMany(targetEntity=Rating.class, orphanRemoval = true,
            fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "wine")
    private List<Rating> ratings;
}
