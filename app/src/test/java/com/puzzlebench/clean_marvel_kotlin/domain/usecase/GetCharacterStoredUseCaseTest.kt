package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterStored
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class GetCharacterStoredUseCaseTest {

    @Mock
    private lateinit var characterStored: CharacterStored
    @Mock
    private lateinit var videoItems: List<Character>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(characterStored.getCharacters()).thenReturn(videoItems)
    }

    @Test
    operator fun invoke() {
        GetCharacterStoredUseCase(characterStored).invoke()
        verify(characterStored).getCharacters()
    }
}
