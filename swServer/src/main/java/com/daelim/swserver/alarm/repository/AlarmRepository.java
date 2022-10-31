package com.daelim.swserver.alarm.repository;

import com.daelim.swserver.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
}
