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
        return if (this.isEmpty()) {
            defaultPriceTitle
        } else {
            val builder = StringBuilder()
            for (price in priceList) {
                if (this.contains(price)) {
                    builder.append(price.text)
                    builder.append(delimiter)
                }
            }
            builder.deleteCharAt(builder.lastIndex)
            builder.deleteCharAt(builder.lastIndex)
            builder.toString()
        }
    }

    fun priceFromDisplayString(curr: String): HashSet<Price> {
        return if (defaultPriceTitle == curr) {
            HashSet()
        } else {
            val selected = HashSet<Price>()
            for (price in curr.split(delimiter.toRegex())) {
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