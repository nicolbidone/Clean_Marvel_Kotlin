package com.puzzlebench.clean_marvel_kotlin.domain.contracts

import com.puzzlebench.clean_marvel_kotlin.domain.model.Character

interface CharacterStored {
    fun getCharacters(): List<Character>
    fun setCharacters(characters: List<Character>)
}
