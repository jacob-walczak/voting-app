package pl.jakubwalczak.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jakubwalczak.voting.domain.model.Voter;

public interface VoterRepository extends JpaRepository<Voter, Long> { }
