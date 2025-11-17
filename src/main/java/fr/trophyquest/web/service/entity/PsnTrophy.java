package fr.trophyquest.web.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;


@Entity
@Table(name = "psn_trophy")
@Data
public class PsnTrophy {

    @Id
    private UUID id;

    private int rank;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trophy_set_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PsnTrophySet trophySet;

    private Boolean isHidden;

    private String trophyType;

    private String iconUrl;

    private String gameGroupId;

    private String quickGuide;

    private String youtubeVideoUrl;

    private String youtubeThumbnailUrl;

}