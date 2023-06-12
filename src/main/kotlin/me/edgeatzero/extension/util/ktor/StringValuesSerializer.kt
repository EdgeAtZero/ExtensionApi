package me.edgeatzero.extension.util.ktor

import io.ktor.http.Headers
import io.ktor.http.HeadersImpl
import io.ktor.http.Parameters
import io.ktor.http.ParametersImpl
import io.ktor.util.StringValues
import io.ktor.util.toMap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public sealed class StringValuesSerializer<T : StringValues> : KSerializer<T> {

    protected val serializer: KSerializer<Map<String, List<String>>> =
        MapSerializer(String.serializer(), ListSerializer(String.serializer()))

    public override fun serialize(encoder: Encoder, value: T): Unit = serializer.serialize(encoder, value.toMap())

}

public object HeadersSerializer : StringValuesSerializer<Headers>() {

    public override val descriptor: SerialDescriptor = SerialDescriptor("io.ktor.http.Headers", serializer.descriptor)

    public override fun deserialize(decoder: Decoder): Headers = HeadersImpl(serializer.deserialize(decoder))

}

public object ParametersSerializer : StringValuesSerializer<Parameters>() {

    public override val descriptor: SerialDescriptor =
        SerialDescriptor("io.ktor.http.Parameters", serializer.descriptor)

    public override fun deserialize(decoder: Decoder): Parameters = ParametersImpl(serializer.deserialize(decoder))

}
