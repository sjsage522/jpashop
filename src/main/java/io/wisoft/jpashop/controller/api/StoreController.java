package io.wisoft.jpashop.controller.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.wisoft.jpashop.domain.favoritestore.FavoriteStore;
import io.wisoft.jpashop.domain.store.BusinessHours;
import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.store.StoreState;
import io.wisoft.jpashop.service.FavoriteStoreService;
import io.wisoft.jpashop.service.StoreService;
import io.wisoft.jpashop.util.StoreUtils;
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
    private final FavoriteStoreService favoriteStoreService;

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

    /**
     * 즐겨찾기 상점목록 조회 API
     * @param userId 즐겨찾기를 등록한 사용자 id
     * @param localDateTime 영업중인지 판단을 위한 조회시각
     * @return 즐겨찾기에 추가한 상점 목록
     */
    @GetMapping("/user/{userId}/favorite/stores")
    public ApiResult<List<FavoriteStoreResponse>> findFavoriteStoreByUserId(
            @PathVariable Long userId,
            @RequestParam("time") @DateTimeFormat(pattern = "yyyyMMddHHmmss") LocalDateTime localDateTime) {

        List<FavoriteStore> favoriteStores = favoriteStoreService.findByUserId(userId);
        return succeed(getFavoriteStoreResponseList(favoriteStores, localDateTime));
    }

    /**
     * 즐겨찾기 상점 추가 API
     * @param userId 즐겨찾기를 등록할 사용자 id
     * @param storeId 즐겨찾기에 추가할 상점 id
     * @return 즐겨찾기 결과
     */
    @PostMapping("/user/{userId}/store/{storeId}/favorite")
    public ApiResult<FavoriteStoreResponse> addFavoriteStore(
            @PathVariable Long userId, @PathVariable Long storeId) {
        FavoriteStore favoriteStore = favoriteStoreService.addFavoriteStore(userId, storeId);
        return succeed(new FavoriteStoreResponse(favoriteStore));
    }

    /**
     * 즐겨 찾기 상점 삭제 API
     * @param userId 즐겨찾기를 삭제할 사용자 id
     * @param storeId 즐겨찾기에서 삭제할 상점 id
     * @return data 필드가 true 일 때, 삭제 성공
     */
    @DeleteMapping("user/{userId}/store/{storeId}/favorite")
    public ApiResult<Boolean> deleteFavoriteStore(
            @PathVariable Long userId, @PathVariable Long storeId) {
        int result = favoriteStoreService.deleteFavoriteStore(userId, storeId);
        return result == 1 ? succeed(true) : failed("Is not favorite store.");
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
    @JsonPropertyOrder({"id", "open", "store", "createdAt"})
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
            storeResponse = new StoreResponse(source.getStore());
        }
    }

    private List<FavoriteStoreResponse> getFavoriteStoreResponseList(List<FavoriteStore> favoriteStores, LocalDateTime localDateTime) {

        List<FavoriteStoreResponse> results = new ArrayList<>();
        for (FavoriteStore favoriteStore : favoriteStores) {
            boolean isOpen = true;

            Store store = favoriteStore.getStore();
            if ((store.getStoreState() != StoreState.NORMAL) || (!StoreUtils.isNormalBusinessHours(localDateTime, store.getBusinessHours())))
                isOpen = false;

            results.add(new FavoriteStoreResponse(store.getId(), isOpen, new StoreResponse(store), favoriteStore.getCreatedAt()));
        }
        return results;
    }
}
