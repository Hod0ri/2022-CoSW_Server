package com.daelim.swserver.alarm.repository;

import com.daelim.swserver.alarm.entity.Alarm;
import com.daelim.swserver.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, String> {

}