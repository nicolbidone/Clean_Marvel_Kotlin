package com.puzzlebench.clean_marvel_kotlin.Fragment.mvp.View

import android.support.v4.app.Fragment
import com.puzzlebench.clean_marvel_kotlin.Fragment.mvp.CharacterFragment
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.showToast
import java.lang.ref.WeakReference

class FragmentView(activity: MainActivity) {

    companion object {
        private const val FRAGMENT_TAG = "FRAGMENT_TAG"
    }

    private val activityRef = WeakReference(activity)

    fun showFragmentDialog(characters: Character, characterFragment: CharacterFragment) {
        val fragmentManager = activityRef.get()?.supportFragmentManager
//        val newFragment = CharacterFragment.newInstance(characters, activityRef.get()!!)
        characterFragment.show(fragmentManager, FRAGMENT_TAG)
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

}