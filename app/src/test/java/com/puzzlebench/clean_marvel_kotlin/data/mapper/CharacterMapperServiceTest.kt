package com.puzzlebench.clean_marvel_kotlin.data.mapper

import com.puzzlebench.clean_marvel_kotlin.data.service.response.CharacterResponse
import com.puzzlebench.clean_marvel_kotlin.data.service.response.ThumbnailResponse
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class CharacterMapperServiceTest {

    companion object {
        private const val ID = 1
        private const val NAME = "sport"
        private const val DESCRIPTION = "some description"
        private const val PATH = "http:/some.com/"
        private const val EXTENSION = ".PNG"
    }

    private lateinit var mapper: CharacterMapperService
    @Mock
    private lateinit var mockCharacterResponse: CharacterResponse
    @Mock
    private lateinit var mockThumbnailResponse: ThumbnailResponse
    @Mock
    private lateinit var mockCharacter: Character
    @Mock
    private lateinit var mockThumbnail: Thumbnail

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockCharacterResponse.thumbnail = mockThumbnailResponse
        mockCharacter.thumbnail = mockThumbnail
        mapper = CharacterMapperService()
    }

    @Test
    fun transform() {
        `when`(mockCharacterResponse.id).thenReturn(ID)
        `when`(mockCharacterResponse.name).thenReturn(NAME)
        `when`(mockCharacterResponse.description).thenReturn(DESCRIPTION)
        `when`(mockCharacterResponse.thumbnail).thenReturn(mockThumbnailResponse)
        `when`(mockThumbnailResponse.extension).thenReturn(EXTENSION)
        `when`(mockThumbnailResponse.path).thenReturn(PATH)

        assertBufferDataEquality(mockCharacterResponse, mapper.transform(mockCharacterResponse))
    }

    @Test
    fun transformToResponse() {
        `when`(mockCharacter.id).thenReturn(ID)
        `when`(mockCharacter.name).thenReturn(NAME)
        `when`(mockCharacter.description).thenReturn(DESCRIPTION)
        `when`(mockCharacter.thumbnail).thenReturn(mockThumbnail)
        `when`(mockThumbnail.extension).thenReturn(EXTENSION)
        `when`(mockThumbnail.path).thenReturn(PATH)

        assertBufferDataEquality(mapper.transformToResponse(mockCharacter), mockCharacter)
    }

    private fun assertBufferDataEquality(characterResponse: CharacterResponse, character: Character) {
        assertEquals(characterResponse.name, character.name)
        assertEquals(characterResponse.description, character.description)
        assertEquals(characterResponse.thumbnail.path, character.thumbnail.path)
        assertEquals(characterResponse.thumbnail.extension, character.thumbnail.extension)
    }
}
