package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterStoredImpl
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character

open class SetCharacterStoredUseCase(private val characterStoredImp: CharacterStoredImpl) {

    open operator fun invoke(characters : List<Character>) = characterStoredImp.setCharacters(characters)}
