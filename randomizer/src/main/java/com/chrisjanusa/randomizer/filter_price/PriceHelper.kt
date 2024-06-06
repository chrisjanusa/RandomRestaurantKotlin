package com.chrisjanusa.randomizer.filter_price

import com.chrisjanusa.base.models.defaultPriceTitle
import com.chrisjanusa.base.models.delimiter
import com.chrisjanusa.base.models.enums.Price


fun priceFromSaveString(curr: String): HashSet<Price> {
    if (defaultPriceTitle == curr) {
        return HashSet()
    }
    val selected = HashSet<Price>()
    for (price in curr.split(delimiter.toRegex())) {
        when (price) {
            Price.One.text -> selected.add(Price.One)
            Price.Two.text -> selected.add(Price.Two)
            Price.Three.text -> selected.add(Price.Three)
            Price.Four.text -> selected.add(Price.Four)
        }
    }
    return selected
}
