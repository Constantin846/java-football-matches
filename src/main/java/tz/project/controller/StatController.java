package tz.project.controller;

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
import tz.project.exceptions.NotMetDateFormatException;
import tz.project.model.Game;
import tz.project.service.StatService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatController {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String DATE_FORMAT_REGEX = "\\d\\d[.]\\d\\d[.]\\d\\d\\d\\d";
    private final StatService statService;

    @PostMapping("/match")
    @ResponseStatus(HttpStatus.CREATED)
    public Game addMatch(@Valid @RequestBody MatchDto matchDto) {
        log.info("Add new match {}", matchDto);
        return statService.addMatch(matchDto);
    }

    @GetMapping("/table")
    @ResponseStatus(HttpStatus.OK)
    public List<TeamStatDto> findStats(@RequestParam(value = "date", required = false) String dateStr) {
        LocalDate date;
        if (Objects.isNull(dateStr)) {
            date = LocalDate.now();
        } else if (!dateStr.matches(DATE_FORMAT_REGEX)) {
            String message = String.format("Wrong date format. Expected date format dd.MM.yyyy. " +
                            "Date is in request: %s", dateStr);
            log.warn(message);
            throw new NotMetDateFormatException(message);
        } else {
            date = LocalDate.parse(dateStr, FORMATTER);
        }
        log.info("Find stat table for date:{}", date);
        return statService.findStats(date);
    }
}
