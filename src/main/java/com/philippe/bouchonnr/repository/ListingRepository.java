package com.philippe.bouchonnr.repository;

import com.philippe.bouchonnr.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {

}
