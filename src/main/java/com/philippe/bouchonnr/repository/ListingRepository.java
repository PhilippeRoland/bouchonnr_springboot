package com.philippe.bouchonnr.repository;

import com.philippe.bouchonnr.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    //@Query(value="SELECT * FROM bouchonnr.listing l JOIN bouchonnr.wine w ON l.wine_id=w.id WHERE l.price >= :minPrice AND l.price <= :maxPrice", nativeQuery = true)
    //@Query(value="SELECT l FROM Listing l JOIN Wine w ON l.wine.id=w.id WHERE l.price >= :minPrice AND l.price <= :maxPrice")
    //List<Listing> findMatchingListing(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice); //has to be list of objects due to being native query

    List<Listing> findByPriceBetween(double minPrice, double maxPrice);

    @Query(value="SELECT l FROM Listing l WHERE l.wine.id=:wineId")
    List<Listing> findWineHistory(@Param("wineId") String wineId);
}


