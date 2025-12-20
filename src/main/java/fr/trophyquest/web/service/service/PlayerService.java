package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.MostActivePlayerResponseDTO;
import fr.trophyquest.web.service.dto.PlayerDTO;
import fr.trophyquest.web.service.dto.PlayerSummaryDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.entity.Player;
import fr.trophyquest.web.service.entity.projections.ActivePlayerProjection;
import fr.trophyquest.web.service.mappers.ActivePlayerMapper;
import fr.trophyquest.web.service.mappers.PlayerMapper;
import fr.trophyquest.web.service.mappers.PlayerSummaryMapper;
import fr.trophyquest.web.service.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final ActivePlayerMapper activePlayerMapper;
    private final PlayerSummaryMapper playerSummaryMapper;

    public PlayerService(
            PlayerRepository playerRepository,
            PlayerMapper playerMapper,
            ActivePlayerMapper activePlayerMapper,
            PlayerSummaryMapper playerSummaryMapper
    ) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
        this.activePlayerMapper = activePlayerMapper;
        this.playerSummaryMapper = playerSummaryMapper;
    }

    /**
     * Retrieves a paginated list of players, mapped to a summary DTO, and the total number of players.
     *
     * @param pageNumber the current page number (0-based index)
     * @param pageSize   the number of players to retrieve per page
     * @return a SearchDTO containing the list of PlayerSummaryDTOs for the given page and the total number of players
     */
    public SearchDTO<PlayerSummaryDTO> search(int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        List<PlayerSummaryDTO> players = this.playerRepository.search(pageSize, offset).stream().map(
                this.playerSummaryMapper::toDTO).toList();
        long nbPlayers = this.playerRepository.count();

        return SearchDTO.<PlayerSummaryDTO>builder().content(players).total(nbPlayers).build();
    }

    /**
     * Finds a player by their unique identifier and converts the entity into a Data Transfer Object (DTO).
     *
     * @param id the unique identifier of the player to retrieve
     * @return a PlayerDTO containing data of the found player
     * @throws java.util.NoSuchElementException if no player is found with the provided identifier
     */
    public PlayerDTO findById(UUID id) {
        Player player = this.playerRepository.findById(id).orElseThrow();
        return this.playerMapper.toDTO(player);
    }

    public Optional<PlayerDTO> findByPseudo(String pseudo) {
        return this.playerRepository.findByPseudo(pseudo).stream().map(this.playerMapper::toDTO).findFirst();
    }

    public int count() {
        return (int) this.playerRepository.count();
    }

    public List<MostActivePlayerResponseDTO> fetchMostActive(int limit) {
        List<ActivePlayerProjection> projections = this.playerRepository.fetchMostActivePlayerTrophies(limit);
        return projections.stream().map(this.activePlayerMapper::toDTO).toList();
    }

}
