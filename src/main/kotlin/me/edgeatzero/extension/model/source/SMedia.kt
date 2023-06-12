package me.edgeatzero.extension.model.source

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.edgeatzero.extension.model.*

/**
 * simple implement for [Media]
 */
@Serializable
public data class SMedia(
    public override val id: String,
    public override val type: MediaType,
    public override val token: String,
    public override val extra: String,
    public override val title: String,
    public override val subtitle: String? = null,
    public override val status: MediaStatus,
    public override val language: String,
    public override val cover: Image,
    public override val tags: List<Tag>,
    public override val authors: List<String>,
    public override val uploader: String? = null,
    public override val description: String? = null,
    public override val updatedInstant: Instant = Clock.System.now(),
    public override val uploadedInstant: Instant,
) : Media
