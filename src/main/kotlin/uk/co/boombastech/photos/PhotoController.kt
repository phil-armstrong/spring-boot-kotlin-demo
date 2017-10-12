package uk.co.boombastech.photos

import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import java.io.File

@RestController
@RequestMapping("/api/photos")
class PhotoController(val photoRepository: PhotoRepository, @Value("%{photos.path}") val photoPath: String) {

    private val imageExtensions = listOf("jpg")

    @GetMapping
    fun searchPhotos(searchCriteria: SearchCriteria): SearchResults {
        return photoRepository.find(searchCriteria)
    }

    @PostMapping
    @ResponseStatus(value = NO_CONTENT)
    fun updatePhotos(@RequestBody photoInfo: List<PhotoInfo>) {
        photoRepository.save(photoInfo)
    }

    @GetMapping
    @RequestMapping("/image/{filename:.+}", produces = arrayOf(MediaType.IMAGE_JPEG_VALUE))
    fun getPhoto(@PathVariable filename: String): ResponseEntity<Any> {
        val image = File("$photoPath/$filename")

        if (image.exists() && imageExtensions.contains(image.extension)) {
            return ok(IOUtils.toByteArray(image.inputStream()))
        }

        return ResponseEntity.notFound().build()
    }
}