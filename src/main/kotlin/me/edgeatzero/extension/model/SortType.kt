package me.edgeatzero.extension.model

import kotlinx.serialization.Serializable

@Serializable
public enum class SortType {

    /**
     *  最新
     * */
    Newest,

    /**
     *  最旧
     * */
    Oldest,

    /**
     *  最热门
     * */
    Hottest,

    /**
     *  近期热门
     * */
    HottestRecently;

}
