package me.edgeatzero.extension.model.source

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.edgeatzero.extension.model.Chapter
import me.edgeatzero.extension.model.Image

/**
 * simple implement for [Chapter]
 */
@Serializable
public data class SChapter(
    public override val id: String,
    public override val token: String,
    public override val title: String,
    public override val subtitle: String? = null,
    public override val cover: Image? = null,
    public override val description: String? = null,
    public override val updatedInstant: Instant = Clock.System.now(),
    public override val uploadedInstant: Instant
) : Chapter
