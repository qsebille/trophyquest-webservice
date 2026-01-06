package fr.trophyquest.web.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "igdb_game")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class IgdbGame {

    @Id
    private Long id;

    private String name;

    private String summary;

    private Date releaseDate;

    private List<String> genres;

    private List<String> themes;

    private List<String> screenshots;

    private List<String> collections;

    private String cover;

    private String artworkWithLogo;

    private String artworkWithoutLogo;

    private String psnWebsite;

    private String officialWebsite;

    private String communityWikiWebsite;

    private List<String> youtubeIds;

    private List<String> developers;

    private List<String> publishers;

}