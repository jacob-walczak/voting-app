package pl.jakubwalczak.voting.api.election.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import pl.jakubwalczak.voting.api.election.dto.OptionResponse;
import pl.jakubwalczak.voting.domain.model.Option;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionMapper {

    public static OptionResponse toResponse(Option option) {
        return new OptionResponse(
                option.getId(),
                option.getName(),
                option.getElection().getId()
        );
    }
}