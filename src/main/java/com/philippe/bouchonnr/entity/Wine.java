package com.philippe.bouchonnr.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
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
    @Getter
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
    @JsonIgnore
    private List<Rating> ratings = new ArrayList<>();

    public double getAvgRating() {
        return ratings.stream().
                map(Rating::getScore).
                mapToInt(Integer::intValue).
                average().orElse(0);
    }
}
