package com.chrisjanusa.randomizer.filter_category

object CategoryHelper {
    fun HashSet<Category>.toSaveString(): String {
        val out = StringBuilder()
        for (category in this.iterator()) {
            out.append(category.identifier)
            out.append(", ")
        }
        return out.dropLast(2).toString()
    }

    fun setFromSaveString(categoryString: String): HashSet<Category> {
        val set = HashSet<Category>()
        for (category in categoryString.split(", ")) {
            val catString = when (category) {
                Category.American.identifier -> Category.American
                Category.Asian.identifier -> Category.Asian
                Category.Bbq.identifier -> Category.Bbq
                Category.Deli.identifier -> Category.Deli
                Category.Dessert.identifier -> Category.Dessert
                Category.Italian.identifier -> Category.Italian
                Category.Indian.identifier -> Category.Indian
                Category.Pizza.identifier -> Category.Pizza
                Category.Mexican.identifier -> Category.Mexican
                Category.Seafood.identifier -> Category.Seafood
                Category.Steak.identifier -> Category.Steak
                Category.Sushi.identifier -> Category.Sushi
                else -> null
            }
            catString?.let { set.add(it) }
        }
        return set
    }

    sealed class Category(val identifier: String, val yelp: String) {
        object American : Category("American", "newamerican,tradamerican")
        object Asian :
            Category("Asian", "chinese,japanese,korean,thai,ramen,dumplings,hkcafe,panasian,taiwanese,asianfusion")

        object Bbq : Category("BBQ", "bbq")
        object Deli : Category("Deli", "delis,sandwiches")
        object Dessert : Category("Dessert", "desserts")
        object Italian : Category("Italian", "italian")
        object Indian : Category("Indian", "indpak")
        object Mexican : Category("Mexican", "mexican,newmexican,tex-mex")
        object Pizza : Category("Pizza", "pizza")
        object Seafood : Category("Seafood", "seafood")
        object Steak : Category("Steak", "steak")
        object Sushi : Category("Sushi", "sushi")
    }
}