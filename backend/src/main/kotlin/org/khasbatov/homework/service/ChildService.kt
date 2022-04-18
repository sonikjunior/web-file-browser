package org.khasbatov.homework.service

import org.khasbatov.homework.error.exception.FileNotFoundException
import org.khasbatov.homework.model.FileInfoDto
import org.springframework.stereotype.Service
import java.io.File

@Service
class ChildService {

    fun getByPath(path: String): List<FileInfoDto> {

        val result = ArrayList<FileInfoDto>()
        val file = File(path)

        if (!file.exists()) {
            throw FileNotFoundException()
        }

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

        return result
    }

    private fun defineType(file: File): FileInfoDto.Type {
        return if (file.isFile) {
            if ("zip" == file.extension) {
                FileInfoDto.Type.archive
            } else {
                FileInfoDto.Type.file
            }
        } else {
            FileInfoDto.Type.directory
        }
    }
}
