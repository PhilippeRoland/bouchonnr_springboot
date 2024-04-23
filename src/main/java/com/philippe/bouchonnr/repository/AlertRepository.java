package com.philippe.bouchonnr.repository;

import com.philippe.bouchonnr.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long>{

    //TODO Little quirk of JPA Derived Query means price must be set twice in parameters. A bit ugly.
    List<Alert> findByMinPriceLessThanAndMaxPriceGreaterThan(double price, double priceAgain);

}
