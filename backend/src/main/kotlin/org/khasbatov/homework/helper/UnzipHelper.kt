package org.khasbatov.homework.helper

import org.springframework.stereotype.Component
import java.io.File
import java.io.File.separator
import java.nio.file.Files
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

@Component
class UnzipHelper {

    fun unzip(file: File, targetPath: String) {
        ZipFile(file).use { zipFile ->
            zipFile.entries()
                .asIterator()
                .forEachRemaining { entry: ZipEntry? ->
                    if (entry != null) {
                        val fileOrDirToUnzip = file.toPath().resolve(targetPath + separator + entry.name)
                        if (entry.isDirectory) {
                            Files.createDirectory(fileOrDirToUnzip)
                        } else {
                            Files.copy(zipFile.getInputStream(entry), fileOrDirToUnzip)
                        }
                    }
                }
        }
    }
}
