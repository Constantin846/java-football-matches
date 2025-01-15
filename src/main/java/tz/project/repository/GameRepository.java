package tz.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.project.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}
