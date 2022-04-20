package org.khasbatov.homework.error

import org.khasbatov.homework.error.exception.FileNotFoundException
import org.khasbatov.homework.error.exception.ForbiddenArchiveException
import org.khasbatov.homework.model.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler
    fun invocationException(e: Exception): ResponseEntity<ErrorResponseDto> {
        return when (e) {
            is FileNotFoundException -> buildError(HttpStatus.UNPROCESSABLE_ENTITY, "File not found")
            is ForbiddenArchiveException -> buildError(HttpStatus.UNPROCESSABLE_ENTITY, "Forbidden archive's extension")
            else -> buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong")
        }
    }

    private fun buildError(httpStatus: HttpStatus, message: String) =
        status(httpStatus).body(ErrorResponseDto(message))
}
