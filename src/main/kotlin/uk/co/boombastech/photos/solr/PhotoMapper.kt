package uk.co.boombastech.photos.solr

import org.apache.solr.client.solrj.response.QueryResponse
import org.apache.solr.common.SolrDocument
import org.springframework.stereotype.Service
import uk.co.boombastech.photos.PhotoInfo
import java.util.*

@Service
class PhotoMapper {
    fun createPhotos(queryResponse: QueryResponse): List<PhotoInfo> {
        return queryResponse.results.map { result ->
            PhotoInfo(
                    id = result["id"] as String,
                    filename = result["name"] as String,
                    albums = result.getListOfStrings("album"),
                    categories = result.getListOfStrings("category"),
                    people = result.getListOfStrings("person"),
                    location = result["location"] as String?,
                    uploadDate = result["uploadDate"] as Date?,
                    takenOnDate = result["takenOnDate"] as Date?,
                    year = result["year"] as Int?,
                    month = result["month"] as Int?,
                    day = result["day"] as Int?,
                    new = result["editted"] as Boolean
            )
        }
    }

    private fun SolrDocument.getListOfStrings(fieldName: String): List<String> {
        return getFieldValues(fieldName)?.map { it.toString() } ?: listOf()
    }
}