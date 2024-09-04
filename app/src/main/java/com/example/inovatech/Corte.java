package com.example.inovatech;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Corte extends AppCompatActivity {
    TextView textViewScannedData, textViewButtonText;
    Button imprimir,qr;
    Bitmap bmp,bmpQr,scaledBitmap,scaledQr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_corte);
        textViewScannedData = findViewById(R.id.textViewScannedData);
        textViewButtonText = findViewById(R.id.textViewButtonText);
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.ipm);
        bmpQr = BitmapFactory.decodeResource(getResources(),R.drawable.qr_ejemplo);
        scaledBitmap = Bitmap.createScaledBitmap(bmp,80,80,false);
        scaledQr = Bitmap.createScaledBitmap(bmpQr,80,80,false);

        // Obtener los datos del intent
        String scannedData = getIntent().getStringExtra("scannedData");
        ArrayList<String> buttonData = getIntent().getStringArrayListExtra("buttonData");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (scannedData != null) {
            String[] dataParts = scannedData.split(",");
            if (dataParts.length == 5) {
                textViewScannedData.setText(dataParts[0] + " " + dataParts[1] + " " + dataParts[2]);
            }
        }
        if (buttonData != null) {
            textViewButtonText.setText(String.join(", ", buttonData));
        }

        imprimir = findViewById(R.id.imprimir);
        imprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPDF(buttonData);
            }
        });
    }
    private void createPDF(ArrayList<String> buttonData) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(380, 250, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(12);

        float x = 125;
        float y = 115;
        float lineHeight = paint.descent() - paint.ascent();

        if (buttonData != null) {
            for (String line : buttonData) {
                canvas.drawText(line, x, y, paint);
                y += lineHeight + 10;  // Espaciado entre líneas
            }
        } else {
            canvas.drawText("No data available", x, y, paint);
        }
        canvas.drawBitmap(scaledBitmap,300,5,paint);
        canvas.drawBitmap(scaledQr,0,5,paint);
        document.finishPage(page);

        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = "etiqueta.pdf";
        File file = new File(downloadsDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();
            Toast.makeText(this, "Se creo la etiqueta con exito!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.d("mylog", "Error while writing " + e.toString());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}