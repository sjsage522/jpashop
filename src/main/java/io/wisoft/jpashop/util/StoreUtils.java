package io.wisoft.jpashop.util;

import io.wisoft.jpashop.domain.store.BusinessHours;

import java.time.LocalDateTime;

public class StoreUtils {

    public static boolean isNormalBusinessHours(LocalDateTime localDateTime, BusinessHours businessHours) {
        int convertedTime = convertTime(localDateTime);
        if ((!isHoliday(localDateTime, businessHours)) && (isRunning24(businessHours))) return true;
        if ((!isHoliday(localDateTime, businessHours)) && (isInTime(convertedTime, businessHours))) return true;
        return false;
    }

    private static boolean isInTime(int convertedTime, BusinessHours businessHours) {
        return businessHours.getCloseTime() >= convertedTime && businessHours.getOpenTime() <= convertedTime;
    }

    private static boolean isRunning24(BusinessHours businessHours) {
        return businessHours.isRun24();
    }

    private static boolean isHoliday(LocalDateTime localDateTime, BusinessHours businessHours) {
        return businessHours.getOffDay() == localDateTime.getDayOfWeek().getValue();
    }

    private static int convertTime(LocalDateTime localDateTime) {
        return (localDateTime.getHour() * 60) + (localDateTime.getMinute());
    }
}
