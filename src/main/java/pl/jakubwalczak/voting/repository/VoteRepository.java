package pl.jakubwalczak.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jakubwalczak.voting.domain.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByVoterIdAndElectionId(Long voterId, Long electionId);
}
