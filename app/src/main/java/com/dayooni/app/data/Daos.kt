package com.dayooni.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

@Query("SELECT * FROM people ORDER BY name COLLATE NOCASE")
fun observeAll(): Flow<List<PersonEntity>>

@Query("SELECT * FROM people")
suspend fun getAll(): List<PersonEntity>

@Query("DELETE FROM people")
suspend fun clearAll()

@Query("SELECT * FROM people WHERE id = :id LIMIT 1")
fun observeById(id: Long): Flow<PersonEntity?>

@Query("SELECT * FROM people WHERE id = :id LIMIT 1")
suspend fun getById(id: Long): PersonEntity?

@Insert
suspend fun insert(person: PersonEntity): Long

@Update
suspend fun update(person: PersonEntity)

@Delete
suspend fun delete(person: PersonEntity)

}

@Dao
interface DebtDao {

@Query("SELECT * FROM debts ORDER BY createdAt DESC")
fun observeAll(): Flow<List<DebtEntity>>

@Query("SELECT * FROM debts")
suspend fun getAll(): List<DebtEntity>

@Query("DELETE FROM debts")
suspend fun clearAll()

@Query("SELECT * FROM debts WHERE personId = :personId ORDER BY createdAt DESC")
fun observeForPerson(personId: Long): Flow<List<DebtEntity>>

@Query("SELECT * FROM debts WHERE id = :id LIMIT 1")
fun observeById(id: Long): Flow<DebtEntity?>

@Query("SELECT * FROM debts WHERE id = :id LIMIT 1")
suspend fun getById(id: Long): DebtEntity?

@Insert
suspend fun insert(debt: DebtEntity): Long

@Update
suspend fun update(debt: DebtEntity)

@Delete
suspend fun delete(debt: DebtEntity)

@Query("UPDATE debts SET paidAmount = paidAmount + :amount WHERE id = :debtId")
suspend fun addPaymentToDebt(debtId: Long, amount: Double)

@Query("SELECT COALESCE(SUM(amount - paidAmount), 0.0) FROM debts WHERE isCreditor = 0")
fun observeTotalOwed(): Flow<Double>

@Query("SELECT COALESCE(SUM(amount - paidAmount), 0.0) FROM debts WHERE isCreditor = 1")
fun observeTotalToReceive(): Flow<Double>

}

@Dao
interface PaymentDao {

@Query("SELECT * FROM payments ORDER BY date DESC")
suspend fun getAll(): List<PaymentEntity>

@Query("DELETE FROM payments")
suspend fun clearAll()

@Query("SELECT * FROM payments WHERE debtId = :debtId ORDER BY date DESC")
fun observeForDebt(debtId: Long): Flow<List<PaymentEntity>>

@Insert
suspend fun insert(payment: PaymentEntity): Long

}

@Dao
interface NoteDao {

@Query("SELECT * FROM notes ORDER BY createdAt DESC")
fun observeAll(): Flow<List<NoteEntity>>

@Query("SELECT * FROM notes")
suspend fun getAll(): List<NoteEntity>

@Query("DELETE FROM notes")
suspend fun clearAll()

@Query("SELECT * FROM notes WHERE personId = :personId ORDER BY createdAt DESC")
fun observeForPerson(personId: Long): Flow<List<NoteEntity>>

@Insert
suspend fun insert(note: NoteEntity): Long

@Update
suspend fun update(note: NoteEntity)

@Delete
suspend fun delete(note: NoteEntity)

}