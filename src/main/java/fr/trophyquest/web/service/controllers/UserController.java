package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.EarnedTrophyDTO;
import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.dto.PsnUserDTO;
import fr.trophyquest.web.service.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<PsnUserDTO> getAllUsers() {
        return this.userService.findAll();
    }

    @GetMapping("/{id}")
    public PsnUserDTO fetchUser(@PathVariable UUID id) {
        return userService.findById(id);
    }

    @GetMapping("/{id}/games")
    public List<GameDTO> getUserGames(@PathVariable UUID id) {
        return userService.findUserGames(id);
    }

    @GetMapping("/{id}/trophies")
    public List<EarnedTrophyDTO> getUserEarnedTrophies(@PathVariable UUID id) {
        return userService.findUserEarnedTrophies(id);
    }

}
