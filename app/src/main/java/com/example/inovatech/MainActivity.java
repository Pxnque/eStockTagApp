package com.example.inovatech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    Button scan_btn;
    TextView textView;

    private final ActivityResultLauncher<Intent> barcodeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    IntentResult intentResult = IntentIntegrator.parseActivityResult(
                            result.getResultCode(), result.getData());
                    if (intentResult != null && intentResult.getContents() != null) {
                        String scannedContent = intentResult.getContents();

                        // Inicia la nueva actividad con los datos escaneados
                        Intent intent = new Intent(MainActivity.this, TipoMalla.class);
                        intent.putExtra("scannedData", scannedContent);
                        startActivity(intent);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        scan_btn = findViewById(R.id.btnScanner);
        textView = findViewById(R.id.text);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("Escanea un codigo QR");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                Intent intent = intentIntegrator.createScanIntent();
                barcodeLauncher.launch(intent);
            }
        });
    }
}
