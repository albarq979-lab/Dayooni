package com.dayooni.app.utils

import android.content.Context
import android.net.Uri
import androidx.room.withTransaction
import com.dayooni.app.data.*
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

data class BackupData(
    val people: List<PersonEntity>,
    val debts: List<DebtEntity>,
    val payments: List<PaymentEntity>,
    val notes: List<NoteEntity>
)

@Singleton
class JsonExporter @Inject constructor(@ApplicationContext private val context: Context, private val db: DayooniDatabase) {
    private val gson = GsonBuilder().setPrettyPrinting().create()

    suspend fun export(uri: Uri) = withContext(Dispatchers.IO) {
        val backup = BackupData(db.personDao().getAll(), db.debtDao().getAll(), db.paymentDao().getAll(), db.noteDao().getAll())
        context.contentResolver.openOutputStream(uri)?.use { out -> out.writer().use { it.write(gson.toJson(backup)) } }
            ?: error("Unable to open export destination")
    }

    suspend fun import(uri: Uri) = withContext(Dispatchers.IO) {
        val json = context.contentResolver.openInputStream(uri)?.use(InputStream::readBytes)?.toString(Charsets.UTF_8)
            ?: error("Unable to read import file")
        val backup = gson.fromJson(json, BackupData::class.java) ?: error("Invalid backup")
        db.withTransaction {
            db.paymentDao().clearAll()
            db.noteDao().clearAll()
            db.debtDao().clearAll()
            db.personDao().clearAll()
            backup.people.forEach { db.personDao().insert(it) }
            backup.debts.forEach { db.debtDao().insert(it) }
            backup.payments.forEach { db.paymentDao().insert(it) }
            backup.notes.forEach { db.noteDao().insert(it) }
        }
    }
}
