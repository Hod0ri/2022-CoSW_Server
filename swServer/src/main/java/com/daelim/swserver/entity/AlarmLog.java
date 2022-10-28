package com.daelim.swserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class AlarmLog {

    @Id
    public String logId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int days;
    private boolean isStopped;

}
