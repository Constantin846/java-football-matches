package tz.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tz.project.dto.MatchDto;
import tz.project.mapper.MatchDtoGameMapper;
import tz.project.mapper.MatchDtoGameMapperImpl;
import tz.project.model.Game;
import tz.project.service.StatService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class StatControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StatService statService;

    @SneakyThrows
    @Test
    void addMatch() {
        MatchDto matchDto = new MatchDto();
        matchDto.setLocalDate(LocalDate.of(2012, 12, 12));
        matchDto.setHomeTeam("Home");
        matchDto.setAwayTeam("Away");
        matchDto.setScoreHomeTeam(5);
        matchDto.setScoreAwayTeam(3);
        MatchDtoGameMapper mapper = new MatchDtoGameMapperImpl();
        Game game = mapper.toGame(matchDto);
        when(statService.addMatch(matchDto)).thenReturn(game);

        String result = mockMvc.perform(post("/match")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(matchDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(game), result);
    }

    @SneakyThrows
    @Test
    void findStats() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateStr = "12.12.2012";
        LocalDate date = LocalDate.parse(dateStr, formatter);

        mockMvc.perform(get(String.format("/table?date=%s", dateStr)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(statService).findStats(date);
    }

    @SneakyThrows
    @Test
    void findStats_withoutParam() {
        mockMvc.perform(get("/table"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(statService).findStats(LocalDate.now());
    }

    @SneakyThrows
    @Test
    void findStats_notMetDateFormat() {
        String dateStr = "2.12.2012";

        mockMvc.perform(get(String.format("/table?date=%s", dateStr)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}