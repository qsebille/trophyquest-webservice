package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.PsnUserDTO;
import fr.trophyquest.web.service.entity.PsnUser;
import fr.trophyquest.web.service.mapper.UserDTOMapper;
import fr.trophyquest.web.service.repository.PsnUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final PsnUserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    public UserService(PsnUserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    public List<PsnUserDTO> findAll() {
        return this.userRepository.findAll().stream().map(this.userDTOMapper::toDTO).toList();
    }

    public PsnUserDTO findById(UUID id) {
        PsnUser psnUser = this.userRepository.findById(id).orElseThrow();
        return this.userDTOMapper.toDTO(psnUser);
    }

}
