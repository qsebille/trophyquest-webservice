package fr.trophyquest.backend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Formula;

import java.util.UUID;

@Entity
@Table(name = "psn_game_image")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GameImage {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @Formula("coalesce(aws_url, psn_url)")
    private String url;

    private String type;

    private String format;

}