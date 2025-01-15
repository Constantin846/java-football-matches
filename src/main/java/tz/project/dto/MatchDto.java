package tz.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchDto {
    @Length(message = "Team name length must be between 1 and 64 characters inclusive", min = 1, max = 64)
    String season;

    @NotNull(message = "Local date must be set.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    LocalDate localDate;

    @NotBlank(message = "Field home team must not be blank.")
    @Length(message = "Team name length must be between 1 and 64 characters inclusive", min = 1, max = 64)
    String homeTeam;

    @NotBlank(message = "Field away team must not be blank.")
    @Length(message = "Team name length must be between 1 and 64 characters inclusive", min = 1, max = 64)
    String awayTeam;

    @PositiveOrZero(message = "Count of goals must not be negative.")
    int scoreHomeTeam;

    @PositiveOrZero(message = "Count of goals must not be negative.")
    int scoreAwayTeam;
}
