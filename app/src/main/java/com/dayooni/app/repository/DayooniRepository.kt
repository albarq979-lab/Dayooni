package com.dayooni.app.repository

import com.dayooni.app.data.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DayooniRepository @Inject constructor(
    private val people: PersonDao,
    private val debts: DebtDao,
    private val payments: PaymentDao,
    private val notes: NoteDao
) {
    fun people(): Flow<List<PersonEntity>> = people.observeAll()
    fun person(id: Long): Flow<PersonEntity?> = people.observeById(id)
    suspend fun personOnce(id: Long) = people.getById(id)
    suspend fun savePerson(p: PersonEntity) = if (p.id == 0L) people.insert(p) else { people.update(p); p.id }
    suspend fun deletePerson(p: PersonEntity) = people.delete(p)

    fun debts(): Flow<List<DebtEntity>> = debts.observeAll()
    fun debtsForPerson(id: Long): Flow<List<DebtEntity>> = debts.observeForPerson(id)
    fun debt(id: Long): Flow<DebtEntity?> = debts.observeById(id)
    suspend fun debtOnce(id: Long) = debts.getById(id)
    suspend fun saveDebt(d: DebtEntity) = if (d.id == 0L) debts.insert(d) else { debts.update(d); d.id }
    suspend fun deleteDebt(d: DebtEntity) = debts.delete(d)
    suspend fun addPayment(d: DebtEntity, amount: Double, note: String) {
        payments.insert(PaymentEntity(debtId = d.id, amount = amount, note = note))
        debts.addPaymentToDebt(d.id, amount)
    }
    fun paymentsForDebt(id: Long) = payments.observeForDebt(id)
    fun totalOwed() = debts.observeTotalOwed()
    fun totalToReceive() = debts.observeTotalToReceive()
    fun notes() = notes.observeAll()
    fun notesForPerson(id: Long) = notes.observeForPerson(id)
    suspend fun saveNote(n: NoteEntity) = if (n.id == 0L) notes.insert(n) else { notes.update(n); n.id }
    suspend fun deleteNote(n: NoteEntity) = notes.delete(n)
}
