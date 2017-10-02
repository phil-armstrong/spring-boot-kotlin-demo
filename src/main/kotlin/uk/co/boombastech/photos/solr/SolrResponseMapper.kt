package uk.co.boombastech.photos.solr

import org.apache.solr.client.solrj.response.QueryResponse
import org.springframework.stereotype.Service
import uk.co.boombastech.photos.SearchCriteria
import uk.co.boombastech.photos.SearchResults

@Service
class SolrResponseMapper(private val photoMapper: PhotoMapper, private val facetMapper: FacetMapper) {

    fun createSearchResult(queryResponse: QueryResponse, searchCriteria: SearchCriteria): SearchResults {
        val facets = facetMapper.createFacets(queryResponse, searchCriteria)

        return SearchResults(
                count = queryResponse.results.numFound,
                results = photoMapper.createPhotos(queryResponse),
                facets = facets)
    }
}