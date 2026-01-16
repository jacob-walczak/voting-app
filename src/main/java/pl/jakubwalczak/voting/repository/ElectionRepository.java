package pl.jakubwalczak.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jakubwalczak.voting.domain.model.Election;

public interface ElectionRepository extends JpaRepository<Election, Long> { }
