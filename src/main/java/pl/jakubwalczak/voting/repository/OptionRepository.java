package pl.jakubwalczak.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jakubwalczak.voting.domain.model.Option;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByElectionId(Long electionId);
}
