package pl.jakubwalczak.voting.api.vote.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CastVoteRequest(
        @NotNull @Positive Long voterId,
        @NotNull @Positive Long electionId,
        @NotNull @Positive Long optionId
) { }
