package com.daelim.swserver.alarm.dto;

import com.daelim.swserver.alarm.entity.Alarm;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AlarmDTO {

    private LocalDateTime startTime;
    private int days;
    private boolean isRepeat;

    public Alarm toEntity() {
        String uuid = UUID.randomUUID().toString();

        return Alarm.builder()
                .alarmId(uuid)
                .startTime(startTime)
                .days(days)
                .isRepeat(isRepeat)
                .build();
    }

}
