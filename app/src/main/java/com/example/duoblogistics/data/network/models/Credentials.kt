package com.example.duoblogistics.data.network.models

data class Credentials(
    val code: String,
    val password: String
){
    fun validate(): Boolean {
        return this.code.isNotBlank() && this.password.isNotBlank()
    }
}