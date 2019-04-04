package com.puzzlebench.clean_marvel_kotlin.fragment.mvp

import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetSingleCharacterServiceUseCase

class FragmentModel(private val getSingleCharacterServiceUseCase: GetSingleCharacterServiceUseCase) {
    fun getSingleCharacterServiceUseCase() = getSingleCharacterServiceUseCase.invoke()
}
