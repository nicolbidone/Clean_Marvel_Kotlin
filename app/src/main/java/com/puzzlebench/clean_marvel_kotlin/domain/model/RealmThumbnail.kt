package com.puzzlebench.clean_marvel_kotlin.domain.model

import com.puzzlebench.clean_marvel_kotlin.CONSTS
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects

open class RealmThumbnail : RealmObject() {
    var path: String = CONSTS.EMPTY_VALUE
    var extension: String = CONSTS.EMPTY_VALUE
    @LinkingObjects(CONSTS.THUMBNAIL_ID)
    val character: RealmResults<RealmCharacter>? = null
}
