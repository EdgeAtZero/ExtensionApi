package me.edgeatzero.extension.model.source

import kotlinx.serialization.Serializable
import me.edgeatzero.extension.model.ContentsToken

/**
 * simple implement for [ContentsToken]
 */
@Serializable
public data class SContentsToken(
    public override val id: String,
    public override val token: String
) : ContentsToken()
