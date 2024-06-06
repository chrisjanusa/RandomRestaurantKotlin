package com.chrisjanusa.restaurant

data class Link(val url: String, val textRes: Int, val iconRes: Int)

fun createWebsiteLink(url: String) = Link(url, R.string.website_title, R.drawable.website)
fun createMenuLink(url: String) = Link(url, R.string.menu_title, R.drawable.menu)
fun createFourSquareLink(foursquareId: String) = Link("https://foursquare.com/v/$foursquareId", R.string.foursquare_title, R.drawable.foursquare)
