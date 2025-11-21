package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.dto.UserGameSearchDTO;
import fr.trophyquest.web.service.dto.UserProfileDTO;
import fr.trophyquest.web.service.dto.UserSearchDTO;
import fr.trophyquest.web.service.service.GameService;
import fr.trophyquest.web.service.service.TrophyService;
import fr.trophyquest.web.service.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public UserSearchDTO searchUsers(
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
    public UserGameSearchDTO fetchGames(
            @PathVariable UUID userId,
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return this.gameService.searchUserGames(userId, pageNumber, pageSize);
    }

}
