package com.philippe.bouchonnr.repository;

import com.philippe.bouchonnr.entity.Rating;
import com.philippe.bouchonnr.entity.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {

}
