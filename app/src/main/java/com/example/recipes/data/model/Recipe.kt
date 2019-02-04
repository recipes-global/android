package com.example.recipes.data.model

data class Recipe(var id: Int,
                  var ownerId: Int,
                  var name: String,
                  var products: List<String>,
                  var description: String,
                  var sharedTo: List<String>
)