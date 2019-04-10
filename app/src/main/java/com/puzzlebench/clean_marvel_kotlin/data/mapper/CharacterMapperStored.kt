package com.puzzlebench.clean_marvel_kotlin.data.mapper

import com.puzzlebench.clean_marvel_kotlin.EMPTY_VALUE
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.RealmCharacter
import com.puzzlebench.clean_marvel_kotlin.domain.model.RealmThumbnail
import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail

open class CharacterMapperStored : BaseMapperRepository<RealmCharacter, Character> {


    override fun transform(character: RealmCharacter): Character = Character(
            character.id,
            character.name,
            character.description,
            transformToRealmThumbnail(character.thumbnail ?: RealmThumbnail())
    )

    override fun transformToResponse(type: Character): RealmCharacter = RealmCharacter(
            type.id,
            type.name,
            type.description,
            transformToThumbnail(type.thumbnail)
    )

    fun transformToRealmThumbnail(realmThumbnail: RealmThumbnail): Thumbnail = Thumbnail(
            realmThumbnail.path,
            realmThumbnail.extension
    )

    fun transformToThumbnail(thumbnail: Thumbnail): RealmThumbnail = RealmThumbnail(
            thumbnail.path,
            thumbnail.extension
    )

    fun transform(realmCharacters: List<RealmCharacter>): List<Character> = realmCharacters.map { transform(it) }
}
