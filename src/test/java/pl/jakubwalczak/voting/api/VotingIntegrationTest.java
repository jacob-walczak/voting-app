package pl.jakubwalczak.voting.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.transaction.annotation.Transactional;
import pl.jakubwalczak.voting.domain.model.Election;
import pl.jakubwalczak.voting.domain.model.Option;
import pl.jakubwalczak.voting.domain.model.Voter;
import pl.jakubwalczak.voting.repository.ElectionRepository;
import pl.jakubwalczak.voting.repository.OptionRepository;
import pl.jakubwalczak.voting.repository.VoterRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class VotingIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    VoterRepository voterRepository;
    @Autowired
    ElectionRepository electionRepository;
    @Autowired
    OptionRepository optionRepository;

    @Test
    void shouldRejectSecondVoteForSameElection() throws Exception {
        Voter voter = voterRepository.save(new Voter(true));
        Election election = electionRepository.save(new Election("Election"));
        Option option = optionRepository.save(new Option("Option", election));

        String body = """
            {
              "voterId": %d,
              "electionId": %d,
              "optionId": %d
            }
            """.formatted(voter.getId(), election.getId(), option.getId());

        mockMvc.perform(post("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }
}
