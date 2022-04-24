package org.khasbatov.homework.controller

import org.junit.jupiter.api.Test
import org.khasbatov.homework.error.exception.FileIsNotFolderException
import org.khasbatov.homework.error.exception.FileNotFoundException
import org.khasbatov.homework.model.FileResponseDto
import org.khasbatov.homework.service.FolderService
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@WebMvcTest(FolderController::class)
class FolderControllerTest(
    @Autowired val mockMvc: MockMvc
) {

    @MockBean
    lateinit var folderService: FolderService

    private val GET_CONTENT_URL = "/folder/content"
    private val FOLDER_PATH = "/Users/i.ivanov/Downloads"
    private val REQUEST_BODY = "{\"path\": \"$FOLDER_PATH\"}"

    @Test
    fun getContent_ok() {
        `when`(folderService.getContent(FOLDER_PATH)).thenReturn(getFolderContent())

        mockMvc.perform(post(GET_CONTENT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(REQUEST_BODY))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(content().json(
                "[" +
                        "   {" +
                        "       \"path\": \"/Users/i.ivanov/Downloads/some_archive.zip\"," +
                        "       \"name\": \"some_archive.zip\"," +
                        "       \"type\": \"archive\"" +
                        "   }," +
                        "   {" +
                        "        \"path\": \"/Users/i.ivanov/Downloads/some_archive\"," +
                        "        \"name\": \"some_archive\"," +
                        "        \"type\": \"folder\"" +
                        "    }," +
                        "    {" +
                        "        \"path\": \"/Users/i.ivanov/Downloads/some_doc.pdf\"," +
                        "        \"name\": \"some_doc.pdf\"," +
                        "        \"type\": \"document\"" +
                        "    }," +
                        "    {" +
                        "        \"path\": \"/Users/i.ivanov/Downloads/some_picture.jpg\"," +
                        "        \"name\": \"some_picture.jpg\"," +
                        "        \"type\": \"picture\"" +
                        "    }" +
                        "]"
            ))

        verify(folderService).getContent(FOLDER_PATH)
    }

    @Test
    fun getContent_ok_emptyFolder() {
        `when`(folderService.getContent(FOLDER_PATH)).thenReturn(emptyList())

        mockMvc.perform(post(GET_CONTENT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(REQUEST_BODY))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(content().json("[]"))

        verify(folderService).getContent(FOLDER_PATH)
    }

    @Test
    fun getContent_fileNotFound() {
        `when`(folderService.getContent(FOLDER_PATH)).thenThrow(FileNotFoundException())

        mockMvc.perform(post(GET_CONTENT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(REQUEST_BODY))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(content().json("{\"message\": \"File not found\"}"))

        verify(folderService).getContent(FOLDER_PATH)
    }

    @Test
    fun getContent_fileIsNotFolder() {
        `when`(folderService.getContent(FOLDER_PATH)).thenThrow(FileIsNotFolderException())

        mockMvc.perform(post(GET_CONTENT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(REQUEST_BODY))
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
            .andExpect(content().json("{\"message\": \"File is not folder\"}"))

        verify(folderService).getContent(FOLDER_PATH)
    }

    private fun getFolderContent() =
        listOf(
            FileResponseDto(
                "/Users/i.ivanov/Downloads/some_archive.zip",
                "some_archive.zip",
                FileResponseDto.Type.archive
            ),
            FileResponseDto(
                "/Users/i.ivanov/Downloads/some_archive",
                "some_archive",
                FileResponseDto.Type.folder
            ),
            FileResponseDto(
                "/Users/i.ivanov/Downloads/some_doc.pdf",
                "some_doc.pdf",
                FileResponseDto.Type.document
            ),
            FileResponseDto(
                "/Users/i.ivanov/Downloads/some_picture.jpg",
                "some_picture.jpg",
                FileResponseDto.Type.picture
            )
        )
}
