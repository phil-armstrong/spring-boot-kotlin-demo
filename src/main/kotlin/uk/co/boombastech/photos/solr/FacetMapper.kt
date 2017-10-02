package uk.co.boombastech.photos.solr

import org.apache.solr.client.solrj.response.FacetField
import org.apache.solr.client.solrj.response.PivotField
import org.apache.solr.client.solrj.response.QueryResponse
import org.apache.solr.common.util.NamedList
import org.springframework.stereotype.Service
import uk.co.boombastech.photos.Facet
import uk.co.boombastech.photos.FacetValue
import uk.co.boombastech.photos.SearchCriteria

@Service
class FacetMapper {
    fun createFacets(queryResponse: QueryResponse, searchCriteria: SearchCriteria): List<Facet> {
        val mutableListOfFacets: MutableList<Facet> = mutableListOf()

        val parameters = getSentParams(queryResponse)

        queryResponse.facetFields
                .map { facetField -> createFacetFromFacetField(facetField, parameters.get(facetField.name)) }
                .forEach { mutableListOfFacets += it }

        FacetPivot.values()
                .mapNotNull { createFacetFromFacetPivot(it, queryResponse) }
                .forEach { mutableListOfFacets += it }

        return mutableListOfFacets.filter { it.values.isNotEmpty() }
    }
}

fun getSentParams(queryResponse: QueryResponse): Parameters {
    val get: Any = queryResponse.header.get("params") ?: return Parameters(listOf())

    val fqs = (get as NamedList<*>).get("fq") ?: return Parameters(listOf())

    if (fqs is List<*>) {
        val queries: List<String> = fqs as List<String>
        val map = queries.map(::asdf)
        return Parameters(map)
    } else {
        return Parameters(listOf(asdf(fqs as String)))
    }
}

fun asdf(query: String): Parameter {
    val withoutTag = query.substringAfter("}")
    val (key, valuesString) = withoutTag.split(":")
    val values = valuesString.removeSurrounding("(", ")").split(" ").toList()
    return Parameter(key, values)
}

data class Parameter(val key: String, val values: List<String>)

class Parameters(private val parameters: List<Parameter>) {
    fun get(solrField: String): List<String> {
        val parameter = parameters.firstOrNull { (key) -> key == solrField }
        return parameter?.values ?: listOf()

    }
}

fun createFacetFromFacetField(facetField: FacetField, selections: List<String>): Facet {
    return Facet(
            name = facetField.name,
            values = facetField.values.map { value -> FacetValue(name = value.name, count = value.count.toInt(), selected = selections.contains(value.name)) }
    )
}

fun createFacetFromFacetPivot(facetPivot: FacetPivot, queryResponse: QueryResponse): Facet? {
    val datePivot = queryResponse.facetPivot.get(facetPivot.pivotFields)
    return createFacet(datePivot, getSentParams(queryResponse))
}

fun createFacet(pivotValue: List<PivotField>?, sentParams: Parameters, parentFacetSelected: Boolean = true): Facet? {
    return if (pivotValue != null && pivotValue.isNotEmpty()) {
        Facet(name = pivotValue[0].field, values = createFacetValues(pivotValue, sentParams, parentFacetSelected))
    } else {
        null
    }
}

fun createFacetValues(pivotFields: List<PivotField>, parameters: Parameters, parentFacetSelected: Boolean = true): List<FacetValue> {
    return pivotFields.map { pivotField ->
        val selected = parameters.get(pivotField.field.toString()).contains(pivotField.value.toString())
        FacetValue(name = pivotField.value.toString(),
                count = pivotField.count,
                selected = parentFacetSelected && selected,
                subFacet = createFacet(pivotField.pivot, parameters, parentFacetSelected && selected))
    }
}