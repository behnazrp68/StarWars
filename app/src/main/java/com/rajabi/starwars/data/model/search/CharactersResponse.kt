package com.rajabi.starwars.data.model.search


import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val characters: List<Character>
)