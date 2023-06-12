package me.edgeatzero.extension.model

import me.edgeatzero.extension.model.client.HttpRequisitionBuilder
import me.edgeatzero.extension.model.client.buildHttpRequisition
import org.intellij.lang.annotations.Language
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@JvmInline
public value class ContentsBuilder(internal val contents: MutableList<Content> = arrayListOf()) :
    List<Content> by contents {

    public fun insertText(text: String) {
        contents.add(Text(text))
    }

    public fun insertHtml(@Language("HTML") html: String) {
        contents.add(HTML(html))
    }

    public fun insertImage(
        urlString: String? = null,
        builderAction: HttpRequisitionBuilder.() -> Unit = {}
    ) {
        contents.add(Image(buildHttpRequisition(urlString, builderAction)))
    }

    public fun insertMovie(
        urlString: String? = null,
        isEmbedded: Boolean = false,
        builderAction: HttpRequisitionBuilder.() -> Unit = {}
    ) {
        contents.add(Movie(buildHttpRequisition(urlString, builderAction), isEmbedded))
    }

    public fun insertMusic(
        urlString: String? = null,
        isEmbedded: Boolean = false,
        builderAction: HttpRequisitionBuilder.() -> Unit
    ) {
        contents.add(Music(buildHttpRequisition(urlString, builderAction), isEmbedded))
    }

}

public fun buildContents(builderAction: ContentsBuilder.() -> Unit): Contents {
    contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }
    return with(ContentsBuilder()) {
        builderAction()
        Contents(contents)
    }
}
