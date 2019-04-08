package com.puzzlebench.clean_marvel_kotlin.domain.contracts

import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import io.reactivex.Observable

interface CharacterServices {
    fun getCharacters(): Observable<List<Character>>
    fun getSingleCharacter(id: Int): Observable<List<Character>>
}
