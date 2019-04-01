package com.puzzlebench.clean_marvel_kotlin.data.service.response

class CharacterResponse(
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: ThumbnailResponse
)