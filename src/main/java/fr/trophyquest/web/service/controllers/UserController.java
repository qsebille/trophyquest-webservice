package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.GameSearchDTO;
import fr.trophyquest.web.service.dto.UserProfileDTO;
import fr.trophyquest.web.service.service.GameService;
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

    public UserController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }


    @GetMapping("/all")
    public List<UserProfileDTO> getAllUsers() {
        return this.userService.findAll();
    }

    @GetMapping("/{userId}")
    public UserProfileDTO fetchUser(@PathVariable UUID userId) {
        return userService.findById(userId);
    }

    @GetMapping("/{userId}/games")
    public GameSearchDTO fetchGames(
            @PathVariable UUID userId,
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return this.gameService.searchUserGames(userId, pageNumber, pageSize);
    }
    
}
