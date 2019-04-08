package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.puzzlebench.clean_marvel_kotlin.EMPTY_VALUE
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.SPAN_COUNT
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.fragment.CharacterFragment
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.adapter.CharacterAdapter
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.showToast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class CharacterView(activity: MainActivity) : CharacterContracts.View {

    private val activityRef = WeakReference(activity)
    private var adapter = CharacterAdapter { character -> showFragmentDialog(character) }

    override fun init() {
        val activity = activityRef.get()
        activity?.recycleView?.layoutManager = GridLayoutManager(activity, SPAN_COUNT)
        activity?.recycleView?.adapter = adapter
    }

    override fun showToastNoItemToShow() {
        val activity = activityRef.get()
        val message = activity?.baseContext?.resources?.getString(R.string.message_no_items_to_show)
        activity?.applicationContext?.showToast(message ?: EMPTY_VALUE)
    }

    override fun showToastNetworkError(error: String) {
        activityRef.get()?.applicationContext?.showToast(error)
    }

    override fun hideLoading() {
        activityRef.get()?.progressBar?.visibility = View.GONE
    }

    override fun showCharacters(characters: List<Character>) {
        adapter.data = characters
    }

    override fun showLoading() {
        activityRef.get()?.progressBar?.visibility = View.VISIBLE
    }

    private fun showFragmentDialog(character: Character) {
        val newFragment = CharacterFragment.newInstance(character, activityRef.get()!!)
        newFragment.init()
    }

}
