package com.rachellima.subscriber.repository

import androidx.lifecycle.LiveData
import com.rachellima.subscriber.data.db.dao.SubscriberDao
import com.rachellima.subscriber.data.db.entity.SubscribeEntity

class DatabaseDataSource(
    private val subscriberDao: SubscriberDao
) : SubscribeRepository {
    override suspend fun insertSubscriber(name: String, email: String): Long {
        val subscriber = SubscribeEntity(name = name, email = email)
        return subscriberDao.insert(subscriber)
    }

    override suspend fun updateSubscriber(id: Long, name: String, email: String) {
        val subscriber = SubscribeEntity(id = id, name = name, email = email)
        subscriberDao.update(subscriber)
    }

    override suspend fun deleteSubscriber(id: Long) {
        subscriberDao.delete(id)
    }

    override suspend fun deleteAllSubscribers() {
        subscriberDao.deleteAll()
    }

    override suspend fun getAllSubscribers(): LiveData<List<SubscribeEntity>> {
        return subscriberDao.getAll()
    }
}