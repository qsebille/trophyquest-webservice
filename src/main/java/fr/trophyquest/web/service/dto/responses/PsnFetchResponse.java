package fr.trophyquest.web.service.dto.responses;

import lombok.Builder;

@Builder
public record PsnFetchResponse(
        String status,
        Integer lambdaStatus,
        Boolean functionError
) {
}
