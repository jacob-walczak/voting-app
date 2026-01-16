package pl.jakubwalczak.voting.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "voters")
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID externalId;

    @Setter
    @Column(nullable = false)
    private boolean active;

    public Voter(boolean active) {
        this.externalId = UUID.randomUUID();
        this.active = active;
    }
}

