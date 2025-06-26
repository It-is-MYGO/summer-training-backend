package security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 实现从数据库加载用户的逻辑
        // 示例伪代码：
        // User user = userRepository.findByUsername(username)
        //     .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        
        // return new org.springframework.security.core.userdetails.User(
        //     user.getUsername(),
        //     user.getPassword(),
        //     user.getAuthorities()
        // );
        
        // 临时返回默认用户，避免编译错误
        return org.springframework.security.core.userdetails.User
            .withUsername(username)
            .password("$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG") // password
            .authorities("ROLE_USER")
            .build();
    }
}