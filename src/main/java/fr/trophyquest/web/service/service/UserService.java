package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.UserDTO;
import fr.trophyquest.web.service.mapper.UserDTOMapper;
import fr.trophyquest.web.service.model.User;
import fr.trophyquest.web.service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    public List<UserDTO> findAll() {
        return this.userRepository.findAll()
                .stream()
                .map(this.userDTOMapper::toDTO)
                .toList();
    }

    public UserDTO findById(UUID id) {
        User user = this.userRepository.findById(id).orElseThrow();
        return this.userDTOMapper.toDTO(user);
    }
}
