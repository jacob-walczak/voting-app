package pl.jakubwalczak.voting.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.jakubwalczak.voting.domain.model.Election;
import pl.jakubwalczak.voting.repository.ElectionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElectionServiceTest {

    @Mock
    ElectionRepository electionRepository;

    @InjectMocks
    ElectionService electionService;

    @Test
    void shouldCreateElection() {
        when(electionRepository.save(any(Election.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Election election = electionService.createElection("Election");

        assertNotNull(election);
        assertEquals("Election", election.getName());
    }

    @Test
    void shouldThrowNotFound_whenElectionDoesNotExist() {
        when(electionRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> electionService.getById(1L)
        );

        assertEquals(404, ex.getStatusCode().value());
    }
}