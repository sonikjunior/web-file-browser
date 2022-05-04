package org.khasbatov.homework.error

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.khasbatov.homework.config.AppConfig
import org.khasbatov.homework.error.exception.FileIsNotFolderException
import org.khasbatov.homework.error.exception.FileNotFoundException
import org.khasbatov.homework.error.exception.ForbiddenArchiveException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.io.File
import java.nio.file.Files

@SpringJUnitConfig(classes = [AppConfig::class, FileValidator::class])
@TestPropertySource(
    properties = [
        "extensions.document: txt",
        "extensions.archive: zip",
        "extensions.picture: jpg",
        "allowed-unzipping-formats: zip",
        "forbidden-unzipping-formats: rar"
    ]
)
class FileValidatorTest {

    @Autowired
    lateinit var appConfig: AppConfig
    @Autowired
    lateinit var fileValidator: FileValidator

    @TempDir
    @JvmField
    var tempFolder: File? = null

    @Test
    fun validateFolder_fileNotFound() {
        val folder = File("")
        assertThrows(FileNotFoundException::class.java) { fileValidator.validateFolder(folder) }
    }

    @Test
    fun validateFolder_fileIsNotFolder() {
        val folder = Files.createFile(tempFolder!!.resolve("some_file.txt").toPath()).toFile()
        assertThrows(FileIsNotFolderException::class.java) { fileValidator.validateFolder(folder) }
    }

    @Test
    fun validateArchive_fileNotFound() {
        val archive = File("")
        assertThrows(FileNotFoundException::class.java) { fileValidator.validateArchive(archive) }
    }

    @Test
    fun validateArchive_forbiddenArchive() {
        val archive = Files.createFile(tempFolder!!.resolve("some_archive.rar").toPath()).toFile()
        assertThrows(ForbiddenArchiveException::class.java) { fileValidator.validateArchive(archive) }
    }
}
