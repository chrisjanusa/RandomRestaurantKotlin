package com.chrisjanusa.randomizer.filter_category

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chrisjanusa.randomizer.R
import com.google.android.material.card.MaterialCardView
import java.lang.StringBuilder

object CategoryHelper {
    const val defaultCategoryTitle = "Categories"

    fun renderCardStyle(card: MaterialCardView, text: TextView, icon: ImageView, selected: Boolean, context: Context) {
        if (selected) {
            val background = ContextCompat.getColor(context, R.color.filter_background_selected)
            card.setCardBackgroundColor(background)
            card.strokeColor = background
            text.setTextColor(ContextCompat.getColor(context, R.color.filter_text_selected))
            val color = ContextCompat.getColor(context, R.color.filter_icon_selected)
            icon.setColorFilter(color)
        } else {
            card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.filter_background_not_selected))
            card.strokeColor = ContextCompat.getColor(context, R.color.outline)
            text.setTextColor(ContextCompat.getColor(context, R.color.filter_text_not_selected))
            val color = ContextCompat.getColor(context, R.color.filter_icon_not_selected)
            icon.setColorFilter(color)
        }
    }

    fun saveToDisplayString(saveString: String): String =
        saveString.takeUnless { it.length > 20 } ?: saveString.substring(0..16) + "..."


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
                CategoryHelper.Category.American.identifier -> CategoryHelper.Category.American
                CategoryHelper.Category.Asian.identifier -> CategoryHelper.Category.Asian
                CategoryHelper.Category.Bbq.identifier -> CategoryHelper.Category.Bbq
                CategoryHelper.Category.Deli.identifier -> CategoryHelper.Category.Deli
                CategoryHelper.Category.Dessert.identifier -> CategoryHelper.Category.Dessert
                CategoryHelper.Category.Italian.identifier -> CategoryHelper.Category.Italian
                CategoryHelper.Category.Indian.identifier -> CategoryHelper.Category.Indian
                CategoryHelper.Category.Pizza.identifier -> CategoryHelper.Category.Pizza
                CategoryHelper.Category.Mexican.identifier -> CategoryHelper.Category.Mexican
                CategoryHelper.Category.Seafood.identifier -> CategoryHelper.Category.Seafood
                CategoryHelper.Category.Steak.identifier -> CategoryHelper.Category.Steak
                CategoryHelper.Category.Sushi.identifier -> CategoryHelper.Category.Sushi
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