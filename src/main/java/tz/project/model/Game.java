package tz.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "games")
@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "home_team_id", referencedColumnName = "id", nullable = false, unique = true)
    GameOfTeam homeTeamInfo;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "away_team_id", referencedColumnName = "id", nullable = false, unique = true)
    GameOfTeam awayTeamInfo;
}
