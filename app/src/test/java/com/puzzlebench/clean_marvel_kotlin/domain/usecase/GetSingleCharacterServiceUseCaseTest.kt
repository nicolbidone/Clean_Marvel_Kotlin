package com.puzzlebench.clean_marvel_kotlin.domain.usecase


import com.puzzlebench.clean_marvel_kotlin.ZERO_VALUE
import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterServices
import com.puzzlebench.clean_marvel_kotlin.mocks.factory.CharactersFactory
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GetSingleCharacterServiceUseCaseTest {

    private lateinit var characterService: CharacterServices

    @Before
    fun setUp() {
        val videoItems = CharactersFactory.getMockCharacter()
        val observable = Observable.just(videoItems)
        characterService = mock(CharacterServices::class.java)
        `when`(characterService.getCharacters()).thenReturn(observable)
    }

    @Test
    operator fun invoke() {
        val getSingleCharacterServiceUseCase = GetSingleCharacterServiceUseCase(characterService, ZERO_VALUE)
        getSingleCharacterServiceUseCase.invoke()
        verify(characterService).getSingleCharacter(ZERO_VALUE)
    }
}
