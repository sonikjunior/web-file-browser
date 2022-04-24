package org.khasbatov.homework.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.khasbatov.homework.config.AppConfig
import org.khasbatov.homework.error.FileValidator
import org.khasbatov.homework.model.FileResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.io.File
import java.io.File.separator
import java.nio.file.Files

@SpringJUnitConfig(classes = [AppConfig::class, FolderService::class])
@TestPropertySource(
    properties = [
        "extensions.document: txt",
        "extensions.archive: zip",
        "extensions.picture: jpg"
    ]
)
class FolderServiceTest {
    @Autowired
    lateinit var appConfig: AppConfig
    @MockBean
    lateinit var fileValidator: FileValidator
    @Autowired
    lateinit var folderService: FolderService

    @TempDir
    @JvmField
    var tempFolder: File? = null

    @Test
    fun getContent() {
        val fileName = "some_file"
        val docName = "some_doc.txt"
        val archiveName = "some_archive.zip"
        val pictureName = "some_picture.jpg"
        val folderName = "some_folder"

        // Create temp file for each type
        Files.createFile(tempFolder!!.resolve(fileName).toPath())
        Files.createFile(tempFolder!!.resolve(docName).toPath())
        Files.createFile(tempFolder!!.resolve(archiveName).toPath())
        Files.createFile(tempFolder!!.resolve(pictureName).toPath())
        Files.createDirectory(tempFolder!!.resolve(folderName).toPath())

        val folderContent = folderService.getContent(tempFolder!!.path)

        // check file
        val fileDto = folderContent.first { it.type == FileResponseDto.Type.file }
        assertEquals(tempFolder!!.path + separator + fileName, fileDto.path)
        assertEquals(fileName, fileDto.name)
        assertEquals(FileResponseDto.Type.file, fileDto.type)

        // check document
        val docDto = folderContent.first { it.type == FileResponseDto.Type.document }
        assertEquals(tempFolder!!.path + separator + docName, docDto.path)
        assertEquals(docName, docDto.name)
        assertEquals(FileResponseDto.Type.document, docDto.type)

        // check archive
        val archiveDto = folderContent.first { it.type == FileResponseDto.Type.archive }
        assertEquals(tempFolder!!.path + separator + archiveName, archiveDto.path)
        assertEquals(archiveName, archiveDto.name)
        assertEquals(FileResponseDto.Type.archive, archiveDto.type)

        // check picture
        val pictureDto = folderContent.first { it.type == FileResponseDto.Type.picture }
        assertEquals(tempFolder!!.path + separator + pictureName, pictureDto.path)
        assertEquals(pictureName, pictureDto.name)
        assertEquals(FileResponseDto.Type.picture, pictureDto.type)

        // check folder
        val folderDto = folderContent.first { it.type == FileResponseDto.Type.folder }
        assertEquals(tempFolder!!.path + separator + folderName, folderDto.path)
        assertEquals(folderName, folderDto.name)
        assertEquals(FileResponseDto.Type.folder, folderDto.type)
    }
}
