package com.daelim.swserver.alarm.dto;

import com.daelim.swserver.alarm.entity.Alarm;
import com.daelim.swserver.mg.Days;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
public class AlarmDTO {

    @Nullable
    @ApiModelProperty(value = "알람 ID")
    private String alarmId;
    @ApiModelProperty(value = "알람 설정시간")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @ApiModelProperty(value = "알람 설정요일")
    private List<Days> days;
    @ApiModelProperty(value = "알람 반복여부")
    private boolean isRepeat;

    public Alarm create() {
        String uuid = UUID.randomUUID().toString();

        return Alarm.builder()
                .alarmId(uuid)
                .startTime(startTime)
                .days(days)
                .isRepeat(isRepeat)
                .build();
    }

    public Alarm toEntity() {
        if (alarmId == null)
            alarmId = UUID.randomUUID().toString();

        return Alarm.builder()
                .alarmId(alarmId)
                .startTime(startTime)
                .days(days)
                .isRepeat(isRepeat)
                .build();
    }

}
