package tz.project.service;

import tz.project.dto.MatchDto;
import tz.project.dto.TeamStatDto;
import tz.project.model.Game;

import java.time.LocalDate;
import java.util.List;

public interface StatService {
    Game addMatch(MatchDto matchDto);

    List<TeamStatDto> findStats(LocalDate date);
}
