package org.khasbatov.homework.error

import org.khasbatov.homework.error.exception.FileNotFoundException
import org.khasbatov.homework.error.exception.ForbiddenArchiveException
import org.khasbatov.homework.config.AppConfig
import org.khasbatov.homework.error.exception.FileIsNotFolderException
import org.springframework.stereotype.Component
import java.io.File

@Component
class FileValidator(
    private val appConfig: AppConfig
) {

    fun validateFolder(file: File) {
        if (!file.exists()) throw FileNotFoundException()
        if (!file.isDirectory) throw FileIsNotFolderException()
    }

    fun validateArchive(file: File) {
        if (!file.exists()) throw FileNotFoundException()
        if (file.isForbiddenArchive() || !file.isAllowedArchive()) throw ForbiddenArchiveException()
    }

    private fun File.isForbiddenArchive() = appConfig.forbiddenUnzippingFormats.contains(extension)
    private fun File.isAllowedArchive() = appConfig.allowedUnzippingFormats.contains(extension)
}
