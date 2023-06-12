package me.edgeatzero.extension.model

import kotlinx.datetime.Instant

public interface Media {

    /**
     * The media's id for identifying.
     */
    public val id: String

    /**
     * The media's type.
     */
    public val type: MediaType

    /**
     * The media's token for accessing media.
     */
    public val token: String

    /**
     * The media's extra data.
     */
    public val extra: String

    /**
     * The media's title text.
     */
    public val title: String

    /**
     * The media's subtitle text.
     */
    public val subtitle: String?

    /**
     * The media's serialization status
     */
    public val status: MediaStatus

    /**
     * The ISO 639 compliant language code.
     */
    public val language: String

    /**
     * The media's token for accessing cover image.
     */
    public val cover: Image

    /**
     * The media's tags.
     */
    public val tags: Tags

    /**
     * The media's creator.
     */
    public val authors: List<String>

    /**
     * The media's uploader (if existed, it should be nonnull).
     */
    public val uploader: String?

    /**
     * The media's description.
     */
    public val description: String?

    /**
     * The media's time of update.
     */
    public val updatedInstant: Instant

    /**
     * The media's time of uploaded.
     */
    public val uploadedInstant: Instant

}
