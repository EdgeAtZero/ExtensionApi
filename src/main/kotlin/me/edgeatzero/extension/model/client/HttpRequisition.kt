package me.edgeatzero.extension.model.client

import io.ktor.http.Headers
import io.ktor.http.HttpMethod
import io.ktor.http.Url
import kotlinx.serialization.Serializable
import me.edgeatzero.extension.util.ktor.HeadersSerializer
import me.edgeatzero.extension.util.ktor.HttpMethodSerializer
import me.edgeatzero.extension.util.ktor.UrlSerializer

@Serializable
public data class HttpRequisition(
    @Serializable(with = UrlSerializer::class)
    public val url: Url,
    @Serializable(with = HttpMethodSerializer::class)
    public val method: HttpMethod,
    @Serializable(with = HeadersSerializer::class)
    public val headers: Headers
)
