package com.puzzlebench.clean_marvel_kotlin.data.service

import com.puzzlebench.clean_marvel_kotlin.data.mapper.CharacterMapperService
import com.puzzlebench.clean_marvel_kotlin.data.service.api.MarvelApi
import com.puzzlebench.clean_marvel_kotlin.data.service.response.CharacterResponse
import com.puzzlebench.clean_marvel_kotlin.data.service.response.DataBaseResponse
import com.puzzlebench.clean_marvel_kotlin.data.service.response.MarvelBaseResponse
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import io.reactivex.Observable
import retrofit2.Call
import java.util.*

class CharacterServicesImpl(private val api: MarvelResquestGenerator = MarvelResquestGenerator(),
                            private val mapper: CharacterMapperService = CharacterMapperService()) {

    fun getCaracters(): Observable<List<Character>> {
        val callResponse = api.createService(MarvelApi::class.java).getCharacter()
        return getter(callResponse)
    }

    fun getSingleCaracter(id: Int): Observable<List<Character>> {
        val callResponse = api.createService(MarvelApi::class.java).getSingleCharacter(id)
        return getter(callResponse)
    }

    private fun getter(callResponse: Call<MarvelBaseResponse<DataBaseResponse<ArrayList<CharacterResponse>>>>): Observable<List<Character>> {
        return Observable.create { subscriber ->

            val response = callResponse.execute()
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    subscriber.onNext(mapper.transform(it.characters))
                    subscriber.onComplete()
                }
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}
