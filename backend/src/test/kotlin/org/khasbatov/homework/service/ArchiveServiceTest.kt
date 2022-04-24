package org.khasbatov.homework.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.khasbatov.homework.error.FileValidator
import org.khasbatov.homework.helper.UnzipHelper
import org.khasbatov.homework.model.FileResponseDto
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.io.File
import java.io.File.separator

@ExtendWith(MockitoExtension::class)
class ArchiveServiceTest {
    @Mock
    lateinit var unzipHelper: UnzipHelper
    @Mock
    lateinit var fileValidator: FileValidator
    @InjectMocks
    lateinit var archiveService: ArchiveService

    @TempDir
    @JvmField
    var tempFolder: File? = null

    @Test
    fun unzip() {
        val archivePath = tempFolder?.path + separator + "some_archive.zip"
        val unzippedFolder = archiveService.unzip(archivePath)

        val expectedFolderPath = tempFolder?.path + separator + "some_archive"
        assertEquals(expectedFolderPath, unzippedFolder.path)
        assertEquals("some_archive", unzippedFolder.name)
        assertEquals(FileResponseDto.Type.folder, unzippedFolder.type)
    }
}
