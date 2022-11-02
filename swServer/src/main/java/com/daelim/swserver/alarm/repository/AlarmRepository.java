package com.daelim.swserver.alarm.repository;

import com.daelim.swserver.alarm.entity.Alarm;
import com.daelim.swserver.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
<<<<<<< HEAD
    boolean findByuserId(Integer userId);
=======
    List<Alarm> findAllByUser(User user);
>>>>>>> develop
}
