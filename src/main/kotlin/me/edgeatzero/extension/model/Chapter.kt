package me.edgeatzero.extension.model

import kotlinx.datetime.Instant

public typealias Chapters = List<Chapter>

public interface Chapter {

    /**
     * The chapter's id for identifying.
     */
    public val id: String

    /**
     * The chapter's token for accessing content.
     */
    public val token: String

    /**
     * The chapter's title text.
     */
    public val title: String

    /**
     * The chapter's subtitle text.
     */
    public val subtitle: String?

    /**
     * The chapter's token for accessing cover image.
     */
    public val cover: Image?

    /**
     * The chapter's description.
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
