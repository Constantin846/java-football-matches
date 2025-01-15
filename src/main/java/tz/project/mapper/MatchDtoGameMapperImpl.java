package tz.project.mapper;

import org.springframework.stereotype.Component;
import tz.project.dto.MatchDto;
import tz.project.model.Game;
import tz.project.model.GameOfTeam;

import java.sql.Date;

@Component
public class MatchDtoGameMapperImpl implements MatchDtoGameMapper {
    @Override
    public Game mapToGame(MatchDto matchDto) {
        Game game = new Game();
        game.setHomeTeamInfo(getHomeTeamInfo(matchDto));
        game.setAwayTeamInfo(getAwayTeamInfo(matchDto));
        return game;
    }

    private GameOfTeam getHomeTeamInfo(MatchDto matchDto) {
        GameOfTeam gameOfTeam = new GameOfTeam();
        gameOfTeam.setTeam(matchDto.getHomeTeam());
        gameOfTeam.setDate(Date.valueOf(matchDto.getLocalDate()));
        gameOfTeam.setPoints(determinePoints(matchDto, true));
        return gameOfTeam;
    }

    private GameOfTeam getAwayTeamInfo(MatchDto matchDto) {
        GameOfTeam gameOfTeam = new GameOfTeam();
        gameOfTeam.setTeam(matchDto.getAwayTeam());
        gameOfTeam.setDate(Date.valueOf(matchDto.getLocalDate()));
        gameOfTeam.setPoints(determinePoints(matchDto, false));
        return gameOfTeam;
    }

    private static final int POINTS_FOR_WIN = 3;
    private static final int POINTS_FOR_DRAW = 1;
    private static final int POINTS_FOR_LOSS = 0;

    private int determinePoints(MatchDto matchDto, boolean homeTeam) {
        int firstTeamScore;
        int secondTeamScore;

        if (homeTeam) {
            firstTeamScore = matchDto.getScoreHomeTeam();
            secondTeamScore = matchDto.getScoreAwayTeam();
        } else {
            firstTeamScore = matchDto.getScoreAwayTeam();
            secondTeamScore = matchDto.getScoreHomeTeam();
        }

        if (firstTeamScore > secondTeamScore) {
            return POINTS_FOR_WIN;
        } else if (firstTeamScore == secondTeamScore) {
            return POINTS_FOR_DRAW;
        } else {
            return POINTS_FOR_LOSS;
        }
    }
}
