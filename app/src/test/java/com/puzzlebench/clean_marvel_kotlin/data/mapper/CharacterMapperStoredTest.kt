package com.puzzlebench.clean_marvel_kotlin.data.mapper

import com.puzzlebench.clean_marvel_kotlin.data.service.response.CharacterResponse
import com.puzzlebench.clean_marvel_kotlin.data.service.response.ThumbnailResponse
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.RealmCharacter
import com.puzzlebench.clean_marvel_kotlin.domain.model.RealmThumbnail
import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class CharacterMapperStoredTest {

    companion object {
        private const val ID = 1
        private const val NAME = "sport"
        private const val DESCRIPTION = "some description"
        private const val PATH = "http:/some.com/"
        private const val EXTENSION = ".PNG"
    }

    private lateinit var mapper: CharacterMapperStored
    @Mock
    private lateinit var mockRealmCharacter: RealmCharacter
    @Mock
    private lateinit var mockRealmThumbnail: RealmThumbnail
    @Mock
    private lateinit var mockCharacter: Character
    @Mock
    private lateinit var mockThumbnail: Thumbnail

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockRealmCharacter.thumbnail = mockRealmThumbnail
        mockCharacter.thumbnail = mockThumbnail
        mapper = CharacterMapperStored()
    }

    @Test
    fun transform() {
        `when`(mockRealmCharacter.id).thenReturn(ID)
        `when`(mockRealmCharacter.name).thenReturn(NAME)
        `when`(mockRealmCharacter.description).thenReturn(DESCRIPTION)
        `when`(mockRealmCharacter.thumbnail).thenReturn(mockRealmThumbnail)
        `when`(mockRealmThumbnail.extension).thenReturn(EXTENSION)
        `when`(mockRealmThumbnail.path).thenReturn(PATH)

        assertBufferooDataEquality(mockRealmCharacter, mapper.transform(mockRealmCharacter))
    }
    
    @Test
    fun transformToResponse() {
        `when`(mockCharacter.id).thenReturn(ID)
        `when`(mockCharacter.name).thenReturn(NAME)
        `when`(mockCharacter.description).thenReturn(DESCRIPTION)
        `when`(mockCharacter.thumbnail).thenReturn(mockThumbnail)
        `when`(mockThumbnail.extension).thenReturn(EXTENSION)
        `when`(mockThumbnail.path).thenReturn(PATH)

        assertBufferooDataEquality(mapper.transformToResponse(mockCharacter), mockCharacter)
    }

    private fun assertBufferooDataEquality(characterResponse: RealmCharacter, character: Character) {
        assertEquals(characterResponse.name, character.name)
        assertEquals(characterResponse.description, character.description)
        assertEquals(characterResponse.thumbnail?.path, character.thumbnail.path)
        assertEquals(characterResponse.thumbnail?.extension, character.thumbnail.extension)
    }
}
