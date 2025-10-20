package com.example.infocapitos.navigation

object Routes {
    const val HOME = "home"
    const val PROFILE = "profile"
    const val ADD = "add"

    const val DETAIL = "detail/{noticiaId}"

    fun detailRoute(noticiaId: Int) = "detail/$noticiaId"
}