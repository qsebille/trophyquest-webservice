package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.dto.UserProfileDTO;
import fr.trophyquest.web.service.entity.UserProfile;
import fr.trophyquest.web.service.mappers.UserProfileMapper;
import fr.trophyquest.web.service.repository.UserProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public SearchDTO<UserProfileDTO> search(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        Page<UserProfile> searchResult = this.userProfileRepository.findAll(pageRequest);
        return new SearchDTO<>(
                searchResult.getContent().stream().map(this.userProfileMapper::toDTO).toList(),
                searchResult.getTotalElements()
        );
    }

    public UserProfileDTO findById(UUID id) {
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow();
        return this.userProfileMapper.toDTO(userProfile);
    }

}
