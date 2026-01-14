package fr.trophyquest.backend.domain.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Embeddable
@Data
public class IgdbCandidateId implements java.io.Serializable {

    @Column(name = "trophy_set_id")
    private UUID trophySetId;

    @Column(name = "candidate_id")
    private Long candidateId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IgdbCandidateId that)) return false;
        return Objects.equals(trophySetId, that.trophySetId) && Objects.equals(candidateId, that.candidateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trophySetId, candidateId);
    }
}