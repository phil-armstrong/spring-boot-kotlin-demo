package uk.co.boombastech.photos.solr

import org.apache.solr.client.solrj.response.FacetField
import org.apache.solr.client.solrj.response.PivotField
import org.apache.solr.client.solrj.response.QueryResponse
import org.springframework.stereotype.Service
import uk.co.boombastech.photos.Facet

@Service
class FacetMapper {
    fun createFacets(queryResponse: QueryResponse): List<Facet> {
        val mutableListOfFacets: MutableList<Facet> = mutableListOf()

        queryResponse.facetFields
                .map(::createFacetFromFacetField)
                .forEach { mutableListOfFacets += it }

        FacetPivot.values()
                .map { createFacetFromFacetPivot(it, queryResponse) }
                .forEach { mutableListOfFacets += it }

        return mutableListOfFacets.filter { it.values.isNotEmpty() }
    }
}

fun createFacetFromFacetField(facetField: FacetField): Facet {
    return Facet(
            name = facetField.name,
            values = facetField.values.map { value -> Facet(name = value.name, count = value.count) }
    )
}

fun createFacetFromPivotField(pivotField: PivotField): Facet {
    return Facet(
            name = pivotField.value.toString(),
            count = pivotField.count.toLong(),
            values = pivotField.pivot?.map(::createFacetFromPivotField).orEmpty()
    )
}

fun createFacetFromFacetPivot(facetPivot: FacetPivot, queryResponse: QueryResponse): Facet {
    return Facet(name = facetPivot.key, values = queryResponse.facetPivot.get(facetPivot.pivotFields).map(::createFacetFromPivotField))
}