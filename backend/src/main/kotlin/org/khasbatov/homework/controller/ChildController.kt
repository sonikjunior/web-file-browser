package org.khasbatov.homework.controller

import org.khasbatov.homework.model.FileInfoDto
import org.khasbatov.homework.model.GetFileRequestDto
import org.khasbatov.homework.service.ChildService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ChildController(
    private val childService: ChildService
) : ChildApi {

    override fun getChilds(getFileRequestDto: GetFileRequestDto): ResponseEntity<List<FileInfoDto>> =
        ResponseEntity.ok(childService.getByPath(getFileRequestDto.path))
}
