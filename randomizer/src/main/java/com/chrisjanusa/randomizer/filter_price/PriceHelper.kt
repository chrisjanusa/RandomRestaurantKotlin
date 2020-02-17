package com.chrisjanusa.randomizer.filter_price

object PriceHelper {
    const val defaultPriceTitle = "Price"
    private const val delimiter = ", "


    sealed class Price(val text: String, val num: Int) {
        object One : Price("$", 1)
        object Two : Price("$$", 2)
        object Three : Price("$$$", 3)
        object Four : Price("$$$$", 4)
    }

    private val priceList = listOf(Price.One, Price.Two, Price.Three, Price.Four)

    fun HashSet<Price>.toSaveString(): String {
        if (isEmpty()) {
            return defaultPriceTitle
        }
        val builder = StringBuilder()
        priceList.forEach { if (contains(it)) builder.append(it.text + delimiter) }
        return builder.dropLast(2).toString()
    }

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

    fun setToYelpString(prices : HashSet<Price>): String? {
        if(prices.isEmpty()) return null
        val yelpString = StringBuilder()
        for (price in prices) {
            yelpString.append(price.num)
            yelpString.append(",")
        }

        return yelpString.dropLast(1).toString()
    }
}