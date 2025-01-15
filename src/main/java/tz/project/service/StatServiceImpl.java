package tz.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tz.project.dto.MatchDto;
import tz.project.dto.TeamStatDto;
import tz.project.exceptions.NotFoundException;
import tz.project.mapper.MatchDtoGameMapper;
import tz.project.model.Game;
import tz.project.model.GameOfTeam;
import tz.project.repository.GameOfTeamRepository;
import tz.project.repository.GameRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final GameRepository gameRepository;
    private final GameOfTeamRepository gameOfTeamRepository;
    private final MatchDtoGameMapper mapper;

    @Transactional
    @Override
    public Game addMatch(MatchDto matchDto) {
        Game game = mapper.mapToGame(matchDto);
        return saveGame(game);
    }

    @Override
    public List<TeamStatDto> findStats(LocalDate date) {
        return gameOfTeamRepository.findStatDtoBeforeDate(Date.valueOf(date));
    }

    private Game saveGame(Game game) {
        game.setHomeTeamInfo(saveGameOfTeam(game.getHomeTeamInfo()));
        game.setAwayTeamInfo(saveGameOfTeam(game.getAwayTeamInfo()));
        game = gameRepository.save(game);
        return findGameById(game.getId());
    }

    private Game findGameById(long gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> {
            String message = String.format("Game was not found by gameId: %s", gameId);
            log.warn(message);
            return new NotFoundException(message);
        });
    }

    private GameOfTeam saveGameOfTeam(GameOfTeam gameOfTeam) {
        return gameOfTeamRepository.save(gameOfTeam);
    }
}
