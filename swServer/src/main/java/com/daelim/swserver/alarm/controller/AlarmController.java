package com.daelim.swserver.alarm.controller;

import com.daelim.swserver.alarm.entity.Alarm;
import com.daelim.swserver.alarm.repository.AlarmRepository;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("alarms")
public class AlarmController {

    @Autowired
    private AlarmRepository alarmRepository;


    @GetMapping("searchalarm")
    @ResponseBody
    public String searchalarm(@RequestParam (value = "id")Integer alarmId)  {
        JsonObject response = new JsonObject();

        alarmRepository.findById(alarmId);
        response.addProperty("complete", "true");

        return response.toString();
    }


    @PostMapping("addalarm")
    @ResponseBody
    public String addalarm(@RequestParam (value = "id")Integer alarmId,@RequestParam (value = "starttime") LocalDateTime startTime, @RequestParam(value = "days")Integer days)  {
        JsonObject response = new JsonObject();

        Alarm alarm = Alarm.builder().alarmId(alarmId).startTime(startTime).days(days).build();
        alarmRepository.save(alarm);

        response.addProperty("success", "true");

        return response.toString();

    }

    @GetMapping("updatealarm")
    @ResponseBody
    public String updatealarm(@RequestParam (value = "id")int alarmId,@RequestParam (value = "starttime") LocalDateTime startTime, @RequestParam(value = "days")int days){

        Optional<Alarm> alarm = alarmRepository.findById(alarmId);

        JsonObject response = new JsonObject();

        if(alarm.isPresent()){
            response.addProperty("empty","failed");
        }else {
            alarmRepository.save(Alarm.builder().alarmId(alarmId).startTime(startTime).days(days).build());
            response.addProperty("sucess","true");
            return response.toString();
        }

        return response.toString();
    }

    @DeleteMapping("deletealarm")
    @ResponseBody
    public String deletealarm(@RequestParam (value = "id")Integer alarmId){
        JsonObject response = new JsonObject();

        Optional<Alarm> alarm = alarmRepository.findById(alarmId);

        if(alarm.isPresent()){
            response.addProperty("empty","failed");
        }else {
            alarmRepository.deleteById(alarmId);
            response.addProperty("succes", "true");
            return response.toString();
        }
        return response.toString();
    }


}
