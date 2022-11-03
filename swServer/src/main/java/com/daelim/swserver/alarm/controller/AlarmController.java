package com.daelim.swserver.alarm.controller;

import com.daelim.swserver.alarm.dto.AlarmDTO;
import com.daelim.swserver.alarm.entity.Alarm;
import com.daelim.swserver.alarm.repository.AlarmRepository;
import com.daelim.swserver.auth.entity.User;
import com.daelim.swserver.auth.repository.UserRepository;
import com.google.gson.JsonObject;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;




@Component
@Slf4j
@RestController
@RequestMapping("alarm")
public class AlarmController {
    private AlarmRepository alarmRepository;
    private UserRepository userRepository;

    @Autowired
    public AlarmController(AlarmRepository alarmRepository, UserRepository userRepository){
        this.alarmRepository = alarmRepository;
        this.userRepository = userRepository;
    }


    @Operation(
            summary = "알람 조회",
            description = "알람 전체 조회"
    )
    @GetMapping("search")
    public String getAlarm(@CookieValue(name = "userid") String userid) {
        JsonObject response = new JsonObject();

        Optional<User> user = userRepository.findById(userid);

        List<Alarm> alarms =  alarmRepository.findAll();

        return response.toString();

    }

    @Operation(
            summary = "알람 추가",
            description = "알람 추가 성공여부 상태 리턴 (success true/failed)"
    )
    @PostMapping("insert")
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
