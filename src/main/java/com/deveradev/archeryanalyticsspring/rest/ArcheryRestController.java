package com.deveradev.archeryanalyticsspring.rest;

import com.deveradev.archeryanalyticsspring.entity.Archer;
import com.deveradev.archeryanalyticsspring.entity.Round;
import com.deveradev.archeryanalyticsspring.service.ArcheryRestService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ArcheryRestController {
    private ArcheryRestService archeryRestService;

    @Autowired
    public ArcheryRestController(ArcheryRestService archeryRestService) {
        this.archeryRestService = archeryRestService;
    }

    // Archers

    @GetMapping("/archers")
    public List<Archer> findAllArchers() {
        List<Archer> archers = archeryRestService.findAllArchers();

        System.out.println("Archers: " + archers.size());
        return archers;
    }

    @GetMapping("/archers/{id}")
    public Archer findArcherById(@PathVariable int id) {
        return archeryRestService.findArcherById(id);
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

        return new ResponseEntity<>("Archer #" + id + " successfully deleted", HttpStatus.OK);
    }
}
