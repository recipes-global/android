package com.example.recipes.data.model

data class Recipe(var id: Int,
                  var ownerId: Int,
                  var name: String,
                  var recipePhoto: String,
                  var isSaved: Boolean,
                  var commentCount: Int,
                  var isLiked: Boolean,
                  var likeCount: Int,
                  var products: List<String>,
                  var description: String,
                  var sharedTo: List<String>
)