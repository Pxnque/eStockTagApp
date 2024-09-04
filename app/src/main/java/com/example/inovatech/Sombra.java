package com.example.inovatech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Sombra extends AppCompatActivity {
    TextView textViewScannedData, textViewButtonText;
    Button rombozigzag;
    ArrayList<String> buttonData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sombra);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textViewScannedData = findViewById(R.id.textViewScannedData);
        textViewButtonText = findViewById(R.id.textViewButtonText);

        // Obtener los datos del intent
        String scannedData = getIntent().getStringExtra("scannedData");
        String buttonText = getIntent().getStringExtra("buttonText");

        buttonData = new ArrayList<>();
        if (buttonText != null) {
            buttonData.add(buttonText);
        }

        // Asignar los datos a los TextView
        if (scannedData != null) {
            String[] dataParts = scannedData.split(",");
            if (dataParts.length == 5) {
                textViewScannedData.setText(dataParts[0] + " " + dataParts[1] + " " + dataParts[2]);
                //textViewTipoMalla.setText(dataParts[3]); // Asigna el tipo de malla escaneado
            }
        }
        textViewButtonText.setText(buttonText);
        rombozigzag = findViewById(R.id.rombobtn);
        rombozigzag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rombozigzagText = rombozigzag.getText().toString();
                buttonData.add(rombozigzagText);

                Intent intent = new Intent(Sombra.this, Colors.class);
                intent.putExtra("scannedData", scannedData);
                intent.putStringArrayListExtra("buttonData", buttonData);
                startActivity(intent);
            }
        });
    }
}