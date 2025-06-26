package modules.auth.controller;

import modules.auth.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sms")
public class SmsController {
    
    private final SmsService smsService;
    
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }
    
    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendVerificationCode(@RequestParam String phone) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = smsService.sendVerificationCode(phone);
            if (success) {
                response.put("success", true);
                response.put("message", "验证码发送成功");
            } else {
                response.put("success", false);
                response.put("message", "验证码发送失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "发送验证码时发生错误: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyCode(@RequestParam String phone, @RequestParam String code) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean isValid = smsService.verifyCode(phone, code);
            response.put("success", true);
            response.put("valid", isValid);
            response.put("message", isValid ? "验证码验证成功" : "验证码验证失败");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "验证验证码时发生错误: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
}
