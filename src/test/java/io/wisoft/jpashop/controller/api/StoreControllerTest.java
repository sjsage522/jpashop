package io.wisoft.jpashop.controller.api;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
@DisplayName("상점 API 테스트")
class StoreControllerTest {

    private MockMvc mockMvc;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("테스트 1. 정상 영업중인 상점 조회 테스트 (월요일 휴무 상점 제외, 조회시각 2021-03-01 09:00:00)")
    void _1_findRunningStoreApiTest() throws Exception {

        ResultActions result = mockMvc.perform(
                get("/api/stores?time=20210301090000")
                        .accept(MediaType.APPLICATION_JSON));

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", is(1)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("Normal Store1")))
                .andExpect(jsonPath("$.data[0].storeState", is("NORMAL")))
                .andExpect(jsonPath("$.data[0].businessHours").exists())
                .andExpect(jsonPath("$.data[0].businessHours.offDay", is(7)))
                .andExpect(jsonPath("$.data[0].businessHours.run24", is(false)))
                .andExpect(jsonPath("$.data[0].businessHours.openTime", is(540)))
                .andExpect(jsonPath("$.data[0].businessHours.closeTime", is(1080)));
    }

    @Test
    @DisplayName("테스트 2. 정상 영업중인 상점 조회 테스트 (영업 종료 상점 제외, 조회시각 2021-03-02 23:00:00)")
    void _2_findRunningStoreApiTest() throws Exception {

        ResultActions result = mockMvc.perform(
                get("/api/stores?time=20210302230000")
                        .accept(MediaType.APPLICATION_JSON));

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", is(1)))
                .andExpect(jsonPath("$.data[0].id", is(3)))
                .andExpect(jsonPath("$.data[0].name", is("Normal Store2")))
                .andExpect(jsonPath("$.data[0].storeState", is("NORMAL")))
                .andExpect(jsonPath("$.data[0].businessHours").exists())
                .andExpect(jsonPath("$.data[0].businessHours.offDay", is(1)))
                .andExpect(jsonPath("$.data[0].businessHours.run24", is(true)))
                .andExpect(jsonPath("$.data[0].businessHours.openTime", is(0)))
                .andExpect(jsonPath("$.data[0].businessHours.closeTime", is(0)));
    }

    @Test
    @DisplayName("테스트 3. 정상 영업중인 상점 조회 테스트 (휴무일과 영업종료 상점 제외, 조회시각 2021-03-01 23:00:00 )")
    void _3_findRunningStoreApiTest() throws Exception {

        ResultActions result = mockMvc.perform(
                get("/api/stores?time=20210301230000")
                        .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", is(0)))
        ;
    }
}