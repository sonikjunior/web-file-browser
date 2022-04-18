package org.khasbatov.homework.error

import org.khasbatov.homework.model.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler
    fun invocationException(e: RuntimeException): ResponseEntity<ErrorResponseDto> {
        return status(HttpStatus.UNPROCESSABLE_ENTITY).body(ErrorResponseDto("File not found"))
    }
}
