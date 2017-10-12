package uk.co.boombastech.photos

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds.ENTRY_CREATE
import java.nio.file.WatchService
import java.util.*


//@Component
class NewFileScheduler(private val photoRepository: PhotoRepository, @Value("%{photos.path}") private val photoPath: String) {

    private final val LOG = LoggerFactory.getLogger(NewFileScheduler::class.java)

    private final val watcher: WatchService = FileSystems.getDefault().newWatchService()

//    @Scheduled(cron = "*/5 * * * * *")
    fun checkForNewFiles() {
        try {
            LOG.info("Looking for new files")

            watcher.poll()?.pollEvents()
                    ?.filter { event -> event.kind() === ENTRY_CREATE }
                    ?.map { event -> event.context() as Path }
                    ?.map { path -> PhotoInfo(filename = path.toString(), uploadDate = Date()) }
                    ?.let(photoRepository::save)

            watcher.poll()?.reset()
        } catch (e: InterruptedException) {
            throw RuntimeException("Exception thrown trying to ")
        }
    }

    init {
        File(photoPath).toPath().register(watcher, ENTRY_CREATE)
    }
}