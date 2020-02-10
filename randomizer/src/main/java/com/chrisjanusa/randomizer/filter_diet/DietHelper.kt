package com.chrisjanusa.randomizer.filter_diet

object DietHelper {
    const val defaultDietTitle = "Diet"

    sealed class Diet(val identifier: String, val display: String) {
        object Vegan : Diet("vegan", "Vegan")
        object Vegetarian : Diet("vegetarian", "Vegetarian")
        object Halal : Diet("halal", "Halal")
        object Kosher : Diet("kosher", "Kosher")
        object None : Diet("none", defaultDietTitle)
    }

    fun dietFromIdentifier(identifier: String): Diet {
        return when (identifier) {
            Diet.Vegan.identifier -> Diet.Vegan
            Diet.Vegetarian.identifier -> Diet.Vegetarian
            Diet.Halal.identifier -> Diet.Halal
            Diet.Kosher.identifier -> Diet.Kosher
            else -> Diet.None
        }
    }

    fun isDefault(diet: Diet) = diet == Diet.None
}