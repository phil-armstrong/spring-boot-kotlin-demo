package uk.co.boombastech.photos

data class Facet(val name: String, val values: List<FacetValue> = listOf())

data class FacetValue(val name: String, val count: Int, val selected: Boolean = false, val subFacet:Facet? = null)