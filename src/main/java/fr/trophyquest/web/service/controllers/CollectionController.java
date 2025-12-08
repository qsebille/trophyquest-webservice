package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.CollectionDTO;
import fr.trophyquest.web.service.service.CollectionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/collection")
@CrossOrigin(origins = "*")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }


    @GetMapping("/{id}")
    public CollectionDTO getGameById(@PathVariable UUID id) {
        return this.collectionService.retrieve(id);
    }

}
