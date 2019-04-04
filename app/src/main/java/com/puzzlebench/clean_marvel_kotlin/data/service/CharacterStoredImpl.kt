package com.puzzlebench.clean_marvel_kotlin.data.service

import android.util.Log
import com.puzzlebench.clean_marvel_kotlin.ID_TEXT
import com.puzzlebench.clean_marvel_kotlin.MESSAGE_EXISTING_OBJECT
import com.puzzlebench.clean_marvel_kotlin.REALM_TAG
import com.puzzlebench.clean_marvel_kotlin.domain.CharacterStored
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.RealmCharacter
import com.puzzlebench.clean_marvel_kotlin.domain.model.RealmThumbnail
import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class CharacterStoredImpl : CharacterStored {

    override fun getCharacters(): List<Character> {

        val realm = Realm.getDefaultInstance()

        var lis = mutableListOf<Character>()
        val findAllAsync = realm.where<RealmCharacter>().findAllAsync()
        for (fin in findAllAsync) {

            var character = Character(
                    fin.id,
                    fin.name,
                    fin.description,
                    Thumbnail(fin.thumbnail?.path ?: "", fin.thumbnail?.extension ?: "")
            )
            lis.add(character)
        }

        realm.close()

        return lis
    }

    override fun setCharacters(characters: List<Character>) {
        val realm = Realm.getDefaultInstance()

        realm.executeTransaction { realmObject ->
            for (character in characters) {
                val hasId = realmObject.where<RealmCharacter>().equalTo(ID_TEXT, character.id).findFirst()
                if (hasId == null) {
                    val aux = realmObject.createObject<RealmCharacter>(character.id)
                    aux.name = character.name
                    aux.description = character.description
                    val thumbnail = realmObject.createObject<RealmThumbnail>()
                    thumbnail.path = character.thumbnail.path
                    thumbnail.extension = character.thumbnail.extension
                    aux.thumbnail = thumbnail
                } else {
                    showStatus(MESSAGE_EXISTING_OBJECT)
                }
            }

        }
        realm.close()
    }

    private fun showStatus(text: String) {
        Log.i(REALM_TAG, text)
    }
}
