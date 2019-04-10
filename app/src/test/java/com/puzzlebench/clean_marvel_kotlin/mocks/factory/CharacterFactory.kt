package com.puzzlebench.clean_marvel_kotlin.mocks.factory

import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail


class CharactersFactory {

    companion object Factory {
        private const val ID = 1
        private const val BASE_NAME = "Name"
        private const val BASE_DESCRIPTION = "Description"
        private const val BASE_PATH = "image"
        private const val BASE_EXTENSION = ".png"
        private const val MIN_ITEMS = 1
        private const val MAX_ITEMS = 5

        open fun getMockCharacter(): List<Character> {
            return listOf(MIN_ITEMS..MAX_ITEMS).map {
                Character(ID, "$BASE_NAME$it", "$BASE_DESCRIPTION$it", Thumbnail("$BASE_PATH$it", BASE_EXTENSION))
            }
        }
    }
}
