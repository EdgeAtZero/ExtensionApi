package me.edgeatzero.extension.provider

import me.edgeatzero.extension.Extension
import me.edgeatzero.extension.ExtensionDsl
import me.edgeatzero.extension.model.Icon

public typealias TranslationFunction = suspend (words: String, from: String, to: String) -> String

public class TranslatorProvider private constructor(
    public override val id: String,
    public val name: String?,
    public val icon: Icon?,
    public val languageFrom: Array<String>,
    public val languageTo: Array<String>,
    private val translation: TranslationFunction
) : Extension.Component.Data() {

    public suspend fun translate(words: String, from: String, to: String): String {
        check(languageFrom.contains(from)) { "unknown language code $from" }
        check(languageTo.contains(to)) { "unknown language code $to" }
        return translation(words, from, to)
    }

    @ExtensionDsl
    public class Builder internal constructor() {

        /**
         * Content Provider's id.
         * Must be not null!
         */
        public var id: String? = null

        /**
         * Content Provider's name.
         */
        public var name: String? = null

        /**
         * Content Provider's icon(coded by base64).
         */
        public var icon: Icon? = null

        /**
         * The language codes from.
         */
        public var languageFrom: Array<String>? = null

        /**
         * The language codes to.
         */
        public var languageTo: Array<String>? = null

        internal var translation: TranslationFunction? = null

        public fun onTranslation(block: TranslationFunction) {
            translation = block
        }

    }

    public companion object : Extension.Component<Builder, TranslatorProvider> {

        public override fun build(buildAction: Builder.() -> Unit): TranslatorProvider = with(Builder()) {
            buildAction()
            return@with TranslatorProvider(id = checkNotNull(id) { "'id' must be not null!" },
                name = name,
                icon = icon,
                languageFrom = checkNotNull(languageFrom) { "'languageFrom' must be not null!" },
                languageTo = checkNotNull(languageTo) { "'languageTo' must be not null!" },
                translation = checkNotNull(translation) { "please setup TranslationFunction (see public fun onTranslation(block: TranslationFunction))!" })
        }

        public override fun isData(data: Extension.Component.Data): Boolean = data is TranslatorProvider

    }

}
