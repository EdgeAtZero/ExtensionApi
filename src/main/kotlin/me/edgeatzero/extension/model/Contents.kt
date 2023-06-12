package me.edgeatzero.extension.model

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
public value class Contents(private val contents: List<Content>) : List<Content> by contents
