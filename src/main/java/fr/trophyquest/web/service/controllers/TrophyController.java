package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.TrophyDTO;
import fr.trophyquest.web.service.service.TrophyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/trophies")
@CrossOrigin(origins = "*")
public class TrophyController {

    private final TrophyService trophyService;

    public TrophyController(TrophyService trophyService) {
        this.trophyService = trophyService;
    }

    @GetMapping("/{id}")
    public TrophyDTO getTrophyById(@PathVariable UUID id) {
        return trophyService.getTrophyById(id);
    }

}
