package com.chrisjanusa.base.models.enums

sealed class Cuisine(val identifier: String, val yelp: String) {
    object American : Cuisine("American", "newamerican,tradamerican")
    object Asian :
        Cuisine("Asian", "chinese,japanese,korean,thai,ramen,dumplings,hkcafe,panasian,taiwanese,asianfusion")
    object Bbq : Cuisine("BBQ", "bbq")
    object Deli : Cuisine("Deli", "delis,sandwiches")
    object Dessert : Cuisine("Dessert", "desserts")
    object Italian : Cuisine("Italian", "italian")
    object Indian : Cuisine("Indian", "indpak")
    object Mexican : Cuisine("Mexican", "mexican,newmexican,tex-mex")
    object Pizza : Cuisine("Pizza", "pizza")
    object Seafood : Cuisine("Seafood", "seafood")
    object Steak : Cuisine("Steak", "steak")
    object Sushi : Cuisine("Sushi", "sushi")
}