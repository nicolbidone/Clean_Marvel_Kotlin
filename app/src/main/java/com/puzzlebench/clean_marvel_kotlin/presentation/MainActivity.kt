package com.puzzlebench.clean_marvel_kotlin.presentation

import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.support.v4.content.res.ResourcesCompat
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

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        val typeface = ResourcesCompat.getFont(applicationContext, R.font.marvel_regular)
        toolbar_layout.setCollapsedTitleTypeface(typeface)
        toolbar_layout.setExpandedTitleTypeface(typeface)

        setSupportActionBar(toolbar)

        fab_download.setOnClickListener {
            presenter.requestGetCharacters()
            editor.putBoolean("recycler_show",true)
            editor.commit()
        }

        fab_getStored.setOnClickListener {
            presenter.requestStoredCharacters()
            editor.putBoolean("recycler_show",true)
            editor.commit()
        }

        fab_clean.setOnClickListener {
            realmClean()
            editor.putBoolean("recycler_show",false)
            editor.commit()
        }

        if (pref.getBoolean("recycler_show",false)){
            presenter.requestStoredCharacters()
        }

        presenter.init()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun realmClean() {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realmObject ->
            realmObject.deleteAll()
        }
        realm.close()
    }
}
