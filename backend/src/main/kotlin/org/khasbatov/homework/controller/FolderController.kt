package org.khasbatov.homework.controller

import org.khasbatov.homework.model.FileRequestDto
import org.khasbatov.homework.model.FileResponseDto
import org.khasbatov.homework.service.FolderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:3000/"])
@RestController
class FolderController(
    private val folderService: FolderService
) : FolderApi {

    override fun getContent(fileRequestDto: FileRequestDto): ResponseEntity<List<FileResponseDto>> =
        ResponseEntity.ok(folderService.getContent(fileRequestDto.path))
}
