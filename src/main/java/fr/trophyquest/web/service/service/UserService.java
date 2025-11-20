package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.UserProfileDTO;
import fr.trophyquest.web.service.entity.UserProfile;
import fr.trophyquest.web.service.mappers.UserProfileMapper;
import fr.trophyquest.web.service.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    public UserService(
            UserProfileRepository userProfileRepository,
            UserProfileMapper userProfileMapper
    ) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }

    public UserProfileDTO findById(UUID id) {
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow();
        return this.userProfileMapper.toDTO(userProfile);
    }

    public List<UserProfileDTO> findAll() {
        return userProfileRepository.findAll().stream()
                .map(this.userProfileMapper::toDTO)
                .toList();
    }
}
