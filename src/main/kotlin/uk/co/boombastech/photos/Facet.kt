package uk.co.boombastech.photos

data class Facet(val name: String, val count: Long? = null, val values: List<Facet> = listOf())