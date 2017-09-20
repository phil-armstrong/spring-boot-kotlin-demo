package uk.co.boombastech.photos.solr

import org.apache.solr.client.solrj.response.QueryResponse
import org.springframework.stereotype.Service
import uk.co.boombastech.photos.SearchResults

@Service
class SolrResponseMapper(private val photoMapper: PhotoMapper, private val facetMapper: FacetMapper) {

    fun createSearchResult(queryResponse: QueryResponse): SearchResults {
        return SearchResults(
                count = queryResponse.results.numFound,
                results = photoMapper.createPhotos(queryResponse),
                facets = facetMapper.createFacets(queryResponse))
    }
}