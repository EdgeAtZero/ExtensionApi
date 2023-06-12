package me.edgeatzero.extension.model

import kotlinx.serialization.Serializable
import me.edgeatzero.extension.model.client.HttpRequisition
import org.intellij.lang.annotations.Language

@Serializable
public sealed interface Content

@JvmInline
@Serializable
public value class Text(public val text: String) : Content

@JvmInline
@Serializable
public value class HTML(@Language("HTML") public val html: String) : Content

@JvmInline
@Serializable
public value class Image(public val value: HttpRequisition) : Content

@Serializable
public data class Movie(public val value: HttpRequisition, public val isEmbedded: Boolean) : Content

@Serializable
public data class Music(public val value: HttpRequisition, public val isEmbedded: Boolean) : Content
