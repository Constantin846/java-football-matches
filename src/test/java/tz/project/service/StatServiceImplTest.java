package tz.project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tz.project.dto.MatchDto;
import tz.project.dto.TeamStatDto;
import tz.project.exceptions.NotFoundException;
import tz.project.mapper.MatchDtoGameMapper;
import tz.project.mapper.MatchDtoGameMapperImpl;
import tz.project.model.Game;
import tz.project.repository.GameOfTeamRepository;
import tz.project.repository.GameRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatServiceImplTest {
    @Mock
    private GameOfTeamRepository gameOfTeamRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private MatchDtoGameMapper mapper;
    @InjectMocks
    private StatServiceImpl statService;

    @Test
    void addMatch() {
        MatchDto matchDto = createMatchDto();
        Game game = createGame(matchDto);
        game.setId(1L);

        when(mapper.toGame(matchDto)).thenReturn(game);
        when(gameOfTeamRepository.save(game.getHomeTeamInfo())).thenReturn(game.getHomeTeamInfo());
        when(gameOfTeamRepository.save(game.getAwayTeamInfo())).thenReturn(game.getAwayTeamInfo());
        when(gameRepository.save(game)).thenReturn(game);
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));

        Game actualGame = statService.addMatch(matchDto);

        assertEquals(game, actualGame);
    }

    @Test
    void addMatch_gameNotFoundException() {
        MatchDto matchDto = createMatchDto();
        Game game = createGame(matchDto);
        game.setId(1L);

        when(mapper.toGame(matchDto)).thenReturn(game);
        when(gameOfTeamRepository.save(game.getHomeTeamInfo())).thenReturn(game.getHomeTeamInfo());
        when(gameOfTeamRepository.save(game.getAwayTeamInfo())).thenReturn(game.getAwayTeamInfo());
        when(gameRepository.save(game)).thenReturn(game);
        when(gameRepository.findById(game.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> statService.addMatch(matchDto));
    }

    @Test
    void findStats() {
        LocalDate date = LocalDate.of(2012, 12, 12);
        TeamStatDto teamStatDto = new TeamStatDto("Team", 5, 11);
        List<TeamStatDto> expectedList = List.of(teamStatDto);

        when(gameOfTeamRepository.findStatDtoForDate(Date.valueOf(date))).thenReturn(expectedList);

        List<TeamStatDto> actualList = statService.findStats(date);

        assertEquals(expectedList, actualList);
    }

    private MatchDto createMatchDto() {
        MatchDto matchDto = new MatchDto();
        matchDto.setLocalDate(LocalDate.of(2012, 12, 12));
        matchDto.setHomeTeam("Home");
        matchDto.setAwayTeam("Away");
        matchDto.setScoreHomeTeam(5);
        matchDto.setScoreAwayTeam(3);
        return matchDto;
    }

    private Game createGame(MatchDto matchDto) {
        MatchDtoGameMapper mapper = new MatchDtoGameMapperImpl();
        return mapper.toGame(matchDto);
    }
}