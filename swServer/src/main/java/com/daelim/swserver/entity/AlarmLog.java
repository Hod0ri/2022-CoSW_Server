package com.daelim.swserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_alarmlog")
public class AlarmLog {

    @Id
    public String logId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int days;
    private boolean isStopped;

}
