package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import android.util.Log
import com.puzzlebench.clean_marvel_kotlin.CONSTS
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.RealmCharacter
import com.puzzlebench.clean_marvel_kotlin.domain.model.RealmThumbnail
import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class CharacterModel {

    lateinit var realm: Realm

    fun init() {
        realm = Realm.getDefaultInstance()
    }

    fun getCharacters(): List<Character> {
        if (realm.where<RealmCharacter>().findAll().size > 0) {
            return realmGetter()
        }
        return mutableListOf()
    }

    private fun realmGetter(): List<Character> {
        var lis = mutableListOf<Character>()
        val findAllAsync = realm.where<RealmCharacter>().findAllAsync()
        for (fin in findAllAsync) {

            var cha = Character(
                    fin.id,
                    fin.name,
                    fin.description,
                    Thumbnail(fin.thumbnail!!.path, fin.thumbnail!!.extension)
            )
            lis.add(cha)
        }

        return lis
    }

    fun realmSetter(characters: List<Character>) {
        realm.executeTransaction { realm ->
            for (cha in characters) {
                val rea = realm.where<RealmCharacter>().equalTo(CONSTS.ID_TEXT, cha.id).findFirst()
                if (rea == null) {
                    val ch = realm.createObject<RealmCharacter>(cha.id)
                    ch.name = cha.name
                    ch.description = cha.description
                    val thum = realm.createObject<RealmThumbnail>()
                    thum.path = cha.thumbnail.path
                    thum.extension = cha.thumbnail.extension
                    ch.thumbnail = thum
                } else {
                    showStatus(CONSTS.MESSAGE_EXISTING_OBJECT)
                }
            }

        }

    }

    fun close() {
        realm.executeTransaction { realm ->
            realm.deleteAll()
        }
        realm.close()
    }

    private fun showStatus(text: String) {
        Log.i(CONSTS.REALM_TAG, text)
    }

}
