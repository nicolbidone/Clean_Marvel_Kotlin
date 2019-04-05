package com.puzzlebench.clean_marvel_kotlin.domain.model

import com.puzzlebench.clean_marvel_kotlin.EMPTY_VALUE
import com.puzzlebench.clean_marvel_kotlin.ZERO_VALUE
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmCharacter(
        @PrimaryKey var id: Int = ZERO_VALUE,
        var name: String = EMPTY_VALUE,
        var description: String = EMPTY_VALUE,
        var thumbnail: RealmThumbnail? = null
): RealmObject()
