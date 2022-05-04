package org.khasbatov.homework.controller

import org.khasbatov.homework.model.FileRequestDto
import org.khasbatov.homework.model.FileResponseDto
import org.khasbatov.homework.service.ArchiveService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:3000/"])
@RestController
class ArchiveController(
    private val archiveService: ArchiveService
) : ArchiveApi {

    override fun unzipArchive(fileRequestDto: FileRequestDto): ResponseEntity<FileResponseDto> =
        ResponseEntity.ok(archiveService.unzip(fileRequestDto.path))
}
