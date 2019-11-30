package com.chrisjanusa.randomizer.helpers

object PriceHelper {
    const val defaultPriceTitle = "Price"


    sealed class Price(val text: String, val num: Int) {
        object One : Price("$",1)
        object Two : Price("$$",2)
        object Three : Price("$$$",3)
        object Four : Price("$$$$",4)
    }

    fun priceToDisplayString(list : List<Price>) : String {
        return if (list.isEmpty()) {
            defaultPriceTitle
        }
        else{
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

    fun priceFromDisplayString(curr : String) : List<Price> {
        return if (defaultPriceTitle == curr){
            ArrayList()
        }
        else{
            val selected = ArrayList<Price>()
            for (price in curr.split(", ")) {
                when (price) {
                    Price.One.text -> selected.add(Price.One)
                    Price.Two.text -> selected.add(Price.Two)
                    Price.Three.text -> selected.add(Price.Three)
                    Price.Four.text -> selected.add(Price.Four)
                }
            }
            selected
        }
    }
}