package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.presentation.base.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm


class CharacterPresenter(view: CharacterView, val model: CharacterModel, private val getCharacterServiceUseCase:
GetCharacterServiceUseCase, val subscriptions: CompositeDisposable) : Presenter<CharacterView>(view) {

    lateinit var realm: Realm

    fun init() {
        view.init()
        model.init()
        val lis = model.getCharacters()
        if (lis.isNotEmpty()) {
            view.showCharacters(lis)
        }
    }

    fun requestGetCharacters() {

        val subscription = getCharacterServiceUseCase.invoke().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ characters ->
            if (characters.isEmpty()) {
                view.showToastNoItemToShow()
            } else {
                model.realmSetter(characters)
                view.showCharacters(characters)
            }
            view.hideLoading()

        }, { e ->
            view.hideLoading()
            view.showToastNetworkError(e.message.toString())
        })
        subscriptions.add(subscription)
    }

    fun close() {
        model.close()
    }
}
