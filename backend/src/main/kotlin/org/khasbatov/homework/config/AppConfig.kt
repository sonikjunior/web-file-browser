package org.khasbatov.homework.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AppConfig {
    @Value("\${extensions.archive}") lateinit var archiveExtensions: Set<String>
    @Value("\${extensions.picture}") lateinit var pictureExtensions: Set<String>
    @Value("\${extensions.document}") lateinit var documentExtensions: Set<String>
    @Value("\${allowed-unzipping-formats}") lateinit var allowedUnzippingFormats: Set<String>
    @Value("\${forbidden-unzipping-formats}") lateinit var forbiddenUnzippingFormats: Set<String>
}
