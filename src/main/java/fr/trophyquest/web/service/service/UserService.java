package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.EarnedTrophyDTO;
import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.dto.PsnUserDTO;
import fr.trophyquest.web.service.entity.PsnUser;
import fr.trophyquest.web.service.mapper.EarnedTrophyDTOMapper;
import fr.trophyquest.web.service.mapper.GameDTOMapper;
import fr.trophyquest.web.service.mapper.UserDTOMapper;
import fr.trophyquest.web.service.repository.TrophyRepository;
import fr.trophyquest.web.service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TrophyRepository trophyRepository;
    private final UserDTOMapper userDTOMapper;
    private final GameDTOMapper gameDTOMapper;
    private final EarnedTrophyDTOMapper earnedTrophyDTOMapper;

    public UserService(
            UserRepository userRepository,
            TrophyRepository trophyRepository,
            UserDTOMapper userDTOMapper,
            GameDTOMapper gameDTOMapper,
            EarnedTrophyDTOMapper earnedTrophyDTOMapper
    ) {
        this.userRepository = userRepository;
        this.trophyRepository = trophyRepository;
        this.userDTOMapper = userDTOMapper;
        this.gameDTOMapper = gameDTOMapper;
        this.earnedTrophyDTOMapper = earnedTrophyDTOMapper;
    }

    public List<PsnUserDTO> findAll() {
        return this.userRepository.findAll()
                .stream()
                .map(this.userDTOMapper::toDTO)
                .toList();
    }

    public PsnUserDTO findById(UUID id) {
        PsnUser psnUser = this.userRepository.findById(id).orElseThrow();
        return this.userDTOMapper.toDTO(psnUser);
    }

    public List<GameDTO> findUserGames(UUID id) {
        PsnUser psnUser = this.userRepository.findById(id).orElseThrow();
        return psnUser.getGames().stream()
                .map(this.gameDTOMapper::toDTO)
                .toList();
    }

    public List<EarnedTrophyDTO> findUserEarnedTrophies(UUID id) {
        PsnUser psnUser = this.userRepository.findById(id).orElseThrow();
        return this.trophyRepository.findEarnedTrophiesByUserId(psnUser.getId())
                .stream()
                .map(this.earnedTrophyDTOMapper::toDTO)
                .toList();
    }

}
