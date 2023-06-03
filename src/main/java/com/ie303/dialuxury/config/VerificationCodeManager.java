package com.ie303.dialuxury.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class VerificationCodeManager {
    private final Map<String, String> verificationCodes = new HashMap<>();

    public void addVerificationCode(String email, String code) {
        verificationCodes.put(email, code);
    }

    public String getVerificationCode(String email) {
        return verificationCodes.get(email);
    }

    public void removeVerificationCode(String email) {
        verificationCodes.remove(email);
    }


}
