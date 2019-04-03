package com.puzzlebench.clean_marvel_kotlin.domain.model

import com.puzzlebench.clean_marvel_kotlin.CONSTS
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmCharacter(
        @PrimaryKey var id: Int = CONSTS.ZERO_VALUE,
        var name: String = CONSTS.EMPTY_VALUE,
        var description: String = CONSTS.EMPTY_VALUE,
        var thumbnail: RealmThumbnail? = null
): RealmObject()
