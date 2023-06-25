package com.example.datashield;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Dec extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST_CODE = 1;
    private Button cfile;
    private SecretKey secretKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dec);







        cfile = findViewById(R.id.cfile);
        cfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open file chooser
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("*/*");
                    startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
                }
            });

            generateSecretKey();
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                Uri uri = data.getData();
                String filePath = uri.getPath();

                // Decrypt the selected file
                decryptFile(filePath);
            }
        }

        private void generateSecretKey() {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                keyGenerator.init(256);
                secretKey = keyGenerator.generateKey();
            } catch (Exception e) {
                Log.e("Decryption Error", "Failed to generate secret key: " + e.getMessage());
            }
        }

        private void decryptFile(String filePath) {
            try {
                File encryptedFile = new File(filePath);
                File decryptedFile = new File(encryptedFile.getParent(), "decrypted_file");

                FileInputStream inputStream = new FileInputStream(encryptedFile);
                FileOutputStream outputStream = new FileOutputStream(decryptedFile);

                byte[] keyBytes = secretKey.getEncoded();
                Key key = new SecretKeySpec(keyBytes, "AES");

                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, key);

                CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                cipherInputStream.close();
                outputStream.close();

                Log.d("Decryption", "File decrypted successfully: " + decryptedFile.getAbsolutePath());
            } catch (Exception e) {
                Log.e("Decryption Error", "Failed to decrypt file: " + e.getMessage());
            }
        }
}
