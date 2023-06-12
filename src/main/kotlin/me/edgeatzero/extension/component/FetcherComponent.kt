package me.edgeatzero.extension.component

import io.ktor.util.AttributeKey
import me.edgeatzero.extension.ExtensionDsl
import me.edgeatzero.extension.model.*
import me.edgeatzero.extension.provider.ContentProvider

public typealias MediaFetcher = suspend (media: Media) -> Media

public typealias ChaptersFetcher = suspend (media: Media) -> Chapters

public typealias ContentTokensFetcher = suspend (chapter: Chapter) -> ContentTokens

public typealias ContentsFetcher = suspend (contentsToken: ContentsToken) -> Contents

public class FetcherComponent private constructor(
    private val mediaFetcher: MediaFetcher,
    private val chaptersFetcher: ChaptersFetcher,
    private val contentTokensFetcher: ContentTokensFetcher,
    private val contentsFetcher: ContentsFetcher
) {

    public suspend fun refresh(media: Media): Media = mediaFetcher(media)

    public suspend fun chapters(media: Media): Chapters = chaptersFetcher(media)

    public suspend fun contentTokens(chapter: Chapter): ContentTokens = contentTokensFetcher(chapter)

    public suspend fun content(contentsToken: ContentsToken): Contents = contentsFetcher(contentsToken)

    @ExtensionDsl
    public class Builder internal constructor() {
        internal var mediaFetcher: MediaFetcher? = null
        internal var chaptersFetcher: ChaptersFetcher? = null
        internal var contentTokensFetcher: ContentTokensFetcher? = null
        internal var contentsFetcher: ContentsFetcher? = null

        public fun onFetchMedia(fetcher: MediaFetcher) {
            mediaFetcher = fetcher
        }

        public fun onFetchChapters(fetcher: ChaptersFetcher) {
            chaptersFetcher = fetcher
        }

        public fun onFetchContentTokens(fetcher: ContentTokensFetcher) {
            contentTokensFetcher = fetcher
        }

        public fun onFetchContent(fetcher: ContentsFetcher) {
            contentsFetcher = fetcher
        }

    }

    public companion object : ContentProvider.Component<Builder, FetcherComponent> {

        public override val key: AttributeKey<FetcherComponent> = AttributeKey("fetcher")

        public override fun build(buildAction: Builder.() -> Unit) = with(Builder()) {
            buildAction()
            return@with FetcherComponent(
                mediaFetcher = checkNotNull(mediaFetcher) { "please setup MediaFetcher" },
                chaptersFetcher = checkNotNull(chaptersFetcher) { "please setup ChaptersFetcher" },
                contentTokensFetcher = checkNotNull(contentTokensFetcher) { "please setup ContentTokensFetcher" },
                contentsFetcher = checkNotNull(contentsFetcher) { "please setup ContentFetcher" }
            )
        }

    }

}
