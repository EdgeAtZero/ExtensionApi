package me.edgeatzero.extension.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import me.edgeatzero.extension.Extension
import me.edgeatzero.extension.ExtensionDsl

public typealias PreferencesUI = @Composable (preferences: SnapshotStateMap<String, String>) -> Unit

public class PreferencesUIProvider private constructor(
    private val ui: PreferencesUI,
) : Extension.Component.Data() {

    override val id = "preferences_ui"

    @Composable
    fun PreferencesUI(preferences: SnapshotStateMap<String, String>) = ui(preferences)

    @ExtensionDsl
    public class Builder internal constructor() {

        internal var ui: PreferencesUI? = null

        public fun onDrawPreferences(block: PreferencesUI) {
            ui = block
        }

    }

    public companion object : Extension.Component<Builder, PreferencesUIProvider> {

        public override fun build(buildAction: Builder.() -> Unit): PreferencesUIProvider = with(Builder()) {
            buildAction()
            return@with PreferencesUIProvider(
                ui = checkNotNull(ui) { "'ui' must be not null!" },
            )
        }

        public override fun isData(data: Extension.Component.Data): Boolean = data is PreferencesUIProvider

    }

}
