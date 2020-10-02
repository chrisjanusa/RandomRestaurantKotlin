package com.chrisjanusa.randomizer.filter_cuisine

import com.chrisjanusa.base.models.delimiter
import com.chrisjanusa.base.models.enums.Cuisine

object CuisineHelper {

    fun HashSet<Cuisine>.toYelpString(): String? {
        if (this.isEmpty()) {
            return null
        }
        val out = StringBuilder()
        for (cuisine in this.iterator()) {
            out.append(cuisine.yelp)
            out.append(delimiter)
        }
        return out.dropLast(2).toString()
    }

    fun cuisineFromIdentifierString(cuisineString: String): HashSet<Cuisine> {
        val set = HashSet<Cuisine>()
        for (cuisine in cuisineString.split(delimiter.toRegex())) {
            val catString = when (cuisine) {
                Cuisine.American.identifier -> Cuisine.American
                Cuisine.Asian.identifier -> Cuisine.Asian
                Cuisine.Bbq.identifier -> Cuisine.Bbq
                Cuisine.Deli.identifier -> Cuisine.Deli
                Cuisine.Dessert.identifier -> Cuisine.Dessert
                Cuisine.Italian.identifier -> Cuisine.Italian
                Cuisine.Indian.identifier -> Cuisine.Indian
                Cuisine.Pizza.identifier -> Cuisine.Pizza
                Cuisine.Mexican.identifier -> Cuisine.Mexican
                Cuisine.Seafood.identifier -> Cuisine.Seafood
                Cuisine.Steak.identifier -> Cuisine.Steak
                Cuisine.Sushi.identifier -> Cuisine.Sushi
                else -> null
            }
            catString?.let { set.add(it) }
        }

        return set
    }
}