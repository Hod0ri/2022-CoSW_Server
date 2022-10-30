package com.daelim.swserver.alarm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_alarm")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {

    @Id
    private String alarmId;

    private LocalDateTime startTime;
    private int days;
    private boolean isRepeat;

}
