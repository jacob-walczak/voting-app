package pl.jakubwalczak.voting.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;
import pl.jakubwalczak.voting.domain.model.Election;
import pl.jakubwalczak.voting.domain.model.Option;
import pl.jakubwalczak.voting.domain.model.Vote;
import pl.jakubwalczak.voting.domain.model.Voter;
import pl.jakubwalczak.voting.repository.ElectionRepository;
import pl.jakubwalczak.voting.repository.OptionRepository;
import pl.jakubwalczak.voting.repository.VoteRepository;
import pl.jakubwalczak.voting.repository.VoterRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotingServiceTest {

    private static final Long VOTER_ID = 1L;
    private static final Long ELECTION_ID = 2L;
    private static final Long OPTION_ID = 3L;

    @Mock
    VoterRepository voterRepository;

    @Mock
    ElectionRepository electionRepository;

    @Mock
    OptionRepository optionRepository;

    @Mock
    VoteRepository voteRepository;

    @InjectMocks
    VotingService votingService;

    @Test
    void shouldThrowForbidden_whenVoterIsInactive() {
        Voter voter = new Voter(false);
        ReflectionTestUtils.setField(voter, "id", VOTER_ID);

        when(voterRepository.findById(VOTER_ID)).thenReturn(Optional.of(voter));

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> votingService.castVote(VOTER_ID, ELECTION_ID, OPTION_ID)
        );

        assertEquals(403, ex.getStatusCode().value());
        verifyNoInteractions(electionRepository, optionRepository, voteRepository);
    }

    @Test
    void shouldThrowConflict_whenVoterAlreadyVoted() {
        Voter voter = new Voter(true);
        ReflectionTestUtils.setField(voter, "id", VOTER_ID);

        Election election = new Election("Election");
        ReflectionTestUtils.setField(election, "id", ELECTION_ID);

        Option option = new Option("Option", election);
        ReflectionTestUtils.setField(option, "id", OPTION_ID);

        when(voterRepository.findById(VOTER_ID)).thenReturn(Optional.of(voter));
        when(electionRepository.findById(ELECTION_ID)).thenReturn(Optional.of(election));
        when(optionRepository.findById(OPTION_ID)).thenReturn(Optional.of(option));
        when(voteRepository.existsByVoterIdAndElectionId(VOTER_ID, ELECTION_ID))
                .thenReturn(true);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> votingService.castVote(VOTER_ID, ELECTION_ID, OPTION_ID)
        );

        assertEquals(409, ex.getStatusCode().value());
        verify(voteRepository, never()).save(any());
    }

    @Test
    void shouldThrowBadRequest_whenOptionDoesNotBelongToElection() {
        Voter voter = new Voter(true);
        ReflectionTestUtils.setField(voter, "id", VOTER_ID);

        Election election = new Election("Election");
        ReflectionTestUtils.setField(election, "id", ELECTION_ID);

        Election otherElection = new Election("Other Election");
        ReflectionTestUtils.setField(otherElection, "id", 99L);

        Option option = new Option("Option", otherElection);
        ReflectionTestUtils.setField(option, "id", OPTION_ID);

        when(voterRepository.findById(VOTER_ID)).thenReturn(Optional.of(voter));
        when(electionRepository.findById(ELECTION_ID)).thenReturn(Optional.of(election));
        when(optionRepository.findById(OPTION_ID)).thenReturn(Optional.of(option));

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> votingService.castVote(VOTER_ID, ELECTION_ID, OPTION_ID)
        );

        assertEquals(400, ex.getStatusCode().value());
        verify(voteRepository, never()).save(any());
    }

    @Test
    void shouldSaveVote_whenAllConditionsMet() {
        Voter voter = new Voter(true);
        ReflectionTestUtils.setField(voter, "id", VOTER_ID);

        Election election = new Election("Election");
        ReflectionTestUtils.setField(election, "id", ELECTION_ID);

        Option option = new Option("Option", election);
        ReflectionTestUtils.setField(option, "id", OPTION_ID);

        when(voterRepository.findById(VOTER_ID)).thenReturn(Optional.of(voter));
        when(electionRepository.findById(ELECTION_ID)).thenReturn(Optional.of(election));
        when(optionRepository.findById(OPTION_ID)).thenReturn(Optional.of(option));
        when(voteRepository.existsByVoterIdAndElectionId(VOTER_ID, ELECTION_ID))
                .thenReturn(false);
        when(voteRepository.save(any(Vote.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Vote vote = votingService.castVote(VOTER_ID, ELECTION_ID, OPTION_ID);

        assertNotNull(vote);
        verify(voteRepository).save(any(Vote.class));
    }
}
