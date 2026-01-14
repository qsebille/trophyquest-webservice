package fr.trophyquest.backend.service;

import fr.trophyquest.backend.api.dto.SearchDTO;
import fr.trophyquest.backend.api.dto.player.PlayerDTO;
import fr.trophyquest.backend.api.dto.player.PlayerLastActivityDTO;
import fr.trophyquest.backend.api.dto.player.PlayerSearchItemDTO;
import fr.trophyquest.backend.api.dto.player.PlayerStatsDTO;
import fr.trophyquest.backend.api.dto.player.RecentPlayerTrophiesItemDTO;
import fr.trophyquest.backend.api.dto.trophy.EarnedTrophySearchItemDTO;
import fr.trophyquest.backend.api.dto.trophyset.PlayedTrophySetSearchItemDTO;
import fr.trophyquest.backend.api.mapper.PlayerMapper;
import fr.trophyquest.backend.api.mapper.TrophyMapper;
import fr.trophyquest.backend.api.mapper.TrophySetMapper;
import fr.trophyquest.backend.domain.entity.Player;
import fr.trophyquest.backend.domain.entity.Trophy;
import fr.trophyquest.backend.domain.entity.TrophySet;
import fr.trophyquest.backend.domain.projection.RecentPlayerRow;
import fr.trophyquest.backend.repository.EarnedTrophyRepository;
import fr.trophyquest.backend.repository.PlayedTrophySetRepository;
import fr.trophyquest.backend.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayedTrophySetRepository playedTrophySetRepository;
    private final EarnedTrophyRepository earnedTrophyRepository;

    private final PlayerMapper playerMapper;
    private final TrophySetMapper trophySetMapper;
    private final TrophyMapper trophyMapper;

    public PlayerService(
            PlayerRepository playerRepository,
            PlayedTrophySetRepository playedTrophySetRepository,
            EarnedTrophyRepository earnedTrophyRepository,
            PlayerMapper playerMapper,
            TrophySetMapper trophySetMapper,
            TrophyMapper trophyMapper
    ) {
        this.playerRepository = playerRepository;
        this.playedTrophySetRepository = playedTrophySetRepository;
        this.earnedTrophyRepository = earnedTrophyRepository;
        this.playerMapper = playerMapper;
        this.trophySetMapper = trophySetMapper;
        this.trophyMapper = trophyMapper;
    }

    public long count() {
        return this.playerRepository.count();
    }

    public long countRecentlyActive() {
        Instant limitDate = Instant.now().minus(7, ChronoUnit.DAYS);
        return this.playedTrophySetRepository.countRecentPlayers(limitDate);
    }

    public PlayerDTO fetch(UUID id) {
        Player player = this.playerRepository.findById(id).orElseThrow();
        return this.playerMapper.toDTO(player);
    }

    public Optional<PlayerDTO> findByPseudo(String pseudo) {
        return this.playerRepository.findByPseudo(pseudo).map(this.playerMapper::toDTO);
    }

    public PlayerLastActivityDTO fetchLastActivity(UUID id) {
        TrophySet lastPlayedTrophySet = this.playedTrophySetRepository.listByPlayerId(id).get(0);
        Trophy lastEarnedTrophy = this.earnedTrophyRepository.listByPlayerId(id).get(0);

        return PlayerLastActivityDTO.builder().lastPlayedTrophySet(
                this.trophySetMapper.toDTO(lastPlayedTrophySet)).lastEarnedTrophy(
                this.trophyMapper.toDTO(lastEarnedTrophy)).build();
    }

    public PlayerStatsDTO fetchStats(UUID id) {
        return this.earnedTrophyRepository.getStatsForPlayer(id);
    }

    public List<PlayerSearchItemDTO> search(int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        return this.playerRepository.searchPlayers(pageSize, offset);
    }

    public SearchDTO<EarnedTrophySearchItemDTO> searchEarnedTrophies(UUID playerId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "earnedAt"));
        Page<EarnedTrophySearchItemDTO> searchResult = this.earnedTrophyRepository.searchEarnedTrophiesByPlayer(
                playerId, pageRequest);
        return SearchDTO.<EarnedTrophySearchItemDTO>builder().content(searchResult.getContent()).total(
                searchResult.getTotalElements()).build();
    }

    public SearchDTO<PlayedTrophySetSearchItemDTO> searchPlayedTrophySets(UUID playerId, int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        List<PlayedTrophySetSearchItemDTO> results = this.playedTrophySetRepository.searchPlayedTrophySetsByPlayer(
                playerId, pageSize, offset);
        return SearchDTO.<PlayedTrophySetSearchItemDTO>builder().content(results).total(
                this.playedTrophySetRepository.countPlayedTrophySetsByPlayer(playerId)).build();
    }

    public List<RecentPlayerTrophiesItemDTO> fetchTopRecent(int playerLimit, int trophyLimit) {
        Instant limitDate = Instant.now().minus(7, ChronoUnit.DAYS);
        List<RecentPlayerRow> rows = this.playerRepository.searchRecentPlayerTrophies(
                playerLimit, trophyLimit, limitDate);

        Map<UUID, RecentPlayerTrophiesItemDTO> players = new LinkedHashMap<>();

        for (RecentPlayerRow row : rows) {
            EarnedTrophySearchItemDTO trophy = EarnedTrophySearchItemDTO.builder()
                    .id(row.getTrophyId())
                    .title(row.getTrophyTitle())
                    .trophyType(row.getTrophyType())
                    .icon(row.getTrophyIcon())
                    .description(row.getTrophyDescription())
                    .trophySetId(row.getTrophySetId())
                    .trophySetTitle(row.getTrophySetTitle())
                    .earnedAt(row.getEarnedAt())
                    .build();

            RecentPlayerTrophiesItemDTO item = players.computeIfAbsent(
                    row.getPlayerId(), k -> {
                        PlayerDTO player = PlayerDTO.builder()
                                .id(row.getPlayerId())
                                .pseudo(row.getPseudo())
                                .avatar(row.getAvatar())
                                .build();
                        return RecentPlayerTrophiesItemDTO.builder()
                                .player(player)
                                .recentTrophyCount(row.getRecentTrophyCount())
                                .lastTrophies(new java.util.ArrayList<>())
                                .build();
                    }
            );

            item.lastTrophies().add(trophy);
        }

        return players.values().stream().toList();
    }

}
