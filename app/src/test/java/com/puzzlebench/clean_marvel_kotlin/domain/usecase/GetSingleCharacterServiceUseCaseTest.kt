package com.puzzlebench.clean_marvel_kotlin.domain.usecase


import com.puzzlebench.clean_marvel_kotlin.ZERO_VALUE
import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterServices
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class GetSingleCharacterServiceUseCaseTest {

    @Mock
    private lateinit var characterService: CharacterServices
    @Mock
    private lateinit var videoItems: List<Character>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(characterService.getCharacters()).thenReturn(Observable.just(videoItems))
    }

    @Test
    operator fun invoke() {
        GetSingleCharacterServiceUseCase(characterService, ZERO_VALUE).invoke()
        verify(characterService).getSingleCharacter(ZERO_VALUE)
    }
}
