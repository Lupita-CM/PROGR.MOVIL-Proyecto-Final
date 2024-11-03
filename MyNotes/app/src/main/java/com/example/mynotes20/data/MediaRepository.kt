package com.example.mynotes20.data

import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllMediaStream(): Flow<List<Media>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getMediaStream(id: Int): Flow<Media?>

    /**
     * Insert item in the data source
     */
    suspend fun insertMedia(media: Media)

    /**
     * Delete item from the data source
     */
    suspend fun deleteMedia(media: Media)

    /**
     * Update item in the data source
     */
    suspend fun updateMedia(media: Media)
}