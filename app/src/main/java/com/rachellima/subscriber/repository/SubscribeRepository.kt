package com.rachellima.subscriber.repository

import androidx.lifecycle.LiveData
import com.rachellima.subscriber.data.db.entity.SubscribeEntity

interface SubscribeRepository {
    suspend fun insertSubscriber(name: String, email: String): Long

    suspend fun updateSubscriber(id: Long, name: String, email: String)

    suspend fun deleteSubscriber(id: Long)

    suspend fun deleteAllSubscribers()

    suspend fun getAllSubscribers(): LiveData<List<SubscribeEntity>>
}