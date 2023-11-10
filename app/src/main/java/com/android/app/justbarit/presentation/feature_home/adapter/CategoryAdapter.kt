package com.android.app.justbarit.presentation.feature_home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.ItemCategoryBinding
import com.android.app.justbarit.domain.model.Category
import com.android.app.justbarit.presentation.common.ext.clickToAction

class CategoryAdapter constructor(categories: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var categoriesList = categories

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(category = categoriesList[position])
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    class ViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.itemCategoryImageView.setImageResource(category.categoryRes)
            binding.itemCategoryTextView.text = category.categoryName
            if (category.selected){
                binding.itemCategoryLayout.setBackgroundResource(R.drawable.item_category_selected_background)
            }else{
                binding.itemCategoryLayout.setBackgroundResource(R.drawable.item_category_background)
            }
            binding.itemCategoryLayout.clickToAction {
                categoryClick(category)
            }
        }
    }

    fun setCategories(newCategories: ArrayList<Category>) {
        categoriesList = newCategories
        notifyDataSetChanged()
    }


    fun updateCategory(updatedCategory: Category){
        categoriesList.forEach { it.selected = false }
        val index = categoriesList.indexOf(updatedCategory)
        categoriesList[index].selected = true
        notifyDataSetChanged()
    }
}

var categoryClick: (Category) -> Unit = {

}