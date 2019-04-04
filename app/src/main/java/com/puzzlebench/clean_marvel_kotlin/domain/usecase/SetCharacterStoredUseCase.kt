package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.domain.CharacterStored
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character

open class SetCharacterStoredUseCase(private val characterStored: CharacterStored) {

    open operator fun invoke(characters: List<Character>) = characterStored.setCharacters(characters)
}
