package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.puzzlebench.clean_marvel_kotlin.presentation.base.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class CharacterPresenter(view: CharacterContracts.View, val model: CharacterContracts.Model)
    : Presenter<CharacterContracts.View>(view), CharacterContracts.Presenter {

    override fun init() {
        view.init()
        val lis = model.getCharacterStoredUseCase()
        if (lis.isNotEmpty()) {
            view.showCharacters(lis)
        }
    }

    override fun requestGetCharacters() {

        model.getCharacterDataServiceUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ characters ->
                    if (characters.isEmpty()) {
                        view.showToastNoItemToShow()
                    } else {
                        model.setCharacterStoredUseCase(characters)
                        view.showCharacters(characters)
                    }
                    view.hideLoading()

                }, { e ->
                    view.hideLoading()
                    view.showToastNetworkError(e.message.toString())
                })
    }

}
