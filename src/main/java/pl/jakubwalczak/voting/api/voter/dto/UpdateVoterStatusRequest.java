package pl.jakubwalczak.voting.api.voter.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateVoterStatusRequest(
        @NotNull Boolean active
) { }
