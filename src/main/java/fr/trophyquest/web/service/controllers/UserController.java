package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.UserDTO;
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
    public List<UserDTO> getAllUsers() {
        return this.userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDTO fetchUser(@PathVariable UUID id) {
        return userService.findById(id);
    }

//    @GetMapping("/{id}/games")
//    public List<GameDTO> getUserGames(@PathVariable Long id) {
//        return
//    }

}
