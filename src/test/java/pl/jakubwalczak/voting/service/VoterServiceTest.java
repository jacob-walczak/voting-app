package pl.jakubwalczak.voting.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.jakubwalczak.voting.domain.model.Voter;
import pl.jakubwalczak.voting.repository.VoterRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoterServiceTest {

    @Mock
    VoterRepository voterRepository;

    @InjectMocks
    VoterService voterService;

    @Test
    void shouldCreateActiveVoter() {
        when(voterRepository.save(any(Voter.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Voter voter = voterService.createVoter();

        assertTrue(voter.isActive());
        assertNotNull(voter.getExternalId());
    }

    @Test
    void shouldThrowNotFound_whenVoterDoesNotExist() {
        when(voterRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> voterService.getById(1L)
        );

        assertEquals(404, ex.getStatusCode().value());
    }
}