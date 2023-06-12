package me.edgeatzero.extension.util.ktor

import io.ktor.http.Url
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public object UrlStringSerializer : KSerializer<Url> {

    private val serializer: KSerializer<List<String>> = ListSerializer(String.serializer())

    public override val descriptor: SerialDescriptor =
        buildSerialDescriptor("io.ktor.http.URLProtocol", PrimitiveKind.STRING)

    public override fun deserialize(decoder: Decoder): Url = Url(decoder.decodeString())

    public override fun serialize(encoder: Encoder, value: Url): Unit = encoder.encodeString(value.toString())

}
