package tz.project.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tz.project.dto.MatchDto;
import tz.project.dto.TeamStatDto;
import tz.project.model.Game;
import tz.project.service.StatService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;

    @PostMapping("/match")
    @ResponseStatus(HttpStatus.CREATED)
    public Game addMatch(@Valid @RequestBody MatchDto matchDto) {
        log.info("Add new match {}", matchDto);
        return statService.addMatch(matchDto);
    }

    @GetMapping("/table")
    @ResponseStatus(HttpStatus.OK)
    public List<TeamStatDto> findStats(
                @RequestParam(value = "date", required = false)
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
                LocalDate date) {
        if (Objects.isNull(date)) {
            date = LocalDate.now();
        }
        log.info("Find stat table for date:{}", date);
        return statService.findStats(date);
    }
}
