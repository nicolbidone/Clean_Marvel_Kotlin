package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.puzzlebench.clean_marvel_kotlin.Fragment.mvp.CharacterFragment
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.adapter.CharacterAdapter
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.showToast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class CharecterView(activity: MainActivity) {
    companion object {
        private const val SPAN_COUNT = 1
        private const val FRAGMENT_TAG = "FRAGMENT_TAG"
    }

    private val activityRef = WeakReference(activity)

    var adapter = CharacterAdapter { character ->
        //activity.applicationContext.showToast(character.name+ "Yeah")
        showFragmentDialog(character)
        //FragmentManager(character.id,activity)
    }

    fun init() {
        val activity = activityRef.get()
        if (activity != null) {
            activity.recycleView.layoutManager = GridLayoutManager(activity, SPAN_COUNT)
            activity.recycleView.adapter = adapter
            showLoading()
        }
    }

    fun showToastNoItemToShow() {
        val activity = activityRef.get()
        if (activity != null) {
            val message = activity.baseContext.resources.getString(R.string.message_no_items_to_show)
            activity.applicationContext.showToast(message)

        }
    }

    fun showToastNetworkError(error: String) {
        activityRef.get()!!.applicationContext.showToast(error)
    }

    fun hideLoading() {
        activityRef.get()!!.progressBar.visibility = View.GONE
    }

    fun showCharacters(characters: List<Character>) {
        adapter.data = characters
    }

    fun showLoading() {
        activityRef.get()!!.progressBar.visibility = View.VISIBLE
    }

    fun showFragmentDialog(character: Character) {
        //val fragmentManager = activityRef.get()?.supportFragmentManager
        val newFragment = CharacterFragment.newInstance(character, activityRef.get()!!)
        newFragment.init()
        //newFragment.show(fragmentManager, FRAGMENT_TAG)
    }
}
