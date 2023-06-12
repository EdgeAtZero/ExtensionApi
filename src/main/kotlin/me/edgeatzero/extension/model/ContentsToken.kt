package me.edgeatzero.extension.model

import kotlinx.serialization.Serializable

public typealias ContentTokens = List<ContentsToken>

@Serializable
public abstract class ContentsToken {

    /**
     * The content's id for identifying.
     */
    public abstract val id: String

    /**
     * The content's token for accessing content.
     */
    public abstract val token: String

}
