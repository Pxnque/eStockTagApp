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

public class TipoMalla extends AppCompatActivity {
    TextView textViewNombre, textViewTipoMalla;
    Button antigranizo;
    String scannedData;
    String[] buttonArray = new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tipo_malla);
        textViewNombre = findViewById(R.id.textViewNombre);
        textViewTipoMalla = findViewById(R.id.textViewTipoMalla);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        scannedData = getIntent().getStringExtra("scannedData");
        if (scannedData != null) {
            String[] dataParts = scannedData.split(",");
            if (dataParts.length == 5) {
                textViewNombre.setText(dataParts[0] + " " + dataParts[1] + " " + dataParts[2]);
                //textViewTipoMalla.setText(dataParts[3]); // Asigna el tipo de malla escaneado
            }
        }

        antigranizo = findViewById(R.id.antigranizobtn);
        antigranizo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonText = antigranizo.getText().toString();
                buttonArray[0] = buttonText; // Almacenar el texto del botón en el array
                Intent intent = new Intent(TipoMalla.this, Sombra.class);
                intent.putExtra("scannedData", scannedData);
                intent.putExtra("buttonText", buttonArray[0]);
                startActivity(intent);
            }
        });
    }
}