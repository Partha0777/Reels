package com.curiozing.reels.model.reels

data class Reels(
    val id:Long,
    val descriptions:String,
    val thumbnail:String,
    val videoUrl:String,
    val createdAt:Long,
    val userInteractions: UserInteractions
)


