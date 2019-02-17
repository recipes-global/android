package com.example.recipes.data.model

data class Card(  var id: Int,
                  var ownerId: Int,
                  var name: String,
                  var recipePhoto: String,
                  var isSaved: Boolean,
                  var commentCount: Int,
                  var isLiked: Boolean,
                  var likeCount: Int
)