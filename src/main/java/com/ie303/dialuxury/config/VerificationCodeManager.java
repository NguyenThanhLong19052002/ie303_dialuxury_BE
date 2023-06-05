package com.ie303.dialuxury.config;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class VerificationCodeManager {
    private final Map<String, VerificationCode> verificationCodes = new HashMap<>();

    public void addVerificationCode(String email, String code) {
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5); // Thời gian hết hạn là 5 phút sau khi được thêm vào
        VerificationCode verificationCode = new VerificationCode(code, expirationTime);
        verificationCodes.put(email, verificationCode);
    }

    public String getVerificationCode(String email) {
        VerificationCode verificationCode = verificationCodes.get(email);
        if (verificationCode != null && !verificationCode.isExpired()) {
            return verificationCode.getCode();
        }
        return null;
    }

    public void removeVerificationCode(String email) {
        verificationCodes.remove(email);
    }

    private static class VerificationCode {
        private final String code;
        private final LocalDateTime expirationTime;

        public VerificationCode(String code, LocalDateTime expirationTime) {
            this.code = code;
            this.expirationTime = expirationTime;
        }

        public String getCode() {
            return code;
        }

        public boolean isExpired() {
            LocalDateTime now = LocalDateTime.now();
            return now.isAfter(expirationTime);
        }
    }
}
