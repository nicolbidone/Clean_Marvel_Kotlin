package com.puzzlebench.clean_marvel_kotlin.fragment.mvp

import com.puzzlebench.clean_marvel_kotlin.ZERO_VALUE
import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterServices
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetSingleCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.mocks.factory.CharactersFactory
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class FragmentModelTest {

    private var characterService = Mockito.mock(CharacterServices::class.java)

    private lateinit var model: FragmentContracts.Model
    private lateinit var getSingleCharacterServiceUseCase: GetSingleCharacterServiceUseCase

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        getSingleCharacterServiceUseCase = GetSingleCharacterServiceUseCase(characterService, ZERO_VALUE)
        model = FragmentModel(getSingleCharacterServiceUseCase)
    }

    @Test
    fun isOkGetSingleCharacterServiceUseCase() {
        val itemsCharacters = CharactersFactory.getMockCharacter()
        val observable = Observable.just(itemsCharacters)
        Mockito.`when`(getSingleCharacterServiceUseCase()).thenReturn(observable)
    }
}
