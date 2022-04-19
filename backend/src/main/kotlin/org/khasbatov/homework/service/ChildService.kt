package org.khasbatov.homework.service

import org.khasbatov.homework.error.exception.FileNotFoundException
import org.khasbatov.homework.error.exception.ForbiddenArchiveException
import org.khasbatov.homework.model.FileInfoDto
import org.khasbatov.homework.util.*
import org.springframework.stereotype.Service
import java.io.File

@Service
class ChildService(
    private val appProps: AppProps
) {

    fun getByPath(path: String): List<FileInfoDto> {
        val file = File(path)
        if (!file.exists()) throw FileNotFoundException()


        if (file.isArchive()) {
            if (file.isForbiddenArchive()) throw ForbiddenArchiveException()
            if (file.isAllowedArchive()) {
                // walk inside archive
            }
        }

        val result = ArrayList<FileInfoDto>()
        if (file.isDirectory) {
            file.walk()
                .maxDepth(1)
                .filter { !it.absolutePath.equals(path) }
                .forEach {
                    result.add(
                        FileInfoDto(
                            it.absolutePath,
                            it.name,
                            defineType(it)
                        )
                    )
                }
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

    private fun File.isArchive() = appProps.archiveExtensions.contains(extension)
    private fun File.isPicture() = appProps.pictureExtensions.contains(extension)
    private fun File.isDocument() = appProps.documentExtensions.contains(extension)
    private fun File.isAllowedArchive() = appProps.allowedUnzippingFormats.contains(extension)
    private fun File.isForbiddenArchive() = appProps.forbiddenUnzippingFormats.contains(extension)
}
