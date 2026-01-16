package pl.jakubwalczak.voting.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.jakubwalczak.voting.domain.model.Election;
import pl.jakubwalczak.voting.domain.model.Option;
import pl.jakubwalczak.voting.repository.ElectionRepository;
import pl.jakubwalczak.voting.repository.OptionRepository;

import java.util.List;

@Service
public class ElectionService {

    private final ElectionRepository electionRepository;
    private final OptionRepository optionRepository;

    public ElectionService(ElectionRepository electionRepository, OptionRepository optionRepository) {
        this.electionRepository = electionRepository;
        this.optionRepository = optionRepository;
    }

    public Election createElection(String name) {
        return electionRepository.save(new Election(name));
    }

    public Election getById(Long electionId) {
        return electionRepository.findById(electionId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Election not found"));
    }

    public Option addOption(Long electionId, String optionName) {
        Election election = getById(electionId);
        return optionRepository.save(new Option(optionName, election));
    }

    public List<Option> listOptions(Long electionId) {
        getById(electionId);
        return optionRepository.findByElectionId(electionId);
    }
}