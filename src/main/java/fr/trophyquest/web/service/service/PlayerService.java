package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.PlayerDTO;
import fr.trophyquest.web.service.dto.PlayerSummaryDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.entity.Player;
import fr.trophyquest.web.service.mappers.PlayerMapper;
import fr.trophyquest.web.service.mappers.PlayerWithTrophyCountMapper;
import fr.trophyquest.web.service.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final PlayerWithTrophyCountMapper playerWithTrophyCountMapper;

    public PlayerService(
            PlayerRepository playerRepository,
            PlayerMapper playerMapper,
            PlayerWithTrophyCountMapper playerWithTrophyCountMapper
    ) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
        this.playerWithTrophyCountMapper = playerWithTrophyCountMapper;
    }

    public SearchDTO<PlayerSummaryDTO> search(int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        List<PlayerSummaryDTO> players = this.playerRepository.search(pageSize, offset).stream().map(
                this.playerWithTrophyCountMapper::toDTO).toList();
        long nbPlayers = this.playerRepository.count();

        return SearchDTO.<PlayerSummaryDTO>builder().content(players).total(nbPlayers).build();
    }

    public PlayerDTO findById(UUID id) {
        Player player = this.playerRepository.findById(id).orElseThrow();
        return this.playerMapper.toDTO(player);
    }

    public int count() {
        return (int) this.playerRepository.count();
    }

}
