package com.edwinespejo.flightnew.app.adapter.ktx

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

object StringExtension {
    fun ByteArray.convertToString(): String {
        return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(this)).toString()
    }
}