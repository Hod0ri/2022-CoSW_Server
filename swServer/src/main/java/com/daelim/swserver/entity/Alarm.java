package com.daelim.swserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_alarm")
public class Alarm {

    @Id
    private String alarmId;

    private LocalDateTime startTime;
    private int days;
    private boolean isRepeat;

    @OneToMany
    private List<AlarmLog> log;

}
