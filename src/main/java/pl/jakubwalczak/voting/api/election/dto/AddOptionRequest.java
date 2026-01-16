package pl.jakubwalczak.voting.api.election.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddOptionRequest(
        @NotBlank @Size(max = 200) String name
) { }
