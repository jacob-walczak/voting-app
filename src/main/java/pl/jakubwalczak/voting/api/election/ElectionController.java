package pl.jakubwalczak.voting.api.election;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import pl.jakubwalczak.voting.api.election.dto.AddOptionRequest;
import pl.jakubwalczak.voting.api.election.dto.CreateElectionRequest;
import pl.jakubwalczak.voting.api.election.dto.ElectionResponse;
import pl.jakubwalczak.voting.api.election.dto.OptionResponse;
import pl.jakubwalczak.voting.api.election.mapper.ElectionMapper;
import pl.jakubwalczak.voting.api.election.mapper.OptionMapper;
import pl.jakubwalczak.voting.domain.model.Election;
import pl.jakubwalczak.voting.domain.model.Option;
import pl.jakubwalczak.voting.service.ElectionService;

import java.util.List;

@RestController
@RequestMapping("/api/elections")
public class ElectionController {

    private final ElectionService electionService;

    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ElectionResponse createElection(@RequestBody @Valid CreateElectionRequest req) {
        Election election = electionService.createElection(req.name());
        return ElectionMapper.toResponse(election);
    }

    @GetMapping("/{electionId}")
    public ElectionResponse getElection(@PathVariable Long electionId) {
        return ElectionMapper.toResponse(electionService.getById(electionId));
    }

    @PostMapping("/{electionId}/options")
    @ResponseStatus(HttpStatus.CREATED)
    public OptionResponse addOption(
            @PathVariable Long electionId,
            @RequestBody @Valid AddOptionRequest req
    ) {
        Option option = electionService.addOption(electionId, req.name());
        return OptionMapper.toResponse(option);
    }

    @GetMapping("/{electionId}/options")
    public List<OptionResponse> listOptions(@PathVariable Long electionId) {
        return electionService.listOptions(electionId).stream()
                .map(OptionMapper::toResponse)
                .toList();
    }
}
