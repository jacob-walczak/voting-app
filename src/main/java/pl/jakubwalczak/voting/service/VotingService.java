package pl.jakubwalczak.voting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import pl.jakubwalczak.voting.domain.model.Election;
import pl.jakubwalczak.voting.domain.model.Option;
import pl.jakubwalczak.voting.domain.model.Vote;
import pl.jakubwalczak.voting.domain.model.Voter;
import pl.jakubwalczak.voting.repository.ElectionRepository;
import pl.jakubwalczak.voting.repository.OptionRepository;
import pl.jakubwalczak.voting.repository.VoteRepository;
import pl.jakubwalczak.voting.repository.VoterRepository;

@RequiredArgsConstructor
@Service
public class VotingService {

    private final VoterRepository voterRepository;
    private final ElectionRepository electionRepository;
    private final OptionRepository optionRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public Vote castVote(Long voterId, Long electionId, Long optionId) {

        Voter voter = voterRepository.findById(voterId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Voter not found"));

        if (!voter.isActive()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Voter is blocked");
        }

        Election election = electionRepository.findById(electionId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Election not found"));

        Option option = optionRepository.findById(optionId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Option not found"));

        if (!option.getElection().getId().equals(electionId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Option does not belong to the election"
            );
        }

        if (voteRepository.existsByVoterIdAndElectionId(voterId, electionId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Voter has already voted in this election"
            );
        }

        try {
            return voteRepository.save(new Vote(voter, election, option));
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Voter has already voted in this election",
                    ex
            );
        }
    }
}
