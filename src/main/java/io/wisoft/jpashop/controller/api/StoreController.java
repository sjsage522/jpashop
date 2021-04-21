package io.wisoft.jpashop.controller.api;

import io.wisoft.jpashop.domain.store.BusinessHours;
import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.store.StoreState;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class StoreController {

    private static class StoreResponse {

        private Long id;
        private String name;
        private StoreState storeState;
        private BusinessHours businessHours;
        private LocalDateTime createdAt;

        protected StoreResponse() {
        }

        public StoreResponse(Store source) {
            BeanUtils.copyProperties(source, this);
        }
    }
}
