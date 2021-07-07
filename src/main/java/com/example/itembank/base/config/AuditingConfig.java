package com.example.itembank.base.config;

import com.example.itembank.model.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider(){
        return new AuditorAwareImpl();
    }

    public static class AuditorAwareImpl implements AuditorAware<String>{
        @Override
        public Optional<String> getCurrentAuditor() {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //토큰에 User 객체 반환
            return Optional.of(user.getAccount());
        }
    }
}
