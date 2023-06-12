package me.edgeatzero.extension.model.client

import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpMethod
import io.ktor.http.URLBuilder
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class HttpRequisitionBuilder(
    public val url: URLBuilder = URLBuilder(),
    public var method: HttpMethod = HttpMethod.Get,
    public val headers: HeadersBuilder = HeadersBuilder()
) {

    public fun url(builderAction: URLBuilder.() -> Unit): Unit = url.let(builderAction)

    public fun headers(builderAction: HeadersBuilder.() -> Unit): Unit = headers.let(builderAction)

}

public fun buildHttpRequisition(
    urlString: String? = null,
    builderAction: HttpRequisitionBuilder.() -> Unit
): HttpRequisition {
    contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }
    return with(HttpRequisitionBuilder(url = urlString?.let { URLBuilder(it) } ?: URLBuilder())) {
        builderAction()
        HttpRequisition(
            url = url.build(),
            method = method,
            headers = headers.build()
        )
    }
}
