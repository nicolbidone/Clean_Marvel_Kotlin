package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterStored
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SetCharacterStoredUseCaseTest {

    @Mock
    private lateinit var itemsCharacters: List<Character>
    @Mock
    private lateinit var characterStored: CharacterStored

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(characterStored.setCharacters(itemsCharacters)).then {}
    }

    @Test
    operator fun invoke() {
        SetCharacterStoredUseCase(characterStored).invoke(itemsCharacters)
        verify(characterStored).setCharacters(itemsCharacters)
    }
}
