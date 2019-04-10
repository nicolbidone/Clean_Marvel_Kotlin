package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterServices
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import io.reactivex.Observable

open class GetSingleCharacterServiceUseCase(private val characterService: CharacterServices, private val id: Int) {

    open operator fun invoke(): Observable<List<Character>> = characterService.getSingleCharacter(id)
}
