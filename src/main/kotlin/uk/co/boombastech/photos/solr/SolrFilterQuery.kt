package uk.co.boombastech.photos.solr

data class SolrFilterQuery(
        val name: String,
        val values: List<String> = listOf(),
        val tag: String? = null
) {
    override fun toString(): String {
        val stringBuilder = StringBuilder()

        if (tag != null) {
            stringBuilder.append("{!tag=$tag}")
        }
        stringBuilder.append(name)
        stringBuilder.append(":")
        stringBuilder.append(values.joinToString(separator = " ", prefix = "(", postfix = ")"))

        return stringBuilder.toString()
    }
}