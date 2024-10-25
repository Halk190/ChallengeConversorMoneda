package com.challenge.models;

public record InformacionDivisasRecord(String base_code,
                                       String target_code,
                                       Double conversion_rate,
                                       Double conversion_result) {
}
