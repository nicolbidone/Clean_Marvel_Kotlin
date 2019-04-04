package com.puzzlebench.clean_marvel_kotlin.fragment.mvp

import com.puzzlebench.clean_marvel_kotlin.fragment.CharacterFragment
import com.puzzlebench.clean_marvel_kotlin.presentation.base.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FragmentPresenter(view: FragmentContracts.View, private val model: FragmentContracts.Model)
    : Presenter<FragmentContracts.View>(view), FragmentContracts.Presenter {

    override fun init(characterFragment: CharacterFragment) {
        requestGetCharacters(characterFragment)
    }

    private fun requestGetCharacters(characterFragment: CharacterFragment) {
        model.getSingleCharacterServiceUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ characters ->
                    if (characters.isEmpty()) {
                        view.showToastNoItemToShow()
                    } else {
                        view.showFragmentDialog(characterFragment)
                    }
                }, { e ->
                    view.showToastNetworkError(e.message.toString())
                })
    }
}
