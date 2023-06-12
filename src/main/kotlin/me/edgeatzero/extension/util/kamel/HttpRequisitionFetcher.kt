package me.edgeatzero.extension.util.kamel

import io.kamel.core.DataSource
import io.kamel.core.Resource
import io.kamel.core.config.KamelConfigBuilder
import io.kamel.core.config.ResourceConfig
import io.kamel.core.fetcher.Fetcher
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.request
import io.ktor.client.request.takeFrom
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import me.edgeatzero.extension.model.client.HttpRequisition

/**
 * Fetcher that fetches [ByteReadChannel] from network using [HttpRequisition].
 */
internal class HttpRequisitionFetcher(private val client: HttpClient) : Fetcher<HttpRequisition> {

    override val source: DataSource = DataSource.Network

    override val HttpRequisition.isSupported: Boolean
        get() = url.protocol.name == "https" || url.protocol.name == "http"

    override fun fetch(
        data: HttpRequisition,
        resourceConfig: ResourceConfig
    ): Flow<Resource<ByteReadChannel>> = channelFlow {
        val response = client.request {
            onDownload { bytesSentTotal, contentLength ->
                val progress = (bytesSentTotal.toFloat() / contentLength).coerceIn(0F..1F)
                    .takeUnless { it.isNaN() }
                if (progress != null) send(Resource.Loading(progress, source))
            }
            takeFrom(resourceConfig.requestData)
            url(data.url)
            method = data.method
            headers.appendAll(data.headers)
        }
        val bytes = response.bodyAsChannel()
        send(Resource.Success(bytes, source))
    }

}

/**
 * Adds an Http fetcher to the [KamelConfigBuilder] using the specified [client].
 */
public fun KamelConfigBuilder.httpRequisitionFetcher(client: HttpClient): Unit = fetcher(HttpRequisitionFetcher(client))
