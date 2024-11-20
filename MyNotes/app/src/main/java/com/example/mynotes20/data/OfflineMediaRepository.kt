package com.example.mynotes20.data

import kotlinx.coroutines.flow.Flow

class OfflineMediaRepository (private val MediaDao: MediaDao) : MediaRepository{
    override fun getAllMediaStream(): Flow<List<Media>> = MediaDao.getAllMedia()

    override fun getMediaStream(id: Int): Flow<Media?> = MediaDao.getMedia(id)

    override suspend fun insertMedia(media: Media) = MediaDao.insert(media)

    override suspend fun deleteMedia(media: Media) = MediaDao.delete(media)

    override suspend fun updateMedia(media: Media) = MediaDao.update(media)
}