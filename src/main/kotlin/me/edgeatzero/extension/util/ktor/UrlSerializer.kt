package me.edgeatzero.extension.util.ktor

import io.ktor.http.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*

public object UrlSerializer : KSerializer<Url> {

    private val serializer: KSerializer<List<String>> = ListSerializer(String.serializer())

    public override val descriptor: SerialDescriptor = buildClassSerialDescriptor("io.ktor.http.URLProtocol") {
        element("protocol", URLProtocolSerializer.descriptor)
        element<String>("host")
        element<Int>("specifiedPort")
        element("pathSegments", serializer.descriptor)
        element("parameters", ParametersSerializer.descriptor)
        element<String>("fragment")
        element<String?>("user")
        element<String?>("password")
        element<Boolean>("trailingQuery")
    }

    public override fun deserialize(decoder: Decoder): Url = decoder.decodeStructure(descriptor) {
        var protocol: URLProtocol = URLProtocol.HTTP
        var host: String = ""
        var specifiedPort: Int = DEFAULT_PORT
        var pathSegments: List<String> = emptyList()
        var parameters: Parameters = Parameters.Empty
        var fragment: String = ""
        var user: String? = null
        var password: String? = null
        var trailingQuery: Boolean = false
        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> protocol = decodeSerializableElement(descriptor, 0, URLProtocolSerializer)
                1 -> host = decodeStringElement(descriptor, 1)
                2 -> specifiedPort = decodeIntElement(descriptor, 2)
                3 -> pathSegments = decodeSerializableElement(descriptor, 3, serializer)
                4 -> parameters = decodeSerializableElement(descriptor, 4, ParametersSerializer)
                5 -> fragment = decodeStringElement(descriptor, 5)
                6 -> user = decodeNullableSerializableElement(descriptor, 6, String.serializer())
                7 -> password = decodeNullableSerializableElement(descriptor, 7, String.serializer())
                8 -> trailingQuery = decodeBooleanElement(descriptor, 8)
                CompositeDecoder.DECODE_DONE -> break
                else -> error("Unexpected index: $index")
            }
        }
        URLBuilder(
            protocol,
            host,
            specifiedPort,
            user,
            password,
            pathSegments,
            parameters,
            fragment,
            trailingQuery
        ).build()
    }

    public override fun serialize(encoder: Encoder, value: Url): Unit = encoder.encodeStructure(descriptor) {
        encodeSerializableElement(descriptor, 0, URLProtocolSerializer, value.protocol)
        encodeStringElement(descriptor, 1, value.host)
        encodeIntElement(descriptor, 2, value.specifiedPort)
        encodeSerializableElement(descriptor, 3, serializer, value.pathSegments)
        encodeSerializableElement(descriptor, 4, ParametersSerializer, value.parameters)
        encodeStringElement(descriptor, 5, value.fragment)
        encodeNullableSerializableElement(descriptor, 6, String.serializer(), value.user)
        encodeNullableSerializableElement(descriptor, 7, String.serializer(), value.password)
        encodeBooleanElement(descriptor, 8, value.trailingQuery)
    }

}
