package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.domain.CharacterStored
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character

open class GetCharacterStoredUseCase(private val characterStored: CharacterStored) {

    open operator fun invoke(): List<Character> = characterStored.getCharacters()
}
