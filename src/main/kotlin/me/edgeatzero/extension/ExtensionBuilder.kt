package me.edgeatzero.extension

import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DIAware

public abstract class ExtensionBuilder: Extension {

    public abstract fun Context.build()

    @ExtensionDsl
    public interface Context : Closeable, CoroutineScope, DIAware {

        public val language: String

        public val preferences: MutableMap<String, String>

        public fun <TBuilder : Any, TData : Extension.Component.Data> bind(
            component: Extension.Component<TBuilder, TData>,
            block: TBuilder.() -> Unit
        )

    }

}
