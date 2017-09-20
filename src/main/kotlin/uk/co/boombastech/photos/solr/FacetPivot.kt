package uk.co.boombastech.photos.solr

import uk.co.boombastech.photos.solr.SolrField.*

enum class FacetPivot(val key: String,
                      val fields: List<SolrField>,
                      val tag: String? = null) {
    DATE(
            key = "datePivot",
            tag = "date",
            fields = listOf(YEAR, MONTH, DAY)
    );

    val pivotFields: String = fields.joinToString(",") { field -> field.key }

    fun createFacetPivot(): String {
        return "{!ex=$tag}$pivotFields"
    }
}