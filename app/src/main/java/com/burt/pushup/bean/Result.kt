package com.burt.pushup.bean

data class Result(
    val bg_color: String,
    val bg_pic: String,
    val color: String,
    val comment: String,
    val content: List<Content>,
    val count: Int,
    val name: String,
    val pic_s192: String,
    val pic_s210: String,
    val pic_s260: String,
    val pic_s444: String,
    val type: Int,
    val web_url: String
)