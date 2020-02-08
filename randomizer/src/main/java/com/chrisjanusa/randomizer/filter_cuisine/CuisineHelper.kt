package com.chrisjanusa.randomizer.filter_cuisine

object CuisineHelper {
    const val defaultCuisineTitle = "Cuisines"
    private const val delimiter = ", "

    fun HashSet<Cuisine>.toSaveString(): String {
        if (this.isEmpty()) {
            return defaultCuisineTitle
        }
        val out = StringBuilder()
        for (cuisine in this.iterator()) {
            out.append(cuisine.identifier)
            out.append(delimiter)
        }
        return out.dropLast(2).toString()
    }

    fun setFromSaveString(cuisineString: String): HashSet<Cuisine> {
        val set = HashSet<Cuisine>()
        for (cuisine in cuisineString.split(delimiter.toRegex())) {
            val catString = when (cuisine) {
                Cuisine.American.identifier -> Cuisine.American
                Cuisine.Asian.identifier -> Cuisine.Asian
                Cuisine.Bbq.identifier -> Cuisine.Bbq
                Cuisine.Deli.identifier -> Cuisine.Deli
                Cuisine.Dessert.identifier -> Cuisine.Dessert
                Cuisine.Italian.identifier -> Cuisine.Italian
                Cuisine.Indian.identifier -> Cuisine.Indian
                Cuisine.Pizza.identifier -> Cuisine.Pizza
                Cuisine.Mexican.identifier -> Cuisine.Mexican
                Cuisine.Seafood.identifier -> Cuisine.Seafood
                Cuisine.Steak.identifier -> Cuisine.Steak
                Cuisine.Sushi.identifier -> Cuisine.Sushi
                else -> null
            }
            catString?.let { set.add(it) }
        }

        return set
    }

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
}