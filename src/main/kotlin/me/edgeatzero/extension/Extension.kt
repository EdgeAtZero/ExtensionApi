package me.edgeatzero.extension

import me.edgeatzero.extension.model.Icon

public interface Extension {

    public abstract val id: String

    public abstract val name: String

    public abstract val icon: Icon?

    public abstract val version: String

    public abstract val versionCode: Int

    public abstract val homeUrl: String?

    public abstract val reportUrl: String?

    public abstract val description: String?

    public interface Component<TBuilder : Any, TData : Component.Data> {

        public fun build(buildAction: TBuilder.() -> Unit): TData

        public fun isData(data: Data): Boolean

        public abstract class Data {

            public abstract val id: String

            public override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is Data) return false

                return id == other.id
            }

            public override fun hashCode(): Int {
                return id.hashCode()
            }

        }

    }

    public companion object {

        public val REGEX_ID = "^[A-Za-z][A-Za-z0-9_-]*$".toRegex()

    }

}
