package pl.jakubwalczak.voting.api.election.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.jakubwalczak.voting.api.election.dto.ElectionResponse;
import pl.jakubwalczak.voting.domain.model.Election;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ElectionMapper {

    public static ElectionResponse toResponse(Election election) {
        return new ElectionResponse(
                election.getId(),
                election.getName()
        );
    }
}