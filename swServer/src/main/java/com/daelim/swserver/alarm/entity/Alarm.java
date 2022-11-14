package com.daelim.swserver.alarm.entity;

import com.daelim.swserver.alarm.dto.AlarmDTO;
import com.daelim.swserver.mg.Days;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "tb_alarm")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Alarm {

    @Id
    private String alarmId;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @Column(nullable = false)
    private Days days;
    @Column(nullable = false)
    private boolean isRepeat;

    public AlarmDTO toDto() {
        AlarmDTO dto = new AlarmDTO();
        dto.setAlarmId(alarmId);
        dto.setStartTime(startTime);
        dto.setDays(days);
        dto.setRepeat(isRepeat);

        return dto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Alarm alarm = (Alarm) o;
        return alarmId != null && Objects.equals(alarmId, alarm.alarmId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
