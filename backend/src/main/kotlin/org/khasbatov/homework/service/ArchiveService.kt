package org.khasbatov.homework.service

import org.khasbatov.homework.error.FileValidator
import org.khasbatov.homework.helper.UnzipHelper
import org.khasbatov.homework.model.FileResponseDto
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files

@Service
class ArchiveService(
    private val unzipHelper: UnzipHelper,
    private val fileValidator: FileValidator
) {

    fun unzip(path: String): FileResponseDto {
        val file = File(path)
        fileValidator.validateArchive(file)

        var unzipFolder = File(generateUnzipFolderPath(file))

        var i = 0
        while (unzipFolder.exists()) {
            unzipFolder = File(generateUnzipFolderPath(file) + "_" + i++)
        }
        Files.createDirectory(unzipFolder.toPath())
        unzipHelper.unzip(file, unzipFolder.path)

        return FileResponseDto(
            unzipFolder.path,
            unzipFolder.name,
            FileResponseDto.Type.folder
        )
    }

    private fun generateUnzipFolderPath(file: File): String =
        file.path.dropLast(file.extension.length + 1)
}
