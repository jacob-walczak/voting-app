package pl.jakubwalczak.voting.api.vote;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jakubwalczak.voting.api.vote.dto.CastVoteRequest;
import pl.jakubwalczak.voting.api.vote.dto.VoteResponse;
import pl.jakubwalczak.voting.api.vote.mapper.VoteMapper;
import pl.jakubwalczak.voting.domain.model.Vote;
import pl.jakubwalczak.voting.service.VotingService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VotingService votingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteResponse castVote(@RequestBody @Valid CastVoteRequest req) {
        Vote vote = votingService.castVote(
                req.voterId(),
                req.electionId(),
                req.optionId()
        );
        return VoteMapper.toResponse(vote);
    }
}