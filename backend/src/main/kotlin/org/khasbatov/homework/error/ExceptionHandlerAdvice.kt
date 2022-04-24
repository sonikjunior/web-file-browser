package org.khasbatov.homework.error

import org.khasbatov.homework.error.exception.FileIsNotFolderException
import org.khasbatov.homework.error.exception.FileNotFoundException
import org.khasbatov.homework.error.exception.ForbiddenArchiveException
import org.khasbatov.homework.model.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
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
            is FileNotFoundException -> buildError(NOT_FOUND, "File not found")
            is ForbiddenArchiveException -> buildError(UNPROCESSABLE_ENTITY, "Unsupported archive's extension")
            is FileIsNotFolderException -> buildError(UNPROCESSABLE_ENTITY, "File is not folder")
            is IOException -> buildError(INTERNAL_SERVER_ERROR, "Some problems while unzipping or reading files")
            else -> buildError(INTERNAL_SERVER_ERROR, "Something went wrong")
        }
    }

    private fun buildError(httpStatus: HttpStatus, message: String) =
        status(httpStatus).body(ErrorResponseDto(message))
}
