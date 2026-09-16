package com.dayooni.app.di

import android.content.Context
import androidx.room.Room
import com.dayooni.app.data.DayooniDatabase
import com.dayooni.app.data.DebtDao
import com.dayooni.app.data.NoteDao
import com.dayooni.app.data.PaymentDao
import com.dayooni.app.data.PersonDao
import com.dayooni.app.repository.DayooniRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DayooniDatabase =
        Room.databaseBuilder(context, DayooniDatabase::class.java, "dayooni.db").build()

    @Provides fun providePersonDao(db: DayooniDatabase): PersonDao = db.personDao()
    @Provides fun provideDebtDao(db: DayooniDatabase): DebtDao = db.debtDao()
    @Provides fun providePaymentDao(db: DayooniDatabase): PaymentDao = db.paymentDao()
    @Provides fun provideNoteDao(db: DayooniDatabase): NoteDao = db.noteDao()

    @Provides @Singleton
    fun provideRepository(personDao: PersonDao, debtDao: DebtDao, paymentDao: PaymentDao, noteDao: NoteDao): DayooniRepository =
        DayooniRepository(personDao, debtDao, paymentDao, noteDao)
}
