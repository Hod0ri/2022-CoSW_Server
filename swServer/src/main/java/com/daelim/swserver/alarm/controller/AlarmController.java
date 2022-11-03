package com.daelim.swserver.alarm.controller;

import com.daelim.swserver.alarm.dto.AlarmDTO;
import com.daelim.swserver.alarm.entity.Alarm;
import com.daelim.swserver.alarm.repository.AlarmRepository;
import com.daelim.swserver.auth.entity.User;
import com.daelim.swserver.auth.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonObject;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    @Operation(
            summary = "알람 조회",
            description = "알람 전체 조회"
    )
    @GetMapping("search")
    public String getAlarm(
            // @CookieValue(name = "userid") String userid
    ) throws JsonProcessingException {

        // Optional<User> user = userRepository.findById(userid);

        List<Alarm> alarms =  alarmRepository.findAll();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.writeValueAsString(alarms);

    }

    @Operation(
            summary = "알람 추가",
            description = "알람 추가 성공여부 상태 리턴 (success true/failed)"
    )
    @PostMapping("insert")
    public String insertAlarm(@RequestBody AlarmDTO alarmDTO) {
        JsonObject response = new JsonObject();
        log.info(alarmDTO.toString());
        Alarm alarm = alarmDTO.toEntity();
        log.info(alarm.toString());

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
