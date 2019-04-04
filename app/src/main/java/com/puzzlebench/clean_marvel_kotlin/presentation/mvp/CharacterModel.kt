package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterStoredUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.SetCharacterStoredUseCase

class CharacterModel(private val getCharacterServiceUseCase: GetCharacterServiceUseCase,
                     private val getCharacterStoredUseCase: GetCharacterStoredUseCase,
                     private val setCharacterStoredUseCase: SetCharacterStoredUseCase) {

    fun getCharacterDataServiceUseCase() = getCharacterServiceUseCase.invoke()
    fun getCharacterStoredUseCase() = getCharacterStoredUseCase.invoke()
    fun setCharacterStoredUseCase(characters: List<Character>) = setCharacterStoredUseCase.invoke(characters)

}
