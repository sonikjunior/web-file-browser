package org.khasbatov.homework.error

import org.khasbatov.homework.error.exception.FileNotFoundException
import org.khasbatov.homework.error.exception.ForbiddenArchiveException
import org.khasbatov.homework.config.AppConfig
import org.springframework.stereotype.Component
import java.io.File

@Component
class FileValidator(
    private val appConfig: AppConfig
) {

    fun validate(file: File) {
        if (!file.exists()) throw FileNotFoundException()
        if (file.isForbiddenArchive()) throw ForbiddenArchiveException()
    }

    private fun File.isForbiddenArchive() = appConfig.forbiddenUnzippingFormats.contains(extension)
}
