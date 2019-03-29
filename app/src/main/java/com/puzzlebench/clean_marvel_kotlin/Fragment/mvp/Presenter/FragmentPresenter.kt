package com.puzzlebench.clean_marvel_kotlin.Fragment.mvp.Presenter

import android.support.v4.app.Fragment
import com.puzzlebench.clean_marvel_kotlin.Fragment.mvp.CharacterFragment
import com.puzzlebench.clean_marvel_kotlin.Fragment.mvp.View.FragmentView
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetSingleCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.presentation.base.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FragmentPresenter(view: FragmentView,
                        private val getSingleCharacterServiceUseCase: GetSingleCharacterServiceUseCase)
    : Presenter<FragmentView>(view) {//, val subscriptions: CompositeDisposable) : Presenter<FragmentView>(view) {

    fun init(characterFragment: CharacterFragment) {
        requestGetCharacters(characterFragment)
    }

    private fun requestGetCharacters(characterFragment: CharacterFragment) {
        val character = getSingleCharacterServiceUseCase.invoke().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ characters ->
                    if (characters.isEmpty()) {
                        view.showToastNoItemToShow()
                    } else {
                        view.showFragmentDialog(characters.first(),characterFragment)
                    }
                }, { e ->
                    view.showToastNetworkError(e.message.toString())
                })
    }
}