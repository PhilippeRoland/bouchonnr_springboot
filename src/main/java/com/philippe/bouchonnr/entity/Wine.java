package com.philippe.bouchonnr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@Table(name = "Wine")
@EntityListeners(AuditingEntityListener.class) //audits database changes, used to enable @CreatedDate on creationDate
@JsonIgnoreProperties(value = { "ratings" })
public class Wine {

    @Id
    @Getter
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Exclude
    private Long id;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Setter
    @OneToMany(targetEntity=Rating.class, orphanRemoval = true,
            fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "wine")
    @Exclude //Excluded from ToString to avoid wine->rating->wine->... stack overflows
    private List<Rating> ratings = new ArrayList<>();

    @JsonIgnore
    public List<Rating> getRatings() { //getter needs to be explicitly declared to be JsonIgnorable
        return ratings;
    }

    public double getAvgRating() {
        return ratings.stream().
                map(Rating::getScore).
                mapToInt(Integer::intValue).
                average().orElse(0);
    }
}
