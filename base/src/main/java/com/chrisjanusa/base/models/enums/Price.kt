package com.chrisjanusa.base.models.enums

sealed class Price(val text: String, val num: Int) {
    object One : Price("$", 1)
    object Two : Price("$$", 2)
    object Three : Price("$$$", 3)
    object Four : Price("$$$$", 4)
}