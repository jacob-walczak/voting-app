package pl.jakubwalczak.voting.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.jakubwalczak.voting.domain.model.Voter;
import pl.jakubwalczak.voting.repository.VoterRepository;

@Service
public class VoterService {

    private final VoterRepository voterRepository;

    public VoterService(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    public Voter createVoter() {
        return voterRepository.save(new Voter(true));
    }

    public Voter getById(Long voterId) {
        return voterRepository.findById(voterId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Voter not found"));
    }

    public Voter setActive(Long voterId, boolean active) {
        Voter voter = getById(voterId);
        voter.setActive(active);
        return voterRepository.save(voter);
    }
}
