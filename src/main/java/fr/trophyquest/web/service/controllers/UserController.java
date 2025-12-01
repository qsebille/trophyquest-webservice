package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.GameGroupTrophiesDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.dto.TrophyDTO;
import fr.trophyquest.web.service.dto.UserGameDTO;
import fr.trophyquest.web.service.dto.UserProfileDTO;
import fr.trophyquest.web.service.service.GameService;
import fr.trophyquest.web.service.service.TrophyService;
import fr.trophyquest.web.service.service.UserService;
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

    public UserController(UserService userService, GameService gameService, TrophyService trophyService) {
        this.userService = userService;
        this.gameService = gameService;
        this.trophyService = trophyService;
    }


    @GetMapping("/search")
    public SearchDTO<UserProfileDTO> searchUsers(
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return this.userService.search(pageNumber, pageSize);
    }

    @GetMapping("/{userId}")
    public UserProfileDTO fetchUser(@PathVariable UUID userId) {
        return userService.findById(userId);
    }

    @GetMapping("/{userId}/trophy-count")
    public TrophyCountDTO fetchTrophySummary(@PathVariable UUID userId) {
        return this.trophyService.getUserTrophyCount(userId);
    }

    @GetMapping("/{userId}/games")
    public SearchDTO<UserGameDTO> fetchGames(
            @PathVariable UUID userId,
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return this.gameService.searchUserGames(userId, pageNumber, pageSize);
    }

    @GetMapping("/{userId}/collection/{collectionId}/trophies")
    public List<GameGroupTrophiesDTO> fetchUserCollectionTrophies(
            @PathVariable UUID userId,
            @PathVariable UUID collectionId
    ) {
        return this.trophyService.fetchUserCollectionTrophies(userId, collectionId);
    }

    @GetMapping("/{userId}/trophies")
    public SearchDTO<TrophyDTO> fetchTrophies(
            @PathVariable UUID userId,
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return this.trophyService.searchUserEarnedTrophies(userId, pageNumber, pageSize);
    }

}
