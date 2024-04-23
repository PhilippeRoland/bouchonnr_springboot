package com.philippe.bouchonnr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.philippe.bouchonnr.entity.Listing;
import com.philippe.bouchonnr.repository.ListingRepository;
import com.philippe.bouchonnr.service.ListingAndAlertingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/listings")
public class ListingController {

    private static final ObjectMapper wineMapper = new ObjectMapper();

    //TODO find a better way. See https://stackoverflow.com/questions/39157370
    public static final String LONG_MAX_VALUE = "" + Long.MAX_VALUE;

    Logger logger = LoggerFactory.getLogger(ListingController.class);

    @Autowired
    ListingAndAlertingService listingAlertingService;

    @Autowired
    ListingRepository listingRepository;

    @PostMapping("")
    public ResponseEntity<Object> createListing(@RequestBody @Valid Listing listing) {
        logger.info(String.format("Creating listing: " + listing.toString()));
        try {
            return listingAlertingService.createListingAndAlert(listing);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Object> findAllSortedByRating(
            @RequestParam(defaultValue = "0", required = false) double minprice,
            @RequestParam(defaultValue = LONG_MAX_VALUE, required = false) double maxprice) {
        logger.info(String.format("Searching for listings between prices %f and %f: " , minprice, maxprice));
        List<Listing> matchingListings = listingRepository.findByPriceBetween(minprice, maxprice);
        //TODO perform sorting in repository/JPA/SQL logic for increased performance
        Comparator<Listing> comparator = Comparator.comparing(listing -> listing.getWine().getAvgRating());
        matchingListings.sort(comparator.reversed());
        return ResponseEntity.ok().body(matchingListings);
    }

    @GetMapping("/history")
    public ResponseEntity<Object> findWineHistory(@RequestParam(required = true) String wineId) {
        logger.info(String.format("Searching for listings for wine ID %s: " , wineId));
        List<Listing> matchingListings = listingRepository.findWineHistory(wineId);
        //TODO perform sorting in repository/JPA/SQL logic for increased performance
        Comparator<Listing> comparator = Comparator.comparing(Listing::getCreationDate);
        matchingListings.sort(comparator.reversed());
        return ResponseEntity.ok().body(matchingListings);
    }

}
