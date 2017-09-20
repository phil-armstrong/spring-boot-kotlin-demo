package uk.co.boombastech.photos

import org.apache.solr.common.SolrInputDocument
import uk.co.boombastech.photos.solr.SolrField
import java.util.*

data class PhotoInfo(
        var id: String? = null,
        var filename: String? = null,
        var albums: List<String> = listOf(),
        var categories: List<String> = listOf(),
        var people: List<String> = listOf(),
        var location: String? = null,
        var uploadDate: Date? = null,
        var takenOnDate: Date? = null,
        var year: Int? = null,
        var month: Int? = null,
        var day: Int? = null,
        var new: Boolean = false
) {

    fun setUpdated(): PhotoInfo {
        new = false
        return this
    }

    fun convertToSolrInputDocument(): SolrInputDocument {
        val solrInputDocument = SolrInputDocument()
        solrInputDocument.addField("name", filename)
        solrInputDocument.addField(SolrField.LOCATION.key, location)
        solrInputDocument.addField(SolrField.ALBUM.key, albums)
        solrInputDocument.addField(SolrField.CATEGORY.key, categories)
        solrInputDocument.addField(SolrField.YEAR.key, year)
        solrInputDocument.addField(SolrField.MONTH.key, month)
        solrInputDocument.addField(SolrField.DAY.key, day)
        solrInputDocument.addField(SolrField.PERSON.key, people)
        solrInputDocument.addField("dateTaken", takenOnDate)
        solrInputDocument.addField("dateUploaded", uploadDate)
        solrInputDocument.addField("editted", new)

        return solrInputDocument
    }
}