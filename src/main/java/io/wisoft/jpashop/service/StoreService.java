package io.wisoft.jpashop.service;

import io.wisoft.jpashop.domain.store.BusinessHours;
import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.store.StoreState;
import io.wisoft.jpashop.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.wisoft.jpashop.util.StoreUtils.isNormalBusinessHours;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public List<Store> findRunningStore(final LocalDateTime localDateTime) {

        /* 정상 영업중인 상점들을 우선 조회 */
        List<Store> normalStores = storeRepository.findByState(StoreState.NORMAL);

        List<Store> results = new ArrayList<>();
        for (Store normalStore : normalStores) {
            BusinessHours businessHours = normalStore.getBusinessHours();

            /* 영업 시간으로 현재 상점이 운영하는지 확인 */
            if (isNormalBusinessHours(localDateTime, businessHours)) results.add(normalStore);
        }

        return results;
    }
}
