package com.daelim.swserver.alarm.entity;

import com.daelim.swserver.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_alarm")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "ALARM_SEQ_GENERATOR",
        sequenceName = "ALARM_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALARM_SEQ_GENERATOR")
    private Integer alarmId;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private int days;
    @Column(nullable = false)
    private boolean isRepeat;

    //ManyToOne
    //@JoinColumn(name = "USER_ID")
    //private User user;

}
