package org.khasbatov.homework.error

import org.khasbatov.homework.error.exception.FileNotFoundException
import org.khasbatov.homework.error.exception.ForbiddenArchiveException
import org.khasbatov.homework.model.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.IOException

@RestControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler
    fun invocationException(e: Exception): ResponseEntity<ErrorResponseDto> {
        return when (e) {
            is FileNotFoundException -> buildError(UNPROCESSABLE_ENTITY, "File not found")
            is ForbiddenArchiveException -> buildError(UNPROCESSABLE_ENTITY, "Unsupported archive's extension")
            is IOException -> buildError(INTERNAL_SERVER_ERROR, "Some problems while unzipping or reading files")
            else -> buildError(INTERNAL_SERVER_ERROR, "Something went wrong")
        }
    }

    private fun buildError(httpStatus: HttpStatus, message: String) =
        status(httpStatus).body(ErrorResponseDto(message))
}
