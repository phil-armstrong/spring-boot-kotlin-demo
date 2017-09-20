package uk.co.boombastech.photos

import org.apache.solr.client.solrj.SolrClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import uk.co.boombastech.photos.solr.FacetPivot
import uk.co.boombastech.photos.solr.SolrField
import uk.co.boombastech.photos.solr.SolrField.values
import uk.co.boombastech.photos.solr.SolrResponseMapper

@Service
class PhotoRepository(val solrClient: SolrClient, val solrResponseMapper: SolrResponseMapper) {

    private val LOG = LoggerFactory.getLogger(PhotoRepository::class.java)

    fun find(searchCriteria: SearchCriteria): SearchResults {
        val solrQuery = searchCriteria.convertToSolrQuery()

        values().filter(SolrField::facetField)
                .map(SolrField::createFacetField)
                .forEach { facet -> solrQuery.addFacetField(facet) }

        FacetPivot.values()
                .map(FacetPivot::createFacetPivot)
                .forEach { facetPivot -> solrQuery.addFacetPivotField(facetPivot) }

        LOG.info(solrQuery.toString())

        val response = solrClient.query(solrQuery)
        return solrResponseMapper.createSearchResult(response)
    }

    fun save(photos: List<PhotoInfo>) {
        solrClient.add(photos
                .map(PhotoInfo::setUpdated)
                .map(PhotoInfo::convertToSolrInputDocument))
        solrClient.commit()
    }
}