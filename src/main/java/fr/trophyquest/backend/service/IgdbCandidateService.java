package fr.trophyquest.backend.service;

import fr.trophyquest.backend.domain.entity.Game;
import fr.trophyquest.backend.domain.entity.igdb.IgdbGame;
import fr.trophyquest.backend.repository.GameRepository;
import fr.trophyquest.backend.repository.IgdbCandidateRepository;
import fr.trophyquest.backend.repository.IgdbGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class IgdbCandidateService {
    private final GameRepository gameRepository;
    private final IgdbGameRepository igdbGameRepository;
    private final IgdbCandidateRepository igdbCandidateRepository;

    public IgdbCandidateService(
            GameRepository gameRepository,
            IgdbGameRepository igdbGameRepository,
            IgdbCandidateRepository igdbCandidateRepository
    ) {
        this.gameRepository = gameRepository;
        this.igdbGameRepository = igdbGameRepository;
        this.igdbCandidateRepository = igdbCandidateRepository;
    }

    @Transactional
    public Boolean validateCandidate(UUID gameId, long igdbGameId) {
        try {
            Game game = this.gameRepository.getReferenceById(gameId);
            IgdbGame igdbGame = this.igdbGameRepository.getReferenceById(igdbGameId);
            game.setIgdbGame(igdbGame);
            this.gameRepository.save(game);
            this.igdbCandidateRepository.updateStatusAfterValidation(gameId, igdbGameId);
            return true;
        } catch (Exception e) {
            log.error("Failed to validate candidate for gameId {} and igdbGameId {}", gameId, igdbGameId, e);
            return false;
        }
    }

    @Transactional
    public Boolean rejectAllPendingCandidates(UUID gameId) {
        try {
            this.igdbCandidateRepository.updateStatusToRejected(gameId);
            return true;
        } catch (Exception e) {
            log.error("Failed to reject candidates for gameId {}", gameId, e);
            return false;
        }
    }

}
