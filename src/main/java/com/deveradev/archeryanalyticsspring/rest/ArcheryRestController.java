package com.deveradev.archeryanalyticsspring.rest;

import com.deveradev.archeryanalyticsspring.entity.Archer;
import com.deveradev.archeryanalyticsspring.entity.Round;
import com.deveradev.archeryanalyticsspring.entity.RoundDTO;
import com.deveradev.archeryanalyticsspring.service.ArcheryRestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArcheryRestController {
    private ArcheryRestService archeryRestService;

    @Autowired
    public ArcheryRestController(ArcheryRestService archeryRestService) {
        this.archeryRestService = archeryRestService;
    }

    //
    // Archers
    //

    @GetMapping("/archers")
    public List<Archer> findAllArchers() {
        List<Archer> archers = archeryRestService.findAllArchers();

        return archers;
    }

    @GetMapping("/archers/{id}")
    public ResponseEntity<Archer> findArcherById(@PathVariable int id) {
        Archer archer = archeryRestService.findArcherById(id);

        return new ResponseEntity<>(archer, HttpStatus.OK);
    }

    @PostMapping("/archers")
    public Archer addArcher(@RequestBody Archer archer) {
        archer.id = 0;
        if (archer.name == null || archer.name.isBlank()) {
            throw new BadRequestException("Missing fields: name");
        }
        return archeryRestService.addArcher(archer);
    }

    @DeleteMapping("/archers/{id}")
    public ResponseEntity<Object> deleteArcher(@PathVariable int id) {
        archeryRestService.deleteArcher(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //
    // Rounds
    //

    @PostMapping("/rounds")
    public Round addRound(@RequestBody @Valid RoundDTO roundDTO) {
        try {
            String format = "yyyy-MM-dd hh:mma";
            Round round = new Round();

            round.id = 0;
            round.date = new SimpleDateFormat(format).parse(roundDTO.date);
            round.archer = archeryRestService.findArcherById(roundDTO.archerId);
            return archeryRestService.addRound(round);
        } catch(ParseException e) {
            throw new BadRequestException("Invalid date format: " + e.getMessage());
        }
    }

    @DeleteMapping("/rounds/{roundId}")
    public ResponseEntity<Object> deleteRound(@PathVariable int roundId) {
        archeryRestService.deleteRound(roundId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/rounds/archer/{archerId}")
    public List<Round> findAllRoundsForArcherId(@PathVariable int archerId) {
        return archeryRestService.findAllRoundsForArcherId(archerId);
    }

//    @PostMapping("/rounds/{roundId}/ends")
//    public End addEndToRound(@RequestBody End end) {
//
//    }
}
