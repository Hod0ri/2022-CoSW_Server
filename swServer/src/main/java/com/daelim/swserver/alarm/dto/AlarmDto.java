package com.daelim.swserver.alarm.dto;

import com.daelim.swserver.alarm.entity.Alarm;
import lombok.Data;
import java.time.LocalDateTime;


@Data
public class AlarmDto {

    private String alarmId;

    private LocalDateTime startTime;

    private int days;

    private boolean isRepeat;

}
