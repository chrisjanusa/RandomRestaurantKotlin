package com.chrisjanusa.base.models.enums

// Foursquare category id comes from https://docs.foursquare.com/data-products/docs/categories
sealed class Cuisine(val identifier: String, val foursquare: String) {
    data object American : Cuisine("American", "13068")
    data object Asian :
        Cuisine("Asian", "13072")
    data object Bbq : Cuisine("BBQ", "13026")
    data object Deli : Cuisine("Deli", "13039,13334")
    data object Dessert : Cuisine("Dessert", "13040")
    data object Italian : Cuisine("Italian", "13236")
    data object Indian : Cuisine("Indian", "13199,13198")
    data object Mexican : Cuisine("Mexican", "13303")
    data object Pizza : Cuisine("Pizza", "13064")
    data object Seafood : Cuisine("Seafood", "13338")
    data object Steak : Cuisine("Steak", "13383")
    data object Sushi : Cuisine("Sushi", "13276")
}