package fr.trophyquest.backend.domain.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IgdbLinkId implements java.io.Serializable {

    @Column(name = "trophy_set_id")
    private UUID trophySetId;

    @Column(name = "game_id")
    private Long gameId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IgdbLinkId that)) return false;
        return Objects.equals(trophySetId, that.trophySetId) && Objects.equals(gameId, that.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trophySetId, gameId);
    }
}