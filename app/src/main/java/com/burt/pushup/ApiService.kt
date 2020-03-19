package com.burt.pushup

import com.burt.pushup.bean.Music
import com.burt.pushup.bean.MusicDetail
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("musicRankings")
    fun getMusics(): Observable<Music>

    @GET("musicRankingsDetails")
    fun getMusicDetails(@Query("type") type: Int): Observable<MusicDetail>
}