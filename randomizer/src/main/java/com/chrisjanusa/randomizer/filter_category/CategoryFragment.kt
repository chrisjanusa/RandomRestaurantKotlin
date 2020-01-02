package com.chrisjanusa.randomizer.filter_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.randomizer.filter_category.actions.ApplyCategoryAction
import com.chrisjanusa.randomizer.filter_category.actions.CategoryChangeAction
import com.chrisjanusa.randomizer.filter_category.actions.ResetCategoryAction
import com.chrisjanusa.randomizer.filter_category.actions.InitCategoryFilterAction
import com.chrisjanusa.randomizer.base.CommunicationHelper.sendAction
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.Category
import com.chrisjanusa.randomizer.filter_category.CategoryHelper.renderCardStyle
import com.chrisjanusa.randomizer.filter_base.FilterHelper
import com.chrisjanusa.randomizer.base.models.RandomizerState
import com.chrisjanusa.randomizer.base.models.RandomizerViewModel
import kotlinx.android.synthetic.main.categories.*
import kotlinx.android.synthetic.main.confirmation_buttons.*

class CategoryFragment : Fragment() {
    private lateinit var randomizerViewModel: RandomizerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        randomizerViewModel = activity?.run {
            ViewModelProviders.of(this)[RandomizerViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.category_filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confirm.setOnClickListener { sendAction(ApplyCategoryAction(), randomizerViewModel) }
        cancel.setOnClickListener { FilterHelper.onCancelFilterClick(randomizerViewModel) }
        reset.setOnClickListener { sendAction(ResetCategoryAction(), randomizerViewModel) }

        american.setOnClickListener { categoryClick(Category.American) }
        asian.setOnClickListener { categoryClick(Category.Asian) }
        bbq.setOnClickListener { categoryClick(Category.Bbq) }
        deli.setOnClickListener { categoryClick(Category.Deli) }
        desserts.setOnClickListener { categoryClick(Category.Dessert) }
        italian.setOnClickListener { categoryClick(Category.Italian) }
        indian.setOnClickListener { categoryClick(Category.Indian) }
        mexican.setOnClickListener { categoryClick(Category.Mexican) }
        pizza.setOnClickListener { categoryClick(Category.Pizza) }
        seafood.setOnClickListener { categoryClick(Category.Seafood) }
        steak.setOnClickListener { categoryClick(Category.Steak) }
        sushi.setOnClickListener { categoryClick(Category.Sushi) }

        randomizerViewModel.state.observe(this, Observer<RandomizerState>(render))
    }

    override fun onResume() {
        super.onResume()
        sendAction(InitCategoryFilterAction(), randomizerViewModel)
    }

    private fun categoryClick(category: Category) {
        sendAction(CategoryChangeAction(category), randomizerViewModel)
    }

    private val render = fun(newState: RandomizerState) {
        context?.let {
            newState.categoryTempSet.run {
                renderCardStyle(american, american_description, american_icon, contains(Category.American), it)
                renderCardStyle(asian, asian_description, asian_icon, contains(Category.Asian), it)
                renderCardStyle(bbq, bbq_description, bbq_icon, contains(Category.Bbq), it)
                renderCardStyle(deli, deli_description, deli_icon, contains(Category.Deli), it)
                renderCardStyle(desserts, desserts_description, desserts_icon, contains(Category.Dessert), it)
                renderCardStyle(italian, italian_description, italian_icon, contains(Category.Italian), it)
                renderCardStyle(indian, indian_description, indian_icon, contains(Category.Indian), it)
                renderCardStyle(mexican, mexican_description, mexican_icon, contains(Category.Mexican), it)
                renderCardStyle(pizza, pizza_description, pizza_icon, contains(Category.Pizza), it)
                renderCardStyle(seafood, seafood_description, seafood_icon, contains(Category.Seafood), it)
                renderCardStyle(steak, steak_description, steak_icon, contains(Category.Steak), it)
                renderCardStyle(sushi, sushi_description, sushi_icon, contains(Category.Sushi), it)

            }
        }
    }
}