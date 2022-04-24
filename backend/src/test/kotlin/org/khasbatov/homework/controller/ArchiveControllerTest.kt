package org.khasbatov.homework.controller

import org.junit.jupiter.api.Test
import org.khasbatov.homework.error.exception.FileNotFoundException
import org.khasbatov.homework.error.exception.ForbiddenArchiveException
import org.khasbatov.homework.model.FileResponseDto
import org.khasbatov.homework.service.ArchiveService
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(ArchiveController::class)
class ArchiveControllerTest(
    @Autowired val mockMvc: MockMvc
) {

    @MockBean
    lateinit var archiveService: ArchiveService

    private val UNZIP_URL = "/archive/unzip"
    private val ARCHIVE_PATH = "/Users/i.ivanov/Downloads/some_archive.zip"
    private val REQUEST_BODY = "{\"path\": \"$ARCHIVE_PATH\"}"

    @Test
    fun unzipArchive_ok() {
        `when`(archiveService.unzip(ARCHIVE_PATH)).thenReturn(getUnzippedFolder())

        mockMvc.perform(post(UNZIP_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(REQUEST_BODY))
            .andExpect(status().isOk)
            .andExpect(content().json(
                "{" +
                    "    \"path\": \"/Users/i.ivanov/Downloads/some_archive\"," +
                    "    \"name\": \"some_archive\"," +
                    "    \"type\": \"folder\"" +
                    "}"))

        verify(archiveService).unzip(ARCHIVE_PATH)
    }

    @Test
    fun unzipArchive_fileNotFound() {
        `when`(archiveService.unzip(ARCHIVE_PATH)).thenThrow(FileNotFoundException())

        mockMvc.perform(post(UNZIP_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(REQUEST_BODY))
            .andExpect(status().isNotFound)
            .andExpect(content().json("{\"message\": \"File not found\"}"))

        verify(archiveService).unzip(ARCHIVE_PATH)
    }

    @Test
    fun unzipArchive_forbiddenArchive() {
        `when`(archiveService.unzip(ARCHIVE_PATH)).thenThrow(ForbiddenArchiveException())

        mockMvc.perform(post(UNZIP_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(REQUEST_BODY))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().json("{\"message\": \"Unsupported archive's extension\"}"))

        verify(archiveService).unzip(ARCHIVE_PATH)
    }

    private fun getUnzippedFolder() =
         FileResponseDto(
             "/Users/i.ivanov/Downloads/some_archive",
             "some_archive",
             FileResponseDto.Type.folder
        )
}
