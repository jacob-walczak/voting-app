package pl.jakubwalczak.voting.api.vote.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.jakubwalczak.voting.api.vote.dto.VoteResponse;
import pl.jakubwalczak.voting.domain.model.Vote;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VoteMapper {

    public static VoteResponse toResponse(Vote vote) {
        return new VoteResponse(
                vote.getId(),
                vote.getVoter().getId(),
                vote.getElection().getId(),
                vote.getOption().getId()
        );
    }
}