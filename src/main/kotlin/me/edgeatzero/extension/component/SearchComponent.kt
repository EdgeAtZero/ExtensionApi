package me.edgeatzero.extension.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import io.ktor.util.AttributeKey
import me.edgeatzero.extension.ExtensionDsl
import me.edgeatzero.extension.model.Media
import me.edgeatzero.extension.model.SortType
import me.edgeatzero.extension.model.Tag
import me.edgeatzero.extension.provider.ContentProvider

public typealias FilterUI = @Composable (data: SnapshotStateMap<String, String>) -> Unit

public typealias FilterBuilder = suspend (index: Int?, keywords: String?, tags: List<Tag>?, sort: SortType?, author: String?, uploader: String?) -> Map<String, String>

public typealias SearchFunction = suspend (data: Map<String, String>) -> Pair<List<Media>, Map<String, String>?>

public class SearchComponent private constructor(
    public val isIndexSupported: Boolean,
    public val isKeywordsSupported: Boolean,
    public val isTagsSupported: Boolean,
    public val isSortSupported: Boolean,
    public val isAuthorSupported: Boolean,
    public val isUploaderSupported: Boolean,
    public val supportedSortType: List<SortType>,
    private val ui: FilterUI,
    private val builder: FilterBuilder,
    private val function: SearchFunction,
) {

    @Composable
    public fun FilterUI(data: SnapshotStateMap<String, String>): Unit = ui(data)

    public suspend fun buildFilter(
        index: Int?,
        keywords: String?,
        tags: List<Tag>?,
        sort: SortType?,
        author: String?,
        uploader: String?
    ): Map<String, String> {
        check(isIndexSupported || index == null) { "searching by index is not supported!" }
        check(isKeywordsSupported || keywords == null) { "searching by keywords is not supported!" }
        check(isTagsSupported || tags == null) { "searching by tags is not supported!" }
        check(isSortSupported || sort == null) { "searching by sort is not supported!" }
        check(!isSortSupported && supportedSortType.contains(sort)) { "unsupported sort type: $sort" }
        check(isAuthorSupported || author == null) { "searching by author is not supported!" }
        check(isUploaderSupported || uploader == null) { "searching by uploader is not supported!" }
        return builder(index, keywords, tags, sort, author, uploader)
    }

    public suspend fun search(data: Map<String, String>): Pair<List<Media>, Map<String, String>?> = function(data)

    @ExtensionDsl
    public class Builder internal constructor() {

        public var isIndexSupported: Boolean = true
        public var isKeywordsSupported: Boolean = true
        public var isTagsSupported: Boolean = true
        public var isSortSupported: Boolean = true
        public var isAuthorSupported: Boolean = true
        public var isUploaderSupported: Boolean = true

        public var supportedSortType: List<SortType>? = null

        internal var ui: FilterUI? = null
        internal var builder: FilterBuilder? = null
        internal var function: SearchFunction? = null

        public fun onDrawFilter(block: FilterUI) {
            ui = block
        }

        public fun onBuildFilter(block: FilterBuilder) {
            builder = block
        }

        public fun onSearch(block: SearchFunction) {
            function = block
        }

    }

    public companion object : ContentProvider.Component<Builder, SearchComponent> {

        public override val key: AttributeKey<SearchComponent> = AttributeKey("search")

        override fun build(buildAction: Builder.() -> Unit): SearchComponent = with(Builder()) {
            buildAction()
            return@with SearchComponent(
                isIndexSupported = isIndexSupported,
                isKeywordsSupported = isKeywordsSupported,
                isTagsSupported = isTagsSupported,
                isSortSupported = isSortSupported,
                isAuthorSupported = isAuthorSupported,
                isUploaderSupported = isUploaderSupported,
                supportedSortType = checkNotNull(supportedSortType),
                ui = checkNotNull(ui),
                builder = checkNotNull(builder),
                function = checkNotNull(function)
            )
        }

        public const val KEY_INDEX = "index"
        public const val KEY_KEYWORDS = "keywords"
        public const val KEY_TAGS = "tags"
        public const val KEY_SORT = "sort"
        public const val KEY_AUTHOR = "author"
        public const val KEY_UPLOADER = "uploader"

    }

}
