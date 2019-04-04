package com.puzzlebench.clean_marvel_kotlin.domain.model

import com.puzzlebench.clean_marvel_kotlin.EMPTY_VALUE
import com.puzzlebench.clean_marvel_kotlin.THUMBNAIL_ID
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects

open class RealmThumbnail : RealmObject() {
    var path: String = EMPTY_VALUE
    var extension: String = EMPTY_VALUE
    @LinkingObjects(THUMBNAIL_ID)
    val character: RealmResults<RealmCharacter>? = null
}
