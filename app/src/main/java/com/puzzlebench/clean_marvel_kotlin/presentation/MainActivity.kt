package com.puzzlebench.clean_marvel_kotlin.presentation

import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterServicesImpl
import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterStoredImpl
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterStoredUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.SetCharacterStoredUseCase
import com.puzzlebench.clean_marvel_kotlin.presentation.base.BaseRxActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.CharacterModel
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.CharacterPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.CharacterView
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*


open class MainActivity : BaseRxActivity() {

    private val getCharacterServiceUseCase = GetCharacterServiceUseCase(CharacterServicesImpl())
    private val getCharacterStoredUseCase = GetCharacterStoredUseCase(CharacterStoredImpl())
    private val setCharacterStoredUseCase = SetCharacterStoredUseCase(CharacterStoredImpl())
    private val presenter = CharacterPresenter(CharacterView(this),
            CharacterModel(getCharacterServiceUseCase, getCharacterStoredUseCase, setCharacterStoredUseCase))

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        fab_download.setOnClickListener {
            presenter.requestGetCharacters()
        }

        fab_getStored.setOnClickListener {
            presenter.requestStoredCharacters()
        }

        fab_clean.setOnClickListener {
            realmClean()
        }

        presenter.init()
    }

    private fun realmClean() {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realmObject ->
            realmObject.deleteAll()
        }
        realm.close()
    }
}
