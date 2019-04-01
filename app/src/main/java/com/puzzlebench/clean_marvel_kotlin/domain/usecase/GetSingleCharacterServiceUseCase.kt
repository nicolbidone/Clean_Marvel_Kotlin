package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterServicesImpl
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import io.reactivex.Observable

open class GetSingleCharacterServiceUseCase(private val characterServiceImp: CharacterServicesImpl, private val id: Int) {

    open operator fun invoke(): Observable<List<Character>> = characterServiceImp.getSingleCaracter(id)
}
