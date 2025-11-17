package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.PsnUserDTO;
import fr.trophyquest.web.service.dto.TrophyDTO;
import fr.trophyquest.web.service.entity.projections.PlayedGameProjection;
import fr.trophyquest.web.service.entity.projections.UserTrophyProjection;
import fr.trophyquest.web.service.service.GameService;
import fr.trophyquest.web.service.service.TrophyService;
import fr.trophyquest.web.service.service.UserService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final GameService gameService;
    private final TrophyService trophyService;

    public UserController(
            UserService userService,
            GameService gameService,
            TrophyService trophyService
    ) {
        this.userService = userService;
        this.gameService = gameService;
        this.trophyService = trophyService;
    }

    @GetMapping
    public List<PsnUserDTO> getAllUsers() {
        return this.userService.findAll();
    }

    @GetMapping("/{userId}")
    public PsnUserDTO fetchUser(@PathVariable UUID userId) {
        return userService.findById(userId);
    }

    @GetMapping("/{userId}/games")
    public List<PlayedGameProjection> getUserGames(
            @PathVariable UUID userId,
            @RequestParam(name = "searchFrom", defaultValue = "0") String searchFrom,
            @RequestParam(name = "searchSize", defaultValue = "50") String searchSize
    ) {
        return this.gameService.getUserPlayedGames(
                userId,
                searchFrom,
                searchSize
        );
    }

    @GetMapping("/{userId}/games/{gameId}/trophies")
    public List<UserTrophyProjection> getUserGameTrophies(
            @PathVariable UUID userId,
            @PathVariable UUID gameId
    ) {
        return this.trophyService.getUserGameTrophies(
                userId,
                gameId
        );
    }

    @GetMapping("/{userId}/trophies")
    public List<TrophyDTO> getUserTrophies(@PathVariable UUID userId) {
        throw new NotImplementedException("User trophies not implemented yet");
    }

}
