package org.khasbatov.homework.service

import org.khasbatov.homework.config.AppConfig
import org.khasbatov.homework.error.FileValidator
import org.khasbatov.homework.model.FileResponseDto
import org.springframework.stereotype.Service
import java.io.File

@Service
class FolderService(
    private val appConfig: AppConfig,
    private val fileValidator: FileValidator
) {

    fun getContent(path: String): List<FileResponseDto> {
        val file = File(path)
        fileValidator.validateFolder(file)

        val content = ArrayList<FileResponseDto>()
        file.walk()
            .maxDepth(1)
            .filter { it.absolutePath != file.absolutePath }
            .forEach {
                content.add(
                    FileResponseDto(
                        it.absolutePath,
                        it.name,
                        defineType(it)
                    )
                )
            }
        return content
    }

    private fun defineType(file: File): FileResponseDto.Type {
        return when {
            file.isDirectory -> FileResponseDto.Type.folder
            file.isArchive() -> FileResponseDto.Type.archive
            file.isPicture() -> FileResponseDto.Type.picture
            file.isDocument() -> FileResponseDto.Type.document
            else -> FileResponseDto.Type.file
        }
    }

    private fun File.isArchive() = appConfig.archiveExtensions.contains(extension)
    private fun File.isPicture() = appConfig.pictureExtensions.contains(extension)
    private fun File.isDocument() = appConfig.documentExtensions.contains(extension)
}
