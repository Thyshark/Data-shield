package com.example.datashield;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;

import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class file extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST_CODE = 1;
    private Button choose;
    private SecretKey secretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

//        choose = findViewById(R.id.choose);
//        choose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Open file chooser
//                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("*/*");
//                startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//            Uri uri = data.getData();
//            String filePath = uri.getPath();
//
//            // Handle the selected file
//            Log.d("File Path:", filePath);
//        }

        choose = findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
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

            // Encrypt the selected file
            encryptFile(filePath);
        }
    }

    private void generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();
        } catch (Exception e) {
            Log.e("Encryption Error", "Failed to generate secret key: " + e.getMessage());
        }
    }

    private void encryptFile(String filePath) {
        try {
            File inputFile = new File(filePath);
            File encryptedFile = new File(inputFile.getParent(), "encrypted_file");

            FileInputStream inputStream = new FileInputStream(inputFile);
            FileOutputStream outputStream = new FileOutputStream(encryptedFile);

            byte[] keyBytes = secretKey.getEncoded();
            Key key = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, bytesRead);
            }

            cipherOutputStream.close();
            inputStream.close();

            Log.d("Encryption", "File encrypted successfully: " + encryptedFile.getAbsolutePath());



        } catch (Exception e) {
            Log.e("Encryption Error", "Failed to encrypt file: " + e.getMessage());
        }
    }
    }

    // Encrypt the data
//    String dataToEncrypt = "Sensitive data";
//    Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//                    byte[] encryptedData = cipher.doFinal(dataToEncrypt.getBytes());
//
//                    // Store the encrypted data in TiDB
//                    Connection connection = TiDBConnectionUtil.getConnection();
//                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO your_table (encrypted_data) VALUES (?)");
//                    preparedStatement.setBytes(1, encryptedData);
//                    preparedStatement.executeUpdate();
//
//                    preparedStatement.close();
//                    connection.close();
//                    } catch (Exception e) {
//                    e.printStackTrace();
//                    }