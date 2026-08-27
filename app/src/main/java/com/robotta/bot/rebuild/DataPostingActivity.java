package com.robotta.bot.rebuild;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DataPostingActivity extends AppCompatActivity {

    private EditText editSlot, editJudul, editHarga, editKategori,
            editKategoriTerpilih, editKondisi, editDeskripsi,
            editGambar1, editGambar2, editLokasi, editHashtag,
            editDelay, editGambar1Duplicate;

    private CheckBox checkRandomGambar1, checkRandomLokasi;
    private TextView textStatus;
    private PostingStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_posting);

        storage = new PostingStorage(this);
        bindViews();
        resetForm();

        findViewById(R.id.buttonSave).setOnClickListener(v -> saveForm());
        findViewById(R.id.buttonLoad).setOnClickListener(v -> loadForm());
        findViewById(R.id.buttonReset).setOnClickListener(v -> resetForm());
    }

    private void bindViews() {
        editSlot = findViewById(R.id.editSlot);
        editJudul = findViewById(R.id.editJudul);
        editHarga = findViewById(R.id.editHarga);
        editKategori = findViewById(R.id.editKategori);
        editKategoriTerpilih = findViewById(R.id.editKategoriTerpilih);
        editKondisi = findViewById(R.id.editKondisi);
        editDeskripsi = findViewById(R.id.editDeskripsi);
        editGambar1 = findViewById(R.id.editGambar1);
        editGambar2 = findViewById(R.id.editGambar2);
        editLokasi = findViewById(R.id.editLokasi);
        editHashtag = findViewById(R.id.editHashtag);
        editDelay = findViewById(R.id.editDelay);
        editGambar1Duplicate = findViewById(R.id.editGambar1Duplicate);
        checkRandomGambar1 = findViewById(R.id.checkRandomGambar1);
        checkRandomLokasi = findViewById(R.id.checkRandomLokasi);
        textStatus = findViewById(R.id.textStatus);
    }

    private PostingData readForm() {
        PostingData d = new PostingData();

        d.judul = editJudul.getText().toString();
        d.harga = editHarga.getText().toString();
        d.kategori = editKategori.getText().toString();
        d.kategoriTerpilih = editKategoriTerpilih.getText().toString();
        d.kondisi = editKondisi.getText().toString();
        d.deskripsi = editDeskripsi.getText().toString();
        d.gambar1 = editGambar1.getText().toString();
        d.gambar2 = editGambar2.getText().toString();
        d.lokasi = editLokasi.getText().toString();
        d.hashtag = editHashtag.getText().toString();
        d.delay = editDelay.getText().toString();
        d.gambar1Duplicate = editGambar1Duplicate.getText().toString();
        d.randomGambar1 = checkRandomGambar1.isChecked();
        d.randomLokasi = checkRandomLokasi.isChecked();

        return d;
    }

    private void applyForm(PostingData d) {
        editJudul.setText(d.judul);
        editHarga.setText(d.harga);
        editKategori.setText(d.kategori);
        editKategoriTerpilih.setText(d.kategoriTerpilih);
        editKondisi.setText(d.kondisi);
        editDeskripsi.setText(d.deskripsi);
        editGambar1.setText(d.gambar1);
        editGambar2.setText(d.gambar2);
        editLokasi.setText(d.lokasi);
        editHashtag.setText(d.hashtag);
        editDelay.setText(d.delay);
        editGambar1Duplicate.setText(d.gambar1Duplicate);
        checkRandomGambar1.setChecked(d.randomGambar1);
        checkRandomLokasi.setChecked(d.randomLokasi);
    }

    private String slotKey() {
        return editSlot.getText().toString().trim();
    }

    private void saveForm() {
        String key = slotKey();

        if (key.isEmpty()) {
            textStatus.setText("Slot wajib diisi.");
            return;
        }

        boolean ok = storage.save(key, readForm());
        textStatus.setText(ok
                ? "Data tersimpan: " + key
                : "Gagal menyimpan data.");
    }

    private void loadForm() {
        String key = slotKey();

        if (key.isEmpty()) {
            textStatus.setText("Slot wajib diisi.");
            return;
        }

        PostingData d = storage.load(key);

        if (d == null) {
            textStatus.setText("Data slot tidak ditemukan atau rusak.");
            return;
        }

        applyForm(d);
        textStatus.setText("Data dimuat: " + key);
    }

    private void resetForm() {
        applyForm(new PostingData());
        textStatus.setText("Siap.");
    }
}
