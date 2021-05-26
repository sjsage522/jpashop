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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
@DisplayName("주문 API 테스트")
class OrderControllerTest {

    private MockMvc mockMvc;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("테스트 01. 주문목록 조회 테스트 (조회시각 2021-03-01 00:00:00 ~ 2021-03-02 00:00:00)")
    void _1_findOrdersApiTest() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/1/orders/between/20210301000000/and/20210302000000")
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", is(3)))
                // 첫번째 주문
                .andExpect(jsonPath("$.data[0].id", is(3)))
                .andExpect(jsonPath("$.data[0].storeId", is(1)))
                .andExpect(jsonPath("$.data[0].items").isArray())
                .andExpect(jsonPath("$.data[0].items.length()", is(3)))
                .andExpect(jsonPath("$.data[0].totalPrice", is(45000)))
                .andExpect(jsonPath("$.data[0].state", is("COMPLETE")))
                .andExpect(jsonPath("$.data[0].cancel").doesNotExist())
                .andExpect(jsonPath("$.data[0].completedAt").exists())
                // 두번째 주문
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].storeId", is(1)))
                .andExpect(jsonPath("$.data[1].items").isArray())
                .andExpect(jsonPath("$.data[1].items.length()", is(1)))
                .andExpect(jsonPath("$.data[1].totalPrice", is(10000)))
                .andExpect(jsonPath("$.data[1].state", is("CANCEL")))
                .andExpect(jsonPath("$.data[1].cancel").exists())
                .andExpect(jsonPath("$.data[1].cancel.message", is("expensive price")))
                .andExpect(jsonPath("$.data[1].cancel.time").exists())
                .andExpect(jsonPath("$.data[1].completedAt").doesNotExist())
                // 세번째 주문
                .andExpect(jsonPath("$.data[2].id", is(1)))
                .andExpect(jsonPath("$.data[2].storeId", is(1)))
                .andExpect(jsonPath("$.data[2].items").isArray())
                .andExpect(jsonPath("$.data[2].items.length()", is(2)))
                .andExpect(jsonPath("$.data[2].totalPrice", is(35000)))
                .andExpect(jsonPath("$.data[2].state", is("NEW")))
                .andExpect(jsonPath("$.data[2].cancel").doesNotExist())
                .andExpect(jsonPath("$.data[2].completedAt").doesNotExist());
    }
}