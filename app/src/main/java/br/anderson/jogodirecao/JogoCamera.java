package br.anderson.jogodirecao;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class JogoCamera extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 123;
    private RelativeLayout layout;
    private Button btnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo_camera);

        layout = findViewById(R.id.layout);
        btnCamera = findViewById(R.id.btn_camera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    // Permissão concedida, reposiciona o botão
                    reposicionarBotao();
                } else {
                    requestCameraPermission();
                }
            }
        });
    }

    private void reposicionarBotao() {
        Random random = new Random();
        int width = layout.getWidth() - btnCamera.getWidth();
        int height = layout.getHeight() - btnCamera.getHeight();

        // Define novas posições aleatórias
        int left = random.nextInt(width);
        int top = random.nextInt(height);
        int right = left + btnCamera.getWidth();
        int bottom = top + btnCamera.getHeight();

        // Define as novas posições do botão
        btnCamera.layout(left, top, right, bottom);
    }

    private boolean checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this, "Permissão da câmera é necessária para o jogo", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, reposiciona o botão
                reposicionarBotao();
            } else {
                Toast.makeText(this, "Permissão da câmera foi negada. O jogo não pode ser iniciado.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
