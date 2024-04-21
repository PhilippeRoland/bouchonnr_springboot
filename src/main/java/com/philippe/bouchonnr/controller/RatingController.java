package com.philippe.bouchonnr.controller;

import com.philippe.bouchonnr.entity.Rating;
import com.philippe.bouchonnr.entity.Wine;
import com.philippe.bouchonnr.repository.RatingRepository;
import com.philippe.bouchonnr.repository.WineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Controller
@RequestMapping("/ratings")
public class RatingController {

    Logger logger = LoggerFactory.getLogger(RatingController.class);

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    WineRepository wineRepository;

    @PostMapping("")
    public ResponseEntity<Object> createRating(@Valid @RequestBody Rating rating) {
        //TODO @Valid is not working somehow; for now we'll trust the request, careful!
        logger.info(String.format("Creating rating: " + rating.toString()));
        try {
            Long wineId = rating.getWine().getId();
            Wine wine = wineRepository.findById(rating.getWine().getId()).orElseThrow(
                    () -> new Exception(String.format("Could not find wine with id %d ", wineId)));
            ratingRepository.save(rating);
            rating.setWine(wine); //Add complete object to set in the response body
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(rating.getId())
                    .toUri();
            return ResponseEntity.created(location).body(rating);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
