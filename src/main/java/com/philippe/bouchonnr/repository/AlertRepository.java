package com.philippe.bouchonnr.repository;

import com.philippe.bouchonnr.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {

}
