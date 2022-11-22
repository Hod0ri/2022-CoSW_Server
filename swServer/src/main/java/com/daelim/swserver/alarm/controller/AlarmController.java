package com.daelim.swserver.alarm.controller;

import com.daelim.swserver.alarm.dto.AlarmDTO;
import com.daelim.swserver.alarm.entity.Alarm;
import com.daelim.swserver.alarm.repository.AlarmRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flipkart.zjsonpatch.JsonPatch;
import com.flipkart.zjsonpatch.JsonPatchApplicationException;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("alarm")
@RequiredArgsConstructor
public class AlarmController {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private final AlarmRepository alarmRepository;

    @Operation(
            summary = "알람 조회",
            description = "알람 전체 조회"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회된 엔티티", response = AlarmDTO.class),
    })
    @GetMapping
    public ResponseEntity<String> getAlarm() throws JsonProcessingException {
        List<Alarm> alarms = alarmRepository.findAll();

        return ResponseEntity.ok(mapper.writeValueAsString(alarms));
    }

    @Operation(
            summary = "알람 추가",
            description = "간단한 상태"
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성 성공"),
            @ApiResponse(code = 400, message = "예외 발생")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<String> insertAlarm(@RequestBody @NotNull AlarmDTO alarmDTO) {
        log.info(alarmDTO.toString());
        Alarm alarm = alarmDTO.create();
        log.info(alarm.toString());

        try {
            alarmRepository.save(alarm);

            JsonObject response = new JsonObject();
            response.addProperty("message", "alarm created success: " + alarmDTO.getAlarmId());

            return new ResponseEntity<>(response.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            JsonObject response = new JsonObject();
            response.addProperty("message", e.getMessage());

            return ResponseEntity.badRequest().body(response.toString());
        }
    }

    @Operation(
            summary = "알람 삭제",
            description = "알람 삭제"
    )
    @ApiResponses({
            @ApiResponse(code = 204, message = "삭제 성공"),
            @ApiResponse(code = 422, message = "alarmId 의 알람이 존재하지 않음")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public ResponseEntity<String> deleteAlarm(@RequestParam String alarmId) {
        if (alarmRepository.existsById(alarmId)) {
            alarmRepository.deleteById(alarmId);

            return ResponseEntity.noContent().build();
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "alarm with id %s is not exists".formatted(alarmId));

            return ResponseEntity.unprocessableEntity().body(jsonObject.toString());
        }
    }

    @Operation(
            summary = "알람 데이터 업데이트",
            description = "RFC6902(https://www.rfc-editor.org/rfc/rfc6902) 에 따른 Patch 구현"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "업데이트 성공"),
            @ApiResponse(code = 400, message = "업데이트 실패, 데이터에 문제 감지"),
            @ApiResponse(code = 422, message = "같은 id의 알람을 찾지 못함")
    })
    @PatchMapping
    public ResponseEntity<String> updateAlarm(@NotNull String alarmId,
                                              @NotNull String patch) {
        if (alarmRepository.existsById(alarmId)) {
            try {
                applyPatch(alarmId, patch);
            } catch (JsonPatchApplicationException | JsonProcessingException e) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("message", "failed to patch %s".formatted(alarmId));
                jsonObject.addProperty("exception", e.getMessage());
                jsonObject.addProperty("data", patch);

                return ResponseEntity.badRequest().body(jsonObject.toString());
            }

            return ResponseEntity.ok().build();
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "alarm with id %s is not exists".formatted(alarmId));
            return ResponseEntity.unprocessableEntity()
                    .body(jsonObject.toString());
        }
    }

    private void applyPatch(@NotNull String alarmId,
                            @NotNull String toPatchString) throws JsonProcessingException {
        AlarmDTO originDto = alarmRepository.findById(alarmId).orElseThrow().toDto();
        String originString = mapper.writeValueAsString(originDto);

        final JsonNode origin = mapper.readValue(originString, JsonNode.class);
        final JsonNode patch = mapper.readValue(toPatchString, JsonNode.class);

        AlarmDTO toSave = mapper.treeToValue(JsonPatch.apply(patch, origin), AlarmDTO.class);
        log.info(toSave.toString());

        alarmRepository.saveAndFlush(toSave.toEntity());
    }

}
