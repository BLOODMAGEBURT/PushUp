package com.burt.pushup

import com.burt.pushup.bean.DevBean
import io.reactivex.Observable
import retrofit2.http.GET


interface ApiService {
    @GET("/developerRegister")
    fun dev_register(): Observable<DevBean>


}