package com.chrisjanusa.randomizer.helpers

object RestrictionHelper {
    const val defaultRestrictionTitle = "Food Restrictions"

    sealed class Restriction(val identifier : String, val display: String) {
        object Vegan : Restriction("vegan", "Vegan")
        object Vegetarian : Restriction("vegetarian", "Vegetarian")
        object Halal : Restriction("halal", "Halal")
        object Kosher : Restriction("kosher", "Kosher")
        object None : Restriction("none", "")
    }

    fun restrictionFromIdentifier(identifier: String) : Restriction{
        return when(identifier) {
            Restriction.Vegan.identifier -> Restriction.Vegan
            Restriction.Vegetarian.identifier -> Restriction.Vegetarian
            Restriction.Halal.identifier -> Restriction.Halal
            Restriction.Kosher.identifier -> Restriction.Kosher
            else -> Restriction.None
        }
    }

    fun isDefault(restriction: Restriction) = restriction == Restriction.None
}