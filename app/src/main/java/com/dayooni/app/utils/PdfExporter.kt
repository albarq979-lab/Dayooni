package com.dayooni.app.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import com.dayooni.app.repository.DayooniRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PdfExporter @Inject constructor(@ApplicationContext private val context: Context, private val repository: DayooniRepository) {
    suspend fun export(uri: Uri) {
        val people = repository.people().first()
        val debts = repository.debts().first()
        val doc = PdfDocument()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { textSize = 14f }
        var pageNumber = 1
        var page = doc.startPage(PdfDocument.PageInfo.Builder(595, 842, pageNumber).create())
        var y = 48f
        paint.textSize = 22f
        page.canvas.drawText("Dayooni - Debt Report", 40f, y, paint)
        y += 34f
        paint.textSize = 12f
        val formatter = NumberFormat.getNumberInstance(Locale.getDefault())
        people.forEach { person ->
            if (y > 790f) { doc.finishPage(page); pageNumber++; page = doc.startPage(PdfDocument.PageInfo.Builder(595, 842, pageNumber).create()); y = 48f }
            page.canvas.drawText(person.name, 40f, y, paint); y += 20f
            debts.filter { it.personId == person.id }.forEach { debt ->
                if (y > 790f) { doc.finishPage(page); pageNumber++; page = doc.startPage(PdfDocument.PageInfo.Builder(595, 842, pageNumber).create()); y = 48f }
                val remaining = debt.amount - debt.paidAmount
                page.canvas.drawText("  ${debt.title}: ${formatter.format(remaining)}", 55f, y, paint); y += 18f
            }
            y += 8f
        }
        doc.finishPage(page)
        context.contentResolver.openOutputStream(uri)?.use { doc.writeTo(it) } ?: error("Unable to write PDF")
        doc.close()
    }
}
