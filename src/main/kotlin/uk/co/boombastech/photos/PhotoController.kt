package uk.co.boombastech.photos

import org.apache.commons.io.IOUtils
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File

@RestController
@RequestMapping("/api/photos")
class PhotoController(val photoRepository: PhotoRepository) {

    private val imageExtensions = listOf("jpg")

    @GetMapping
    fun searchPhotos(searchCriteria: SearchCriteria): SearchResults {
        return photoRepository.find(searchCriteria)
    }

    @PostMapping
    @ResponseStatus(value = NO_CONTENT)
    fun updatePhotos(@RequestBody photoInfos: List<PhotoInfo>) {
        photoRepository.save(photoInfos)
    }

    @GetMapping
    @RequestMapping("/image/{filename:.+}", produces = arrayOf(MediaType.IMAGE_JPEG_VALUE))
    fun getPhoto(@PathVariable filename: String): ResponseEntity<Any> {
        val image = File("src/main/resources/static/$filename")

        if (image.exists() && imageExtensions.contains(image.extension)) {
            return ResponseEntity.ok(IOUtils.toByteArray(image.inputStream()))
        }

        return ResponseEntity.notFound().build()
    }
}