package uk.co.boombastech.photos

import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrServerException
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*

@SpringBootApplication
@EnableScheduling
class Application {

    private val LOG = LoggerFactory.getLogger(Application::class.java)

    @Bean
    fun init(solrClient: SolrClient, photoRepository: PhotoRepository) = CommandLineRunner {
        try {
            val photo1Upload = Calendar.getInstance()
            val photo1Taken = Calendar.getInstance()
            photo1Upload.set(2017,9,24)
            photo1Taken.set(2017,9,23)

            val photo2Upload = Calendar.getInstance()
            val photo2Taken = Calendar.getInstance()
            photo2Upload.set(2017,9,24)
            photo2Taken.set(2017,9,22)

            val photo3Upload = Calendar.getInstance()
            val photo3Taken = Calendar.getInstance()
            photo3Upload.set(2017,9,24)
            photo3Taken.set(2017,7,24)

            val photo4Upload = Calendar.getInstance()
            val photo4Taken = Calendar.getInstance()
            photo4Upload.set(2016,2,4)
            photo4Taken.set(2017,9,24)
            solrClient.ping()
            LOG.info("Successfully pinged solr server!")

            solrClient.deleteByQuery("*:*")
            photoRepository.save(listOf(
                    PhotoInfo(filename = "image1.jpg",
                            albums = listOf("cheese", "petrol"),
                            categories = listOf("category1"),
                            person = listOf("Phil", "Rhiann"),
                            year = 2017,
                            month = 9,
                            day = 10,
                            location = "4",
                            uploadDate = photo1Upload.time,
                            takenOnDate = photo1Taken.time
                    ),
                    PhotoInfo(filename = "image2.jpg",
                            albums = listOf("petrol"),
                            categories = listOf("category2"),
                            person = listOf("Phil", "Rhiann", "Zach"),
                            year = 2017,
                            month = 10,
                            day = 9,
                            location = "3",
                            uploadDate = photo2Upload.time,
                            takenOnDate = photo2Taken.time
                    ),
                    PhotoInfo(filename = "image3.jpg",
                            albums = listOf("cheese"),
                            categories = listOf("category1", "category2"),
                            person = listOf("Phil", "Ethan"),
                            year = 2017,
                            month = 10,
                            day = 11,
                            location = "2",
                            uploadDate = photo3Upload.time,
                            takenOnDate = photo3Taken.time
                    ),
                    PhotoInfo(filename = "image4.jpg",
                            albums = listOf("cheese"),
                            categories = listOf("category1", "category3"),
                            person = listOf("Rhiann", "Zach"),
                            year = 2016,
                            month = 9,
                            day = 12,
                            location = "1",
                            uploadDate = photo4Upload.time,
                            takenOnDate = photo4Taken.time
                    )
            ))
        } catch (solrException: SolrServerException) {
            throw Exception("Could not ping solr server", solrException)
        }
    }

    @Bean
    fun solrClient(): SolrClient {
        return HttpSolrClient.Builder("http://localhost:8983/solr/photos").build()
    }

    @Bean
    fun propertyConfigurer() = PropertySourcesPlaceholderConfigurer().apply {
        setPlaceholderPrefix("%{")
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

