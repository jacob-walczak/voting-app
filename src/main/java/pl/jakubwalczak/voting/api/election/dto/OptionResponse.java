package pl.jakubwalczak.voting.api.election.dto;

public record OptionResponse(
        Long id,
        String name,
        Long electionId
) { }
