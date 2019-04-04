package com.puzzlebench.clean_marvel_kotlin.fragment.mvp

import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.fragment.CharacterFragment
import io.reactivex.Observable

interface FragmentContracts {

    interface Model {
        fun getSingleCharacterServiceUseCase(): Observable<List<Character>>
    }

    interface View {
        fun showFragmentDialog(characterFragment: CharacterFragment)
        fun showToastNoItemToShow()
        fun showToastNetworkError(error: String)
    }

    interface Presenter {
        fun init(characterFragment: CharacterFragment)
    }
}
