package fr.trophyquest.backend.domain.entity;

import fr.trophyquest.backend.domain.entity.embedded.IgdbCandidateId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name = "igdb_candidate")
@Data
public class IgdbCandidate {

    @EmbeddedId
    private IgdbCandidateId id;

    private Long score;

    private String status;

    @MapsId("trophySetId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trophy_set_id", nullable = false)
    private TrophySet trophySet;

    @MapsId("candidateId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private IgdbGame candidate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IgdbCandidate that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}