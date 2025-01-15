package tz.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tz.project.dto.TeamStatDto;
import tz.project.model.GameOfTeam;

import java.sql.Date;
import java.util.List;

public interface GameOfTeamRepository extends JpaRepository<GameOfTeam, Long> {
    @Query(value = """
            select new tz.project.dto.TeamStatDto(gt.team, count(gt.id), sum(gt.points))
            from GameOfTeam as gt
            where gt.date <= :date
            group by gt.team
            order by sum(gt.points) desc
            """)
    List<TeamStatDto> findStatDtoBeforeDate(@Param("date") Date date);
}
