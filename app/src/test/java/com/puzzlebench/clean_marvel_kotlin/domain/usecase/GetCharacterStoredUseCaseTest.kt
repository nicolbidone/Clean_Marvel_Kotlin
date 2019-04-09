package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterStored
import com.puzzlebench.clean_marvel_kotlin.mocks.factory.CharactersFactory
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GetCharacterStoredUseCaseTest {

    private lateinit var characterStored: CharacterStored

    @Before
    fun setUp() {
        val videoItems = CharactersFactory.getMockCharacter()
        characterStored = mock(CharacterStored::class.java)
        `when`(characterStored.getCharacters()).thenReturn(videoItems)
    }

    @Test
    operator fun invoke() {
        val getCharacterStoredUseCase = GetCharacterStoredUseCase(characterStored)
        getCharacterStoredUseCase.invoke()
        verify(characterStored).getCharacters()
    }
}
