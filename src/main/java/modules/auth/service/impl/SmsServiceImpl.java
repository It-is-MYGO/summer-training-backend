package modules.auth.service.impl;

import modules.auth.service.SmsService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {
    
    private final StringRedisTemplate redisTemplate;
    private final Random random = new Random();
    
    public SmsServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @Override
    public boolean sendVerificationCode(String phone) {
        try {
            // 生成6位随机验证码
            String code = String.format("%06d", random.nextInt(1000000));
            
            // 将验证码存储到Redis，设置5分钟过期
            String key = "sms:code:" + phone;
            redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
            
            // TODO: 这里应该调用实际的短信服务API
            // 目前只是模拟发送成功
            System.out.println("向手机号 " + phone + " 发送验证码: " + code);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean verifyCode(String phone, String code) {
        try {
            String key = "sms:code:" + phone;
            String storedCode = redisTemplate.opsForValue().get(key);
            
            if (storedCode != null && storedCode.equals(code)) {
                // 验证成功后删除验证码
                redisTemplate.delete(key);
                return true;
            }
            
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
} 