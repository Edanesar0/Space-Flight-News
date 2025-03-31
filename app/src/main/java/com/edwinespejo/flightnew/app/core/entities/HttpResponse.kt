package com.edwinespejo.flightnew.app.core.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HttpResponse {
    @Expose
    @SerializedName("code")
    internal var code = 0

    @Expose
    @SerializedName("message")
    internal var message: String? = null

    @Expose
    @SerializedName("data")
    internal var data: ByteArray? = null
}