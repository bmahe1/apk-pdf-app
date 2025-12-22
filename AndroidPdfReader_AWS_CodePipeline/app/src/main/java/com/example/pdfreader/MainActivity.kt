package com.example.pdfreader

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import java.io.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val pdfUrl =
        "https://pdf-reader-bucket.s3.amazonaws.com/sample1.pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.pdfImage)
        val button = findViewById<Button>(R.id.btnLoad)

        button.setOnClickListener {
            downloadAndShowPdf()
        }
    }

    private fun downloadAndShowPdf() {
        Thread {
            try {
                val file = File(cacheDir, "sample.pdf")
                URL(pdfUrl).openStream().use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }
                runOnUiThread {
                    renderPdf(file)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun renderPdf(file: File) {
        val fileDescriptor =
            ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)

        val pdfRenderer = PdfRenderer(fileDescriptor)
        val page = pdfRenderer.openPage(0)

        val bitmap =
            Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)

        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        imageView.setImageBitmap(bitmap)

        page.close()
        pdfRenderer.close()
    }
}
