package io.wisoft.jpashop.controller.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.wisoft.jpashop.domain.favoritestore.FavoriteStore;
import io.wisoft.jpashop.domain.store.BusinessHours;
import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.store.StoreState;
import io.wisoft.jpashop.service.StoreService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.wisoft.jpashop.controller.api.ApiResult.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class StoreController {

    private final StoreService storeService;

    /**
     * 상점 목록 조회 API
     * @param localDateTime 영업중인지 판단을 위한 조회 시각
     * @return 영업 중인 상점 목록
     */
    @GetMapping("/stores")
    public ApiResult<List<StoreResponse>> findRunningStore(
            @RequestParam("time") @DateTimeFormat(pattern = "yyyyMMddHHmmss") LocalDateTime localDateTime) {

        List<Store> runningStores = storeService.findRunningStore(localDateTime);
        List<StoreResponse> results = new ArrayList<>();
        for (Store runningStore : runningStores) {
            StoreResponse storeResponse = new StoreResponse(runningStore);
            results.add(storeResponse);
        }

        return succeed(results);
    }

    @Getter
    @Setter
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

    @Getter
    @Setter
    private static class FavoriteStoreResponse {

        private Long id;
        private boolean open;

        @JsonProperty("store")
        private StoreResponse storeResponse;

        private LocalDateTime createdAt;

        public FavoriteStoreResponse(Long id, boolean open, StoreResponse storeResponse, LocalDateTime createdAt) {
            this.id = id;
            this.open = open;
            this.storeResponse = storeResponse;
            this.createdAt = createdAt;
        }

        public FavoriteStoreResponse(FavoriteStore source) {
            BeanUtils.copyProperties(source, this);
        }
    }

}
