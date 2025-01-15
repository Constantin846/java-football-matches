package tz.project.mapper;

import tz.project.dto.MatchDto;
import tz.project.model.Game;

public interface MatchDtoGameMapper {
    Game mapToGame(MatchDto matchDto);
}
