package com.puzzlebench.clean_marvel_kotlin.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterServicesImpl
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetSingleCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.fragment.mvp.presenter.FragmentPresenter
import com.puzzlebench.clean_marvel_kotlin.fragment.mvp.view.FragmentView
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.getImageByUrl
import kotlinx.android.synthetic.main.fragment_character.view.*

class CharacterFragment : DialogFragment() {

    companion object {
        private lateinit var character: Character
        private const val DOT = "."
        private lateinit var theactivity: MainActivity

        fun newInstance(character: Character, activity: MainActivity): CharacterFragment {
            this.character = character
            theactivity = activity
            return CharacterFragment()
        }
    }

    fun init() {
        val getSingleCharacterServiceUseCase = GetSingleCharacterServiceUseCase(CharacterServicesImpl(), character.id)
        val presenter = FragmentPresenter(FragmentView(theactivity), getSingleCharacterServiceUseCase)
        presenter.init(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val frag = inflater.inflate(R.layout.fragment_character, container, false)
        frag.text_characterName.text = character.name
        frag.text_characterDesc.text = character.description
        frag.imageView.getImageByUrl(character.thumbnail.path + DOT + character.thumbnail.extension)
        return frag
    }

}