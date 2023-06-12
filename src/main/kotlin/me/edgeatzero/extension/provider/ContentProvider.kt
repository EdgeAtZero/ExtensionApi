package me.edgeatzero.extension.provider

import io.ktor.util.AttributeKey
import io.ktor.util.Attributes
import me.edgeatzero.extension.ExtensionDsl
import me.edgeatzero.extension.Extension
import me.edgeatzero.extension.model.Icon

public class ContentProvider private constructor(
    public override val id: String,
    public val name: String?,
    public val icon: Icon?,
    public val host: String?,
    private val components: Attributes
) : Extension.Component.Data() {

    public operator fun <TBuilder : Any, TData : Any> get(component: Component<TBuilder, TData>): TData {
        return components[component.key]
    }

    @ExtensionDsl
    public class Builder internal constructor(internal val components: Attributes = Attributes()) {

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
         * Content Provider's host.
         */
        public var host: String? = null

        public fun <TBuilder : Any, TData : Any> bind(
            component: Component<TBuilder, TData>,
            block: TBuilder.() -> Unit
        ) {
            components.put(component.key, component.build(block))
        }

    }

    public interface Component<TBuilder : Any, TData : Any> {

        public val key: AttributeKey<TData>

        public fun build(buildAction: TBuilder.() -> Unit): TData

    }

    public companion object : Extension.Component<Builder, ContentProvider> {

        public override fun build(buildAction: Builder.() -> Unit): ContentProvider = with(Builder()) {
            buildAction()
            return@with ContentProvider(
                id = checkNotNull(id) { "'id' must be not null!" },
                name = name,
                icon = icon,
                host = host,
                components = components
            )
        }

        override fun isData(data: Extension.Component.Data): Boolean = data is ContentProvider

    }

}
