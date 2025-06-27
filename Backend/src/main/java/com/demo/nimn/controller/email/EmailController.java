package com.demo.nimn.controller.email;

import com.demo.nimn.dto.email.EmailDTO;
import com.demo.nimn.dto.email.EmailRequestDto;
import com.demo.nimn.service.email.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Email API", description = "이메일 인증 관련 API")
@RestController
@RequestMapping("/email")
public class EmailController {
    private final MailService mailService;
    private int number; // 이메일 인증 숫자를 저장하는 변수

    public EmailController(MailService mailService) {
        this.mailService = mailService;
    }

    @Operation(summary = "인증 이메일 전송", description = "해당 이메일로 인증 메일을 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    // 인증 이메일 전송
    @PostMapping("/send")
    public ResponseEntity<EmailDTO> mailSend(@RequestBody EmailRequestDto emailRequestDto, HttpSession session) {
        EmailDTO emailDTO;
        String email = emailRequestDto.getEmail();

        try {
            number = mailService.sendMail(email);
            session.setMaxInactiveInterval(60 * 3); // 세션 저장 시간 3분 설정
            session.setAttribute("email", email);
            session.setAttribute("authCode", number); // 이메일 인증 코드 세션에 저장
            String num = String.valueOf(number);

            emailDTO = EmailDTO.builder()
                    .email(email)
                    .isSuccess(true)
                    .message("인증번호 전송 성공")
                    .build();
            return ResponseEntity.ok(emailDTO);
        } catch (Exception e) {
            e.printStackTrace();
            emailDTO = EmailDTO.builder()
                    .email(email)
                    .isSuccess(false)
                    .message("인증번호 전송 실패")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailDTO);
        }
    }

    // 인증번호 일치여부 확인
    @Operation(summary = "인증번호 일치여부 확인", description = "인증번호 검증을 진행합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/check")
    public ResponseEntity<EmailDTO> mailCheck(
            @RequestBody EmailRequestDto requestDto,
            HttpSession session) {

        try {
            String email = (String) session.getAttribute("email");
            Object objCode = session.getAttribute("authCode");

            if (email == null || objCode == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        EmailDTO.builder()
                                .email(requestDto.getEmail())
                                .isSuccess(false)
                                .message("세션 정보가 존재하지 않습니다. (만료되었거나 전송되지 않음)")
                                .build()
                );
            }
            int code = (int) objCode;

            boolean isEmailMatch = requestDto.getEmail().equals(email);
            boolean isCodeMatch = requestDto.getCode() == code? true: false;

            if (!isEmailMatch) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        EmailDTO.builder()
                                .email(requestDto.getEmail())
                                .isSuccess(false)
                                .message("요청된 이메일이 일치하지 않습니다.")
                                .build()
                );
            }

            if (!isCodeMatch) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        EmailDTO.builder()
                                .email(requestDto.getEmail())
                                .isSuccess(false)
                                .message("인증번호가 일치하지 않습니다.")
                                .build()
                );
            }

            session.removeAttribute("email");
            session.removeAttribute("authCode");

            // 인증 성공
            return ResponseEntity.ok(
                    EmailDTO.builder()
                            .email(requestDto.getEmail())
                            .isSuccess(true)
                            .message("이메일 인증이 완료되었습니다.")
                            .build()
            );

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    EmailDTO.builder()
                    .email(requestDto.getEmail())
                    .isSuccess(false)
                    .message("에러 발생")
                    .build());
        }
    }


}
