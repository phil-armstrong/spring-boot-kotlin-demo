package uk.co.boombastech.photos

data class SearchResults(
        val count: Long,
        val results: List<PhotoInfo>,
        val facets: List<Facet>
)