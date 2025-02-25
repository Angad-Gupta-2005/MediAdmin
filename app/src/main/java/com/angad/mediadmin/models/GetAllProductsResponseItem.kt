package com.angad.mediadmin.models

data class GetAllProductsResponseItem(
    val category: String,
    val id: Int,
    val name: String,
    val price: Int,
    val products_id: String,
    val stock: Int
)