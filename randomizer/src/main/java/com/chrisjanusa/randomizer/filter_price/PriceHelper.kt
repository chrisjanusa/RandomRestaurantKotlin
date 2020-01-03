package com.chrisjanusa.randomizer.filter_price

object PriceHelper {
    const val defaultPriceTitle = "Price"


    sealed class Price(val text: String, val num: Int) {
        object One : Price("$", 1)
        object Two : Price("$$", 2)
        object Three : Price("$$$", 3)
        object Four : Price("$$$$", 4)
    }

    fun priceToDisplayString(list: List<Price>): String {
        return if (list.isEmpty()) {
            defaultPriceTitle
        } else {
            val builder = StringBuilder()
            for (price in list) {
                builder.append(price.text)
                builder.append(", ")
            }
            builder.deleteCharAt(builder.lastIndex)
            builder.deleteCharAt(builder.lastIndex)
            builder.toString()
        }
    }

    fun priceFromDisplayString(curr: String): List<Price> {
        return if (defaultPriceTitle == curr) {
            ArrayList()
        } else {
            val selected = ArrayList<Price>()
            for (price in curr.split(", ")) {
                when (price) {
                    PriceHelper.Price.One.text -> selected.add(PriceHelper.Price.One)
                    PriceHelper.Price.Two.text -> selected.add(PriceHelper.Price.Two)
                    PriceHelper.Price.Three.text -> selected.add(PriceHelper.Price.Three)
                    PriceHelper.Price.Four.text -> selected.add(PriceHelper.Price.Four)
                }
            }
            selected
        }
    }
}