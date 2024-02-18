package com.example.youtube.data.model

data class PlaylistItemsModel(
    val etag: String?,
    val items: List<Item?>?,
    val kind: String?,
    val nextPageToken: String?,
) {
    data class Item(
        val contentDetails: ContentDetails?,
        val id: String?,
        val snippet: Snippet?
    ) {
        data class ContentDetails(
            val videoId: String?, val videoPublishedAt: String?
        )

        data class Snippet(
            val description: String?,
            val thumbnails: Thumbnails?,
            val title: String?
        ) {
            data class Thumbnails(
                val default: Default?
            ) {
                data class Default(
                    val height: Int?, val url: String?, val width: Int?
                )
            }
        }
    }
}