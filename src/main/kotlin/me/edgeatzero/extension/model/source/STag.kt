package me.edgeatzero.extension.model.source

import kotlinx.serialization.Serializable
import me.edgeatzero.extension.model.Tag

/**
 * simple implement for [Tag]
 */
@Serializable
public data class STag(
    public override val title: String
) : Tag()
