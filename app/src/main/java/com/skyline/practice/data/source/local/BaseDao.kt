package com.skyline.practice.data.source.local

import androidx.room.*

@Dao
abstract class BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(obj: List<T>): List<Long>

    @Update
    abstract fun update(obj: T): Int

    @Update
    abstract fun update(obj: List<T>): Int

    @Transaction
    open fun insertOrUpdate(obj: T) {
        insert(obj).takeIf {
            it == -1L
        }.run {
            update(obj)
        }
    }

    @Transaction
    open fun insertOrUpdate(objList: List<T>) {
        val updateList = mutableListOf<T>()

        insert(objList).apply {
            for (i in indices) {
                this[i].takeIf {
                    it == -1L
                }.run {
                    updateList.add(objList[i])
                }
            }
        }

        if (updateList.isNotEmpty()) update(updateList)
    }
}
