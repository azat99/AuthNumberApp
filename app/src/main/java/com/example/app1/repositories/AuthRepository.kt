package com.example.app1.repositories

import com.example.app1.model.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository{

    private var apiService: ApiService = ApiService.invoke()

    suspend fun check(number:String):Int{
        return withContext(Dispatchers.IO){
            val res = apiService.checkNumber(number)
            res.code()
        }
    }

    suspend fun code(number: String): Int{
        return withContext(Dispatchers.IO){
            val res = apiService.getCode(number)
            res.code()
        }
    }

}