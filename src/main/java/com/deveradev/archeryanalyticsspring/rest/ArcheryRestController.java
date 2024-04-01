package com.deveradev.archeryanalyticsspring.rest;

import com.deveradev.archeryanalyticsspring.entity.Archer;
import com.deveradev.archeryanalyticsspring.service.ArcheryRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/archery")
public class ArcheryRestController {
    private ArcheryRestService archeryRestService;

    @Autowired
    public ArcheryRestController(ArcheryRestService archeryRestService) {
        this.archeryRestService = archeryRestService;
    }

    @GetMapping("/archers")
    public List<Archer> findAllArchers() {
        List<Archer> archers = archeryRestService.findAllArchers();

        System.out.println("Archers: " + archers.size());
        return archers;
    }

    @GetMapping("/archer/{archerId}")
    public Archer findArcherById(@PathVariable int archerId) {
        Optional<Archer> archer = archeryRestService.findArcherById(archerId);

        if (archer.isEmpty()) {
            throw new ArcherNotFoundException("Archer id not found: " + archerId);
        }
        return archer.get();
    }
}
