package uk.co.boombastech.photos

import org.apache.solr.client.solrj.SolrQuery
import uk.co.boombastech.photos.solr.SolrField
import uk.co.boombastech.photos.solr.SolrFilterQuery

data class SearchCriteria(
        var location: List<String> = listOf(),
        var album: List<String> = listOf(),
        var category: List<String> = listOf(),
        var person: List<String> = listOf(),
        var year: List<Int> = listOf(),
        var month: List<Int> = listOf(),
        var day: List<Int> = listOf(),
        var size: Int = 10,
        var page: Int = 1
) {
    fun convertToSolrQuery(): SolrQuery {
        val solrQuery = SolrQuery("*:*")

        SolrField.values()
                .map { solrField ->
                    SolrFilterQuery(
                            name = solrField.key,
                            tag = solrField.tag,
                            values = solrField.searchCriteriaProperty.get(this).map(Any::toString)
                    )
                }
                .filter { query -> query.values.isNotEmpty() }
                .map(SolrFilterQuery::toString)
                .forEach { query -> solrQuery.addFilterQuery(query) }

        solrQuery.start = (page - 1) * size
        solrQuery.rows = size

        return solrQuery
    }
}

