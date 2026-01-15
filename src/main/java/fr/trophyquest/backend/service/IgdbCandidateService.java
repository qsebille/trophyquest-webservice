package fr.trophyquest.backend.service;

import fr.trophyquest.backend.domain.entity.IgdbLink;
import fr.trophyquest.backend.domain.entity.embedded.IgdbLinkId;
import fr.trophyquest.backend.repository.IgdbCandidateRepository;
import fr.trophyquest.backend.repository.IgdbGameRepository;
import fr.trophyquest.backend.repository.IgdbLinkRepository;
import fr.trophyquest.backend.repository.TrophySetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class IgdbCandidateService {
    private final TrophySetRepository trophySetRepository;
    private final IgdbLinkRepository igdbLinkRepository;
    private final IgdbGameRepository igdbGameRepository;
    private final IgdbCandidateRepository igdbCandidateRepository;

    public IgdbCandidateService(
            TrophySetRepository trophySetRepository,
            IgdbLinkRepository igdbLinkRepository,
            IgdbGameRepository igdbGameRepository,
            IgdbCandidateRepository igdbCandidateRepository
    ) {
        this.trophySetRepository = trophySetRepository;
        this.igdbLinkRepository = igdbLinkRepository;
        this.igdbGameRepository = igdbGameRepository;
        this.igdbCandidateRepository = igdbCandidateRepository;
    }

    @Transactional
    public Boolean validateCandidate(UUID trophySetId, long igdbGameId) {
        try {
            IgdbLink link = new IgdbLink();
            link.setId(new IgdbLinkId(trophySetId, igdbGameId));
            link.setTrophySet(this.trophySetRepository.getReferenceById(trophySetId));
            link.setGame(this.igdbGameRepository.getReferenceById(igdbGameId));
            this.igdbLinkRepository.save(link);
            this.igdbCandidateRepository.updateCandidateStatus(trophySetId, igdbGameId);
            return true;
        } catch (Exception e) {
            log.error("Failed to validate candidate for trophySetId {} and igdbGameId {}", trophySetId, igdbGameId, e);
            return false;
        }
    }

}
