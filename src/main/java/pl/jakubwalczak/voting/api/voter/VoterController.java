package pl.jakubwalczak.voting.api.voter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jakubwalczak.voting.api.voter.dto.CreateVoterRequest;
import pl.jakubwalczak.voting.api.voter.dto.UpdateVoterStatusRequest;
import pl.jakubwalczak.voting.api.voter.dto.VoterResponse;
import pl.jakubwalczak.voting.api.voter.mapper.VoterMapper;
import pl.jakubwalczak.voting.domain.model.Voter;
import pl.jakubwalczak.voting.service.VoterService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/voters")
public class VoterController {

    private final VoterService voterService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoterResponse createVoter(@RequestBody(required = false) CreateVoterRequest ignored) {
        Voter voter = voterService.createVoter();
        return VoterMapper.toResponse(voter);
    }

    @GetMapping("/{voterId}")
    public VoterResponse getVoter(@PathVariable Long voterId) {
        return VoterMapper.toResponse(voterService.getById(voterId));
    }

    @PatchMapping("/{voterId}/status")
    public VoterResponse updateStatus(
            @PathVariable Long voterId,
            @RequestBody @Valid UpdateVoterStatusRequest req
    ) {
        Voter voter = voterService.setActive(voterId, req.active());
        return VoterMapper.toResponse(voter);
    }
}
