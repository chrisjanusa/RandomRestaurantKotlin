package com.chrisjanusa.randomizer.filter_restriction

object RestrictionHelper {
    const val defaultRestrictionTitle = "Food Restrictions"

    sealed class Restriction(val identifier: String, val display: String) {
        object Vegan : Restriction("vegan", "Vegan")
        object Vegetarian : Restriction("vegetarian", "Vegetarian")
        object Halal : Restriction("halal", "Halal")
        object Kosher : Restriction("kosher", "Kosher")
        object None : Restriction("none", "")
    }

    fun restrictionFromIdentifier(identifier: String): Restriction {
        return when (identifier) {
            RestrictionHelper.Restriction.Vegan.identifier -> RestrictionHelper.Restriction.Vegan
            RestrictionHelper.Restriction.Vegetarian.identifier -> RestrictionHelper.Restriction.Vegetarian
            RestrictionHelper.Restriction.Halal.identifier -> RestrictionHelper.Restriction.Halal
            RestrictionHelper.Restriction.Kosher.identifier -> RestrictionHelper.Restriction.Kosher
            else -> RestrictionHelper.Restriction.None
        }
    }

    fun isDefault(restriction: Restriction) = restriction == RestrictionHelper.Restriction.None
}