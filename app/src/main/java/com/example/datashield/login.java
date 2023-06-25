package com.example.datashield;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//import com.pingcap.tidb.Driver;
//import com.pingcap.tidb.TiDBDataSource;

import java.sql.Connection;

import java.sql.Driver;

public class login extends AppCompatActivity {
    Button enc, dec;
    ImageView shield1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enc = (Button) findViewById(R.id.enc);
        dec = (Button) findViewById(R.id.dec);

        shield1 = (ImageView) findViewById(R.id.shield1);

        enc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action when the button is clicked
                moveToNextActivity();

            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action when buttonNext2 is clicked
                moveToNextfile();
            }
        });
    }


    private void moveToNextActivity() {
        // Create an Intent to navigate to the next activity
        Intent intent = new Intent(login.this, file.class);
        startActivity(intent);


    }

    private void moveToNextfile() {
        // Create an Intent to navigate to the next activity
        Intent intent = new Intent(login.this, Dec.class);
        startActivity(intent);
    }
}
//        // Create a TiDB data source
//        TiDBDataSource dataSource = new TiDBDataSource();

// Configure the connection parameters
//        dataSource.setHost("your_tidb_host");
//        dataSource.setPort(your_tidb_port);
//        dataSource.setUser("your_tidb_username");
//        dataSource.setPassword("your_tidb_password");
//        dataSource.setDatabase("your_database_name");

        // Establish the TiDB connection
//        Driver driver = new Driver();
//        Connection connection = driver.connect(dataSource);

// Use the connection to execute queries and interact with TiDB
// ...




