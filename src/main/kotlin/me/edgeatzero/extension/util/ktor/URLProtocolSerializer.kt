package me.edgeatzero.extension.util.ktor

import io.ktor.http.DEFAULT_PORT
import io.ktor.http.URLProtocol
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*

public object URLProtocolSerializer : KSerializer<URLProtocol> {

    public override val descriptor: SerialDescriptor = buildClassSerialDescriptor("io.ktor.http.URLProtocol") {
        element<String>("name")
        element<Int>("defaultPort")
    }

    public override fun deserialize(decoder: Decoder): URLProtocol = decoder.decodeStructure(descriptor) {
        var name = ""
        var defaultPort = DEFAULT_PORT
        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> name = decodeStringElement(descriptor, 0)
                1 -> defaultPort = decodeIntElement(descriptor, 0)
                CompositeDecoder.DECODE_DONE -> break
                else -> error("Unexpected index: $index")
            }
        }
        URLProtocol(name = name, defaultPort = defaultPort)
    }

    public override fun serialize(encoder: Encoder, value: URLProtocol): Unit = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, 0, value.name)
        encodeIntElement(descriptor, 1, value.defaultPort)
    }

}
