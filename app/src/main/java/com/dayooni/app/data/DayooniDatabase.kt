package com.dayooni.app.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PersonEntity::class, DebtEntity::class, PaymentEntity::class, NoteEntity::class], version = 1, exportSchema = false)
abstract class DayooniDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun debtDao(): DebtDao
    abstract fun paymentDao(): PaymentDao
    abstract fun noteDao(): NoteDao
}
