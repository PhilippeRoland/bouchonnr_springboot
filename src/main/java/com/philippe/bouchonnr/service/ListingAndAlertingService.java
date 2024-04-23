package com.philippe.bouchonnr.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.philippe.bouchonnr.entity.Alert;
import com.philippe.bouchonnr.entity.Listing;
import com.philippe.bouchonnr.entity.Wine;
import com.philippe.bouchonnr.repository.AlertRepository;
import com.philippe.bouchonnr.repository.ListingRepository;
import com.philippe.bouchonnr.repository.WineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class ListingAndAlertingService {

    private static final ObjectMapper listingMapper = new ObjectMapper();

    Logger logger = LoggerFactory.getLogger(ListingAndAlertingService.class);

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    AlertRepository alertRepository;

    @Autowired
    WineRepository wineRepository;

    public ResponseEntity<Object> createListingAndAlert(Listing listing) {
        Listing persistedListing = listingRepository.save(listing);

        try {
            Long wineId = listing.getWine().getId();
            Wine wine = wineRepository.findById(listing.getWine().getId()).orElseThrow(
                    () -> new Exception(String.format("Could not find wine with id %d ", wineId)));
            persistedListing.setWine(wine); //Add complete object to set in the response body and alert
            checkAndSendAlerts(persistedListing);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(persistedListing.getId())
                .toUri();

        return ResponseEntity.created(location).body(persistedListing);
    }

    private void checkAndSendAlerts(Listing persistedListing) {
        double price = persistedListing.getPrice();
        List<Alert> matchingAlerts = alertRepository.findByMinPriceLessThanAndMaxPriceGreaterThan(price, price);
        matchingAlerts.forEach(alert -> {
            try {
                triggerNotification(alert, persistedListing);
            } catch (IOException | InterruptedException e) {
                logger.error("An error occurred while triggering notifications", e);
            }
        });
    }

    private void triggerNotification(Alert alert, Listing listing) throws IOException, InterruptedException {
        logger.info("Found matching alert with id {}, notifying ", alert.getId());
        String requestBody = listingMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(listing);
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(alert.getNotificationUrl()))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            logger.info(response.toString());
        }
    }


}
