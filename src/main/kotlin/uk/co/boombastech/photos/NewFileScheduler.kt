package uk.co.boombastech.photos

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.*
import java.nio.file.StandardWatchEventKinds.*
import java.util.*


@Component
class NewFileScheduler(private val photoRepository: PhotoRepository, @Value("%{photos.path}") private val photoPath: String) {

    private final val LOG = LoggerFactory.getLogger(NewFileScheduler::class.java)

    private final val watcher: WatchService = FileSystems.getDefault().newWatchService()

    @Scheduled(cron = "*/5 * * * * *")
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