package io.wisoft.jpashop.controller.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
@DisplayName("즐겨찾기 상점 API 테스트")
class FavoriteStoreControllerTest {

    private MockMvc mockMvc;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("테스트 01. 즐겨찾기 상점목록 조회 테스트 (영업중인 상점은 open 필드가 true, 조회시각 2021-03-01 09:00:00)")
    void _01_findFavoriteStoreApiTest() throws Exception {

        ResultActions result = mockMvc.perform(
                get("/api/user/1/favorite/stores?time=20210301090000")
                        .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", is(2)))
                .andExpect(jsonPath("$.data[0].id", is(2)))
                .andExpect(jsonPath("$.data[0].open", is(false)))
                .andExpect(jsonPath("$.data[0].store").exists())
                .andExpect(jsonPath("$.data[0].store.id", is(2)))
                .andExpect(jsonPath("$.data[0].store.name", is("Hidden Store")))
                .andExpect(jsonPath("$.data[0].store.storeState", is("HIDDEN")))
                .andExpect(jsonPath("$.data[0].store.businessHours").exists())
                .andExpect(jsonPath("$.data[0].store.businessHours.offDay", is(7)))
                .andExpect(jsonPath("$.data[0].store.businessHours.run24", is(false)))
                .andExpect(jsonPath("$.data[0].store.businessHours.openTime", is(540)))
                .andExpect(jsonPath("$.data[0].store.businessHours.closeTime", is(1080)))
                .andExpect(jsonPath("$.data[1].id", is(1)))
                .andExpect(jsonPath("$.data[1].open", is(true)))
                .andExpect(jsonPath("$.data[1].store").exists())
                .andExpect(jsonPath("$.data[1].store.id", is(1)))
                .andExpect(jsonPath("$.data[1].store.name", is("Normal Store1")))
                .andExpect(jsonPath("$.data[1].store.storeState", is("NORMAL")))
                .andExpect(jsonPath("$.data[1].store.businessHours").exists())
                .andExpect(jsonPath("$.data[1].store.businessHours.offDay", is(7)))
                .andExpect(jsonPath("$.data[1].store.businessHours.run24", is(false)))
                .andExpect(jsonPath("$.data[1].store.businessHours.openTime", is(540)))
                .andExpect(jsonPath("$.data[1].store.businessHours.closeTime", is(1080)));
    }

    @Test
    @DisplayName("테스트 02. 즐겨찾기 상점 추가 성공 테스트")
    void _02_addFavoriteStoreApiTest() throws Exception {

        ResultActions result = mockMvc.perform(
                post("/api/user/1/store/3/favorite")
                        .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.store.id", is(3)))
                .andExpect(jsonPath("$.data.store.name", is("Normal Store2")))
                .andExpect(jsonPath("$.data.store.storeState", is("NORMAL")))
                .andExpect(jsonPath("$.data.store.businessHours").exists())
                .andExpect(jsonPath("$.data.store.businessHours.offDay", is(1)))
                .andExpect(jsonPath("$.data.store.businessHours.run24", is(true)))
                .andExpect(jsonPath("$.data.store.businessHours.openTime", is(0)))
                .andExpect(jsonPath("$.data.store.businessHours.closeTime", is(0)))
                .andExpect(jsonPath("$.data.createdAt").exists())
        ;
        // 즐겨찾기 추가 후 목록 조회
        ResultActions after = mockMvc.perform(
                get("/api/user/1/favorite/stores?time=20210301230000")
                        .accept(MediaType.APPLICATION_JSON)
        );
        after.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", is(3)))
                // 첫번째 즐겨찾기 상점
                .andExpect(jsonPath("$.data[0].id", is(3)))
                .andExpect(jsonPath("$.data[0].store.name", is("Normal Store2")))
                // 두번째 즐겨찾기 상점
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].store.name", is("Hidden Store")))
                // 세번째 즐겨찾기 상점
                .andExpect(jsonPath("$.data[2].id", is(1)))
                .andExpect(jsonPath("$.data[2].store.name", is("Normal Store1")))
        ;

    }

    @Test
    @DisplayName("테스트 03. 즐겨찾기 상점 추가 실패 테스트 (이미 즐겨찾기에 추가된 상점)")
    void _03_addFavoriteStoreApiTest() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/user/1/store/2/favorite")
                        .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.errorMessage").exists())
        ;
    }

    @Test
    @DisplayName("테스트 04. 즐겨찾기 상점 삭제 성공 테스트")
    void _04_deleteFavoriteStoreApiTest() throws Exception {
        ResultActions result = mockMvc.perform(
                delete("/api/user/1/store/2/favorite")
                        .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(true)));
    }

    @Test
    @DisplayName("테스트 05. 즐겨찾기 상점 삭제 실패 테스트 (존재하지 않는 상점)")
    void _05_deleteFavoriteStoreApiTest() throws Exception {
        ResultActions result = mockMvc.perform(
                delete("/api/user/1/store/12345/favorite")
                        .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.errorMessage").exists());
    }
}