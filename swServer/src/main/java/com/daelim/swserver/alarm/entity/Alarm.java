package com.daelim.swserver.alarm.entity;

import com.daelim.swserver.mg.Days;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "tb_alarm")
@Data
@Builder
@NoArgsConstructor
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

    //ManyToOne
    //@JoinColumn(name = "USER_ID")
    //private User user;

}
