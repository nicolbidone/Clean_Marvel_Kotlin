package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterStored
import com.puzzlebench.clean_marvel_kotlin.mocks.factory.CharactersFactory
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class SetCharacterStoredUseCaseTest {

    private val itemsCharacters = CharactersFactory.getMockCharacter()

    private lateinit var characterStored: CharacterStored

    @Before
    fun setUp() {
        characterStored = mock(CharacterStored::class.java)
        `when`(characterStored.setCharacters(itemsCharacters)).then {}
    }

    @Test
    operator fun invoke() {
        val setCharacterStoredUseCase = SetCharacterStoredUseCase(characterStored)
        setCharacterStoredUseCase.invoke(itemsCharacters)
        verify(characterStored).setCharacters(itemsCharacters)
    }
}
