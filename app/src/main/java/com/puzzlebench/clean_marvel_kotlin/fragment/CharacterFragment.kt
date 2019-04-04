package com.puzzlebench.clean_marvel_kotlin.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import com.puzzlebench.clean_marvel_kotlin.DOT
import com.puzzlebench.clean_marvel_kotlin.NO_DESCRIPTION
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.SCREEN_PERCENTAGE
import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterServicesImpl
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetSingleCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.fragment.mvp.FragmentModel
import com.puzzlebench.clean_marvel_kotlin.fragment.mvp.FragmentPresenter
import com.puzzlebench.clean_marvel_kotlin.fragment.mvp.FragmentView
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.getImageByUrl
import kotlinx.android.synthetic.main.fragment_character.view.*

class CharacterFragment : DialogFragment() {

    private val getSingleCharacterServiceUseCase = GetSingleCharacterServiceUseCase(CharacterServicesImpl(), character.id)
    private val presenter = FragmentPresenter(FragmentView(mainActivity), FragmentModel(getSingleCharacterServiceUseCase))

    companion object {
        private lateinit var character: Character
        private lateinit var mainActivity: MainActivity

        fun newInstance(character: Character, activity: MainActivity): CharacterFragment {
            this.character = character
            mainActivity = activity
            return CharacterFragment()
        }
    }

    fun init() {
        presenter.init(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val frag = inflater.inflate(R.layout.fragment_character, container, false)
        frag.text_characterName.text = character.name
        frag.text_characterDesc.text = if (!character.description.isEmpty()) character.description else NO_DESCRIPTION
        frag.imageView.getImageByUrl("${character.thumbnail.path}$DOT${character.thumbnail.extension}")
        var size = Point()
        (mainActivity.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(size)
        frag.imageView.layoutParams.width = (size.x * SCREEN_PERCENTAGE).toInt()

        return frag
    }
}
