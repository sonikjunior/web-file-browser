package org.khasbatov.homework.helper

import org.springframework.stereotype.Component
import java.io.File
import java.io.File.separator
import java.io.IOException
import java.nio.file.Files
import java.util.zip.ZipEntry
import java.util.zip.ZipFile


@Component
class UnzipHelper {

    fun unzip(file: File, path: String) {
        try {
            ZipFile(file).use { zipFile ->
                zipFile.entries()
                    .asIterator()
                    .forEachRemaining { entry: ZipEntry? ->
                        if (entry != null) {
                            val fileOrDirToCreate = file.toPath().resolve(path + separator + entry.name)
                            if (entry.isDirectory) {
                                Files.createDirectory(fileOrDirToCreate)
                            } else {
                                Files.copy(zipFile.getInputStream(entry), fileOrDirToCreate)
                            }
                        }
                    }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
