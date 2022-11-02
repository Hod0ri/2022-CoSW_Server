package com.daelim.swserver.alarm.controller;

import com.daelim.swserver.alarm.dto.AlarmDTO;
import com.daelim.swserver.alarm.entity.Alarm;
import com.daelim.swserver.alarm.repository.AlarmRepository;
import com.daelim.swserver.auth.entity.User;
import com.daelim.swserver.auth.repository.UserRepository;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("alarm")
public class AlarmController {

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("alarm")
    public String getAlarm(@CookieValue(name = "userid") String userid) {
        JsonObject response = new JsonObject();

        Optional<User> user = userRepository.findById(userid);

        List<Alarm> alarms =  alarmRepository.findAllByUser(user.get());

        return response.toString();

    }

    @PostMapping("alarm")
    public String insertAlarm(@RequestBody AlarmDTO alarmDTO) {
        JsonObject response = new JsonObject();

        Alarm alarm = alarmDTO.toEntity();

        try{
            Alarm saveAlarm = alarmRepository.save(alarm);
            response.addProperty("success", "true");
        }catch (Exception e) {
            response.addProperty("success", "failed");
            response.addProperty("message", e.getMessage());
        }

        return response.toString();

    }


}
