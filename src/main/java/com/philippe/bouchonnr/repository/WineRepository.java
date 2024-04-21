package com.philippe.bouchonnr.repository;

import com.philippe.bouchonnr.entity.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineRepository extends JpaRepository<Wine, Long> {

}
