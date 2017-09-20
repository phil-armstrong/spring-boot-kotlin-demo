package uk.co.boombastech.photos.solr

import uk.co.boombastech.photos.PhotoInfo
import uk.co.boombastech.photos.SearchCriteria
import kotlin.reflect.KMutableProperty1

enum class SolrField(val key: String,
                     val searchCriteriaProperty: KMutableProperty1<SearchCriteria, out List<Any>>,
                     val tag: String? = key,
                     val facetField: Boolean = true,
                     val photoInfoListProperty: KMutableProperty1<PhotoInfo, List<String>>? = null,
                     val photoInfoStringProperty: KMutableProperty1<PhotoInfo, String?>? = null,
                     val photoInfoIntProperty: KMutableProperty1<PhotoInfo, Int?>? = null
) {
    ALBUM(
            key = "album",
            searchCriteriaProperty = SearchCriteria::album,
            photoInfoListProperty = PhotoInfo::albums
    ),
    CATEGORY(
            key = "category",
            searchCriteriaProperty = SearchCriteria::category,
            photoInfoListProperty = PhotoInfo::categories
    ),
    LOCATION(
            key = "location",
            searchCriteriaProperty = SearchCriteria::location,
            photoInfoStringProperty = PhotoInfo::location
    ),
    PERSON(
            key = "people",
            searchCriteriaProperty = SearchCriteria::person,
            photoInfoListProperty = PhotoInfo::people
    ),
    YEAR(
            key = "year",
            searchCriteriaProperty = SearchCriteria::year,
            tag = "date",
            facetField = false,
            photoInfoIntProperty = PhotoInfo::year
    ),
    MONTH(
            key = "month",
            searchCriteriaProperty = SearchCriteria::month,
            tag = "date",
            facetField = false,
            photoInfoIntProperty = PhotoInfo::month
    ),
    DAY(
            key = "day",
            searchCriteriaProperty = SearchCriteria::day,
            tag = "date",
            facetField = false,
            photoInfoIntProperty = PhotoInfo::day
    ), ;

    fun createFacetField(): String {
        return "{!ex=$tag}$key"
    }
}