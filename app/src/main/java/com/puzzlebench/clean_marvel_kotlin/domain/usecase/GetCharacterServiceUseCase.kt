package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.domain.CharacterServices
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import io.reactivex.Observable

open class GetCharacterServiceUseCase(private val characterService: CharacterServices) {

    open operator fun invoke(): Observable<List<Character>> = characterService.getCharacters()
}
