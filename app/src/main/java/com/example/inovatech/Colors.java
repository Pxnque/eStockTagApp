package com.example.inovatech;

import android.content.Intent;
import android.graphics.Color;
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

public class Colors extends AppCompatActivity {
    TextView textViewScannedData, textViewButtonText;
    Button negro;
    ArrayList<String> buttonData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_colors);
        textViewScannedData = findViewById(R.id.textViewScannedData);
        textViewButtonText = findViewById(R.id.textViewButtonText);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Obtener los datos del intent
        String scannedData = getIntent().getStringExtra("scannedData");
        ArrayList<String> buttonText = getIntent().getStringArrayListExtra("buttonData");

        buttonData = new ArrayList<>();
        if (buttonText != null) {
            buttonData.addAll(buttonText);
        }

        // Asignar los datos a los TextView
        if (scannedData != null) {
            String[] dataParts = scannedData.split(",");
            if (dataParts.length == 5) {
                textViewScannedData.setText(dataParts[0] + " " + dataParts[1] + " " + dataParts[2]);
            }
        }
        if (buttonText != null) {
            textViewButtonText.setText(String.join(", ", buttonText));
        }
        negro = findViewById(R.id.negrobtn);
        negro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rombozigzagText = negro.getText().toString();
                buttonData.add(rombozigzagText);

                Intent intent = new Intent(Colors.this, Corte.class);
                intent.putExtra("scannedData", scannedData);
                intent.putStringArrayListExtra("buttonData", buttonData);
                startActivity(intent);
            }
        });
    }
}