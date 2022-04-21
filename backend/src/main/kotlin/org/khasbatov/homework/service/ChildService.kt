package org.khasbatov.homework.service

import org.khasbatov.homework.config.AppConfig
import org.khasbatov.homework.error.FileValidator
import org.khasbatov.homework.helper.UnzipHelper
import org.khasbatov.homework.model.FileInfoDto
import org.springframework.stereotype.Service
import java.io.File
import java.io.File.separator
import java.nio.file.Files
import java.util.stream.Collectors

@Service
class ChildService(
    private val appConfig: AppConfig,
    private val unzipHelper: UnzipHelper,
    private val fileValidator: FileValidator
) {

    fun getByPath(path: String): List<FileInfoDto> {
        var updatedPath = path
        if (path.hasArchive()) {
            updatedPath = swapPathForArchives(path)
        }
        val file = File(updatedPath)
        fileValidator.validate(file)

        if (file.isDirectory) {
            return walk(file)
        }

        if (file.isAllowedArchive()) {
            val unzipDirName = "_" + file.name
            val unzipDirFile = File(file.parent + separator + unzipDirName)

            if (!unzipDirFile.exists()) {
                Files.createDirectory(unzipDirFile.toPath())
                unzipHelper.unzip(file, unzipDirFile.path)
            }
            return walk(unzipDirFile, unzipDirName, file.name)
        }
        return emptyList()
    }

    private fun swapPathForArchives(path: String) : String {
        val paths = path.split(separator).toMutableList()
        val indexesForReplace = ArrayList<Int>()
        paths.forEachIndexed{ index, value ->
            run {
                appConfig.allowedUnzippingFormats.forEach { zipFormat ->
                    run {
                        if (value.contains(zipFormat)) {
                            indexesForReplace.add(index)
                        }
                    }
                }
            }
        }
        indexesForReplace.forEach {
            val zipFileName = paths[it].toString()
            paths[it] = zipFileName.replace(zipFileName, "_$zipFileName")
        }
        return paths.stream().collect(Collectors.joining(separator))
    }

    private fun walk(file: File, unzipDirName: String = "", zipName: String = ""): ArrayList<FileInfoDto> {
        val result = ArrayList<FileInfoDto>()
        file.walk()
            .maxDepth(1)
            .filter { it.absolutePath != file.absolutePath }
            .forEach {
                result.add(
                    FileInfoDto(
                        it.absolutePath.replace(unzipDirName, zipName),
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

    private fun String.hasArchive(): Boolean {
        var result = false
        appConfig.allowedUnzippingFormats.forEach {
            if (this.contains(it)) result = true
        }
        return result
    }

    private fun File.isArchive() = appConfig.archiveExtensions.contains(extension)
    private fun File.isPicture() = appConfig.pictureExtensions.contains(extension)
    private fun File.isDocument() = appConfig.documentExtensions.contains(extension)
    private fun File.isAllowedArchive() = appConfig.allowedUnzippingFormats.contains(extension)
}
