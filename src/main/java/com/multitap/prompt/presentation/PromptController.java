package com.multitap.prompt.presentation;

import com.multitap.prompt.application.PromptService;
import com.multitap.prompt.common.exception.BaseException;
import com.multitap.prompt.common.response.BaseResponse;
import com.multitap.prompt.dto.in.PromptRequestDto;
import com.multitap.prompt.dto.in.RetrievePromptRequestDto;
import com.multitap.prompt.dto.out.PromptResponseDto;
import com.multitap.prompt.vo.in.RetrievePromptRequestVo;
import com.multitap.prompt.vo.out.PromptDetailsResponseVo;
import com.multitap.prompt.vo.out.PromptResponseVo;
import com.multitap.prompt.vo.in.PromptRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/prompt")
public class PromptController {

    private final PromptService promptService;

    @Operation(summary = "피드백을 위한 프롬프트 등록", description = "프롬프트 등록")
    @PostMapping()
    public BaseResponse<Void> addPrompt(@RequestBody PromptRequestVo promptRequestVo) {
        promptService.addPrompt(PromptRequestDto.from(promptRequestVo));
        return new BaseResponse<>();
    }

    @Operation(summary = "피드백을 위한 프롬프트 조회", description = "프롬프트 조회")
    @GetMapping()
    public BaseResponse<List<PromptResponseVo>> getPrompt() {
        List<PromptResponseVo> promptResponseVoList = promptService.getPromptList()
                .stream()
                .map(PromptResponseDto::toVo)
                .toList();
        return new BaseResponse<>(promptResponseVoList);
    }

    @Operation(summary = "피드백을 위한 프롬프트 수정", description = "프롬프트 수정")
    @PutMapping("/{id}")
    public BaseResponse<Void> getPrompt(@PathVariable String id, @RequestBody PromptRequestVo promptRequestVo) {
        promptService.changePrompt(PromptRequestDto.from(promptRequestVo), id);
        return new BaseResponse<>();
    }

    @Operation(summary = "피드백을 위한 프롬프트 AI 피드백 서비스로 전달", description = "프롬프트 전달")
    @PostMapping("/send")
    public BaseResponse<PromptDetailsResponseVo> sendPromptDetails(@RequestBody RetrievePromptRequestVo retrievePromptRequestVo) {
        return new BaseResponse<>(promptService.setPromptDetails(RetrievePromptRequestDto.from(retrievePromptRequestVo)).toVo());

    }

}