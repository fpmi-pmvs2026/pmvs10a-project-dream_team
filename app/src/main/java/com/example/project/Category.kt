package com.example.project

import android.content.Context

enum class Category(val dbKey: String, val stringRes: Int) {
    FOOD("food", R.string.cat_food),
    TRANSPORT("transport", R.string.cat_transport),
    HOUSING("housing", R.string.cat_housing),
    ENTERTAINMENT("entertainment", R.string.cat_entertainment),
    OTHER("other", R.string.cat_other);

    companion object {
        fun getLocalizedName(context: Context, dbKey: String): String {
            val category = entries.find { it.dbKey == dbKey }
            if (category != null) {
                return context.getString(category.stringRes)
            }
            return when (dbKey) {
                "Еда" -> context.getString(R.string.cat_food)
                "Транспорт" -> context.getString(R.string.cat_transport)
                "Жилье" -> context.getString(R.string.cat_housing)
                "Развлечения" -> context.getString(R.string.cat_entertainment)
                "Другое" -> context.getString(R.string.cat_other)
                else -> dbKey
            }
        }
    }
}