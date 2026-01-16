package pl.jakubwalczak.voting.api.vote.dto;

public record VoteResponse(
        Long voteId,
        Long voterId,
        Long electionId,
        Long optionId
) { }
