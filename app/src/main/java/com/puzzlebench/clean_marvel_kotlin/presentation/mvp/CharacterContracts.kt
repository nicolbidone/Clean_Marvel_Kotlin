package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import io.reactivex.Observable

interface CharacterContracts {
    interface Model {
        fun getCharacterDataServiceUseCase(): Observable<List<Character>>
        fun getCharacterStoredUseCase(): List<Character>
        fun setCharacterStoredUseCase(characters: List<Character>)
    }

    interface Presenter {
        fun init()
        fun requestGetCharacters()
    }

    interface View {
        fun init()
        fun showToastNoItemToShow()
        fun showToastNetworkError(error: String)
        fun hideLoading()
        fun showCharacters(characters: List<Character>)
    }
}
