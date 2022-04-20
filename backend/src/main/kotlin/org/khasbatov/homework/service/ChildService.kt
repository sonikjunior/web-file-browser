package org.khasbatov.homework.service

import org.khasbatov.homework.error.FileValidator
import org.khasbatov.homework.model.FileInfoDto
import org.khasbatov.homework.config.AppConfig
import org.khasbatov.homework.helper.UnzipHelper
import org.springframework.stereotype.Service
import java.io.File
import java.io.File.separator
import java.nio.file.Files

@Service
class ChildService(
    private val appConfig: AppConfig,
    private val unzipHelper: UnzipHelper,
    private val fileValidator: FileValidator
) {

    fun getByPath(path: String): List<FileInfoDto> {
        val file = File(path)
        fileValidator.validate(file)

        if (file.isDirectory) {
            return walk(file)
        }

        if (file.isAllowedArchive()) {
            val unzipDirName = file.pureName()
            val unzipDirFile = File(file.parent + separator + unzipDirName)

            if (!unzipDirFile.exists()) {
                Files.createDirectory(unzipDirFile.toPath())
                unzipHelper.unzip(file, unzipDirFile.path)
            }
            return walk(unzipDirFile)
        }
        return emptyList()
    }

    private fun walk(file: File): ArrayList<FileInfoDto> {
        val result = ArrayList<FileInfoDto>()
        file.walk()
            .maxDepth(1)
            .filter { it.absolutePath != file.absolutePath }
            .forEach {
                result.add(
                    FileInfoDto(
                        it.absolutePath,
                        it.name,
                        defineType(it)
                    )
                )
            }
        return result
    }

    private fun defineType(file: File): FileInfoDto.Type {
        return when {
            file.isDirectory -> FileInfoDto.Type.directory
            file.isArchive() -> FileInfoDto.Type.archive
            file.isPicture() -> FileInfoDto.Type.picture
            file.isDocument() -> FileInfoDto.Type.document
            else -> FileInfoDto.Type.file
        }
    }

    private fun File.pureName() = name.dropLast(extension.length + 1)
    private fun File.isArchive() = appConfig.archiveExtensions.contains(extension)
    private fun File.isPicture() = appConfig.pictureExtensions.contains(extension)
    private fun File.isDocument() = appConfig.documentExtensions.contains(extension)
    private fun File.isAllowedArchive() = isArchive() && appConfig.allowedUnzippingFormats.contains(extension)
}
