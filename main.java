package com.f221103807.edittextandbutton;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText nimEditText, namaEditText, kelasEditText, semesterEditText, jurusanEditText;
    Button simpanButton, tampilButton, ubahButton, hapusButton;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        nimEditText = findViewById(R.id.nimEditText);
        namaEditText = findViewById(R.id.namaEditText);
        kelasEditText = findViewById(R.id.kelasEditText);
        semesterEditText = findViewById(R.id.semesterEditText);
        jurusanEditText = findViewById(R.id.jurusanEditText);
        simpanButton = findViewById(R.id.simpanButton);
        tampilButton = findViewById(R.id.tampilButton);
        ubahButton = findViewById(R.id.ubahButton);
        hapusButton = findViewById(R.id.hapusButton);
        db = new DBHelper(this);

        simpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view ) {
              String nim_mhs = nimEditText.getText().toString();
              String nama_mhs = namaEditText.getText().toString();
              String kelas_mhs = kelasEditText.getText().toString();
              String semester_mhs = semesterEditText.getText().toString();
              String jurusan_mhs = jurusanEditText.getText().toString();
              if (TextUtils.isEmpty(nim_mhs) || TextUtils.isEmpty(nama_mhs) || TextUtils.isEmpty(kelas_mhs) || TextUtils.isEmpty(semester_mhs) || TextUtils.isEmpty(jurusan_mhs))
                  Toast.makeText(MainActivity.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
              else {
                    Boolean checknim = db.checknim(nim_mhs);
                    if (checknim == false) {
                        Boolean insert = db.insertDataMhs(nim_mhs, nama_mhs, kelas_mhs, semester_mhs, jurusan_mhs);
                        if (insert == true) {
                            Toast.makeText(MainActivity.this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Data Gagal Ditambahkan", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Data Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
              }
            }
        });
        tampilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilDataMhs();
                if (res.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("NIM Mahasiswa : " + res.getString(0)+"\n");
                    buffer.append("Nama Mahasiswa : " + res.getString(1)+"\n");
                    buffer.append("Kelas Mahasiswa : " + res.getString(2)+"\n");
                    buffer.append("Semester Mahasiswa : " + res.getString(3)+"\n");
                    buffer.append("Jurusan Mahasiswa : " + res.getString(4)+"\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Data Mahasiswa");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
