package com.jotave.healthcare.infra.exceptions;

public record ValidationErrorDto(String field, String message) {
}
