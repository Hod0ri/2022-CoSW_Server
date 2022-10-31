package com.daelim.swserver.alarm.dto;

import com.daelim.swserver.alarm.entity.Alarm;
import lombok.Data;
import java.time.LocalDateTime;


@Data
public class AlarmDTO {

    private LocalDateTime startTime;

    private int days;

    private boolean isRepeat;

    public Alarm toEntity() {
        Alarm alarm = Alarm.builder()
                .startTime(startTime)
                .days(days)
                .isRepeat(isRepeat)
                .build();

        return alarm;
    }

}
