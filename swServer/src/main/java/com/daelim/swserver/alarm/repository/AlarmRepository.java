package com.daelim.swserver.alarm.repository;

import com.daelim.swserver.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    boolean findByuserId(Integer userId);
}
