package me.edgeatzero.extension.model

import kotlinx.serialization.Serializable

public typealias Tags = List<Tag>

@Serializable
public abstract class Tag {

    public abstract val title: String

}
