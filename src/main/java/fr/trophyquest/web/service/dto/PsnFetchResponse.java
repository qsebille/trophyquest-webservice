package fr.trophyquest.web.service.dto;

public record PsnFetchResponse(
        String status,
        Integer lambdaStatus,
        Boolean functionError
) {
}
