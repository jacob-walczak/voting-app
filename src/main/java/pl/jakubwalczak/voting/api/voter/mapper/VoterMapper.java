package pl.jakubwalczak.voting.api.voter.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.jakubwalczak.voting.api.voter.dto.VoterResponse;
import pl.jakubwalczak.voting.domain.model.Voter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VoterMapper {

    public static VoterResponse toResponse(Voter voter) {
        return new VoterResponse(
                voter.getId(),
                voter.getExternalId(),
                voter.isActive()
        );
    }
}

