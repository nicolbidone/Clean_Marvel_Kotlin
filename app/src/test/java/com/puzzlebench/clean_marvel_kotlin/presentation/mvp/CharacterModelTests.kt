package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterServices
import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterStored
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterStoredUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.SetCharacterStoredUseCase
import com.puzzlebench.clean_marvel_kotlin.mocks.factory.CharactersFactory
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CharacterModelTests {

    private var characterServiceImp = Mockito.mock(CharacterServices::class.java)
    private var characterStoredImp = Mockito.mock(CharacterStored::class.java)

    private lateinit var model: CharacterContracts.Model
    private lateinit var getCharacterServiceUseCase: GetCharacterServiceUseCase
    private lateinit var getCharacterStoredUseCase: GetCharacterStoredUseCase
    private lateinit var setCharacterStoredUseCase: SetCharacterStoredUseCase

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        getCharacterServiceUseCase = GetCharacterServiceUseCase(characterServiceImp)
        getCharacterStoredUseCase = GetCharacterStoredUseCase(characterStoredImp)
        setCharacterStoredUseCase = SetCharacterStoredUseCase(characterStoredImp)

        model = CharacterModel(getCharacterServiceUseCase, getCharacterStoredUseCase, setCharacterStoredUseCase)

    }

    @Test
    fun isOkGetCharacterDataServiceUseCase() {
        val itemsCharacters = CharactersFactory.getMockCharacter()
        val observable = Observable.just(itemsCharacters)
        Mockito.`when`(getCharacterServiceUseCase()).thenReturn(observable)
    }

    @Test
    fun isOkGetCharacterStoredUseCase() {
        val itemsCharacters = CharactersFactory.getMockCharacter()
        Mockito.`when`(getCharacterStoredUseCase()).thenReturn(itemsCharacters)
    }

    @Test
    fun isOkSetCharacterStoredUseCase() {
        val itemsCharacters = CharactersFactory.getMockCharacter()
        Mockito.`when`(setCharacterStoredUseCase(itemsCharacters)).then { }
    }
}
