package fr.trophyquest.web.service.entity;

import fr.trophyquest.web.service.entity.embedded.IgdbCandidateId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "igdb_candidate")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class IgdbCandidate {

    @EmbeddedId
    private IgdbCandidateId id;

    @MapsId("gameId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @MapsId("candidateId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private IgdbGame candidate;

    @MapsId("status")
    private String status;

}