package com.chrisjanusa.randomizer.filter_diet

import com.chrisjanusa.base.models.enums.Diet

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
