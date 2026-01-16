package pl.jakubwalczak.voting.api.voter.dto;

import java.util.UUID;

public record VoterResponse(
        Long id,
        UUID externalId,
        boolean active
) {}

