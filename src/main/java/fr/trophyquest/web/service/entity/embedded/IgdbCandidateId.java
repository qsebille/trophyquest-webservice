package fr.trophyquest.web.service.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Embeddable
@Data
@EqualsAndHashCode
public class IgdbCandidateId implements java.io.Serializable {

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "candidate_id")
    private Long candidateId;

}