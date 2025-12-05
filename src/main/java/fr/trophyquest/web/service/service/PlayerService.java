package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.PlayerDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.entity.Player;
import fr.trophyquest.web.service.mappers.PlayerMapper;
import fr.trophyquest.web.service.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerService(
            PlayerRepository playerRepository,
            PlayerMapper playerMapper
    ) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    public SearchDTO<PlayerDTO> search(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "pseudo"));
        Page<Player> searchResult = this.playerRepository.findAll(pageRequest);
        return new SearchDTO<>(
                searchResult.getContent().stream().map(this.playerMapper::toDTO).toList(),
                searchResult.getTotalElements()
        );
    }

    public PlayerDTO findById(UUID id) {
        Player player = playerRepository.findById(id).orElseThrow();
        return this.playerMapper.toDTO(player);
    }

}
