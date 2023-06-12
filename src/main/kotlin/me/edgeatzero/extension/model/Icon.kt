package me.edgeatzero.extension.model

import kotlinx.serialization.Serializable

@Serializable
public sealed class Icon {

    @JvmInline
    @Serializable
    public value class Base64(public val value: String)

    @JvmInline
    @Serializable
    public value class JarFile(public val value: String)

}
