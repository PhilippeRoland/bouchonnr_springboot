package com.philippe.bouchonnr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.philippe.bouchonnr.entity.Wine;
import com.philippe.bouchonnr.repository.WineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Controller
@RequestMapping("/wines")
public class WineController {

    private static final ObjectMapper wineMapper = new ObjectMapper();

    Logger logger = LoggerFactory.getLogger(WineController.class);

    @Autowired
    WineRepository wineRepository;

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createWine(@RequestBody Wine wine) {
        logger.info(String.format("Creating wine: " + wine.toString()));
        try {
            wineRepository.save(wine);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(wine.getId())
                    .toUri();
            //Override default object mapping to add avg rating
            Map<String, Object> map = wineMapper.convertValue(wine, Map.class);
            map.put("avgRating", wine.getAvgRating());
            return ResponseEntity.created(location).body(map);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
