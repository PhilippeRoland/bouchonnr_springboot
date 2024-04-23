package com.philippe.bouchonnr.controller;

import com.philippe.bouchonnr.entity.Alert;
import com.philippe.bouchonnr.entity.Wine;
import com.philippe.bouchonnr.repository.AlertRepository;
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
@RequestMapping("/alerts")
public class AlertController {

    Logger logger = LoggerFactory.getLogger(AlertController.class);

    @Autowired
    WineRepository wineRepository;

    @Autowired
    AlertRepository alertRepository;

    @PostMapping("")
    public ResponseEntity<Object> createAlert(@Valid @RequestBody Alert alert) {
        //TODO @Valid is not working somehow; for now we'll trust the request, careful!
        logger.info(String.format("Creating alert: " + alert.toString()));
        try {
            Long wineId = alert.getWine().getId();
            Wine wine = wineRepository.findById(alert.getWine().getId()).orElseThrow(
                    () -> new Exception(String.format("Could not find wine with id %d ", wineId)));
            alertRepository.save(alert);
            alert.setWine(wine); //Add complete object to set in the response body
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(alert.getId())
                    .toUri();
            return ResponseEntity.created(location).body(alert);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
