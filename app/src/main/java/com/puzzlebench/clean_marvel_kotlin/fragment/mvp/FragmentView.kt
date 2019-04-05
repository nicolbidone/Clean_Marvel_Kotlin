package com.puzzlebench.clean_marvel_kotlin.fragment.mvp

import com.puzzlebench.clean_marvel_kotlin.FRAGMENT_TAG
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.fragment.CharacterFragment
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.showToast
import java.lang.ref.WeakReference

class FragmentView(activity: MainActivity) : FragmentContracts.View {

    private val activityRef = WeakReference(activity)

    override fun showFragmentDialog(characterFragment: CharacterFragment) {
        val fragmentManager = activityRef.get()?.supportFragmentManager
        characterFragment.show(fragmentManager, FRAGMENT_TAG)
    }

    override fun showToastNoItemToShow() {
        val activity = activityRef.get()
        val message = activity?.baseContext?.resources?.getString(R.string.message_no_items_to_show)
        activity?.applicationContext?.showToast(message as String)
    }

    override fun showToastNetworkError(error: String) {
        activityRef.get()?.applicationContext?.showToast(error)
    }

}
