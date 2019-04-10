package com.puzzlebench.clean_marvel_kotlin.data.service

import android.util.Log
import com.puzzlebench.clean_marvel_kotlin.ID_TEXT
import com.puzzlebench.clean_marvel_kotlin.MESSAGE_EXISTING_OBJECT
import com.puzzlebench.clean_marvel_kotlin.REALM_TAG
import com.puzzlebench.clean_marvel_kotlin.data.mapper.CharacterMapperStored
import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterStored
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.RealmCharacter
import io.realm.Realm
import io.realm.kotlin.where

class CharacterStoredImpl : CharacterStored {

    override fun getCharacters(): List<Character> {
        val realm = Realm.getDefaultInstance()
        val lis = mutableListOf<Character>()

        lis.addAll(CharacterMapperStored().transform(realm.where<RealmCharacter>().findAllAsync()))

        realm.close()
        return lis
    }

    override fun setCharacters(characters: List<Character>) {
        val realm = Realm.getDefaultInstance()
        val mapper = CharacterMapperStored()

        realm.executeTransaction { realmObject ->
            for (character in characters) {
                val hasId = realmObject.where<RealmCharacter>().equalTo(ID_TEXT, character.id).findFirst()
                if (hasId == null) {
                    mapper.transformToResponse(character)
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
