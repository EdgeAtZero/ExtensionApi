package me.edgeatzero.extension.util.ktor

import io.ktor.http.HttpMethod
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public object HttpMethodSerializer : KSerializer<HttpMethod> {

    public override val descriptor: SerialDescriptor =
        buildSerialDescriptor("io.ktor.http.HttpMethod", PrimitiveKind.STRING)

    public override fun deserialize(decoder: Decoder): HttpMethod = HttpMethod(decoder.decodeString())

    public override fun serialize(encoder: Encoder, value: HttpMethod): Unit = encoder.encodeString(value.value)

}
