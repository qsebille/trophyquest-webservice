package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.GameCandidatesDTO;
import fr.trophyquest.web.service.service.IgdbService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/igdb-candidate")
@CrossOrigin(origins = "*")
public class IgdbCandidateController {

    private final IgdbService igdbService;

    public IgdbCandidateController(IgdbService igdbService) {
        this.igdbService = igdbService;
    }

    @GetMapping("/search")
    public List<GameCandidatesDTO> getGameById(
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return igdbService.searchGameCandidates(pageNumber, pageSize);
    }
}