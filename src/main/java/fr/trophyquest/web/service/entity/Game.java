package fr.trophyquest.web.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "game")
@Data
public class Game {

    @Id
    private UUID id;

    private String title;

    private String platform;

    private String imageUrl;

    @ManyToMany
    @JoinTable(name = "user_game", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<PsnUser> psnUsers = new HashSet<>();

}