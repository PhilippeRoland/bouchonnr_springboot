package com.philippe.bouchonnr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.philippe.bouchonnr.entity.Wine;
import com.philippe.bouchonnr.repository.WineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@Controller
@RequestMapping("/listings")
public class ListingController {

    private static final ObjectMapper wineMapper = new ObjectMapper();

    Logger logger = LoggerFactory.getLogger(ListingController.class);

    @Autowired
    WineRepository wineRepository;

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createWine(@RequestBody Wine wine) {
        //TODO
        return null;
    }

}
