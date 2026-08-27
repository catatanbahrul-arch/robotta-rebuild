package com.robotta.bot.rebuild;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/** Native model mirroring the recovered legacy DataPostingActivity JSON. */
public class PostingData {
    public String judul = "";
    public String harga = "";
    public String kategori = "Mebel";
    public String kategoriTerpilih = "";
    public String kondisi = "Baru";
    public String deskripsi = "";
    public String gambar1 = "";
    public String gambar2 = "";
    public String lokasi = "";
    public String hashtag = "";
    public String delay = "25";
    public String gambar1Duplicate = "1";
    public boolean randomGambar1 = false;
    public boolean randomLokasi = false;
    public final List<String> localGambar1Uris = new ArrayList<>();
    public final List<String> localGambar2Uris = new ArrayList<>();

    public JSONObject toJson() throws Exception {
        JSONObject o = new JSONObject();
        o.put("judul", judul);
        o.put("harga", harga);
        o.put("kategori", kategori);
        o.put("kategoriTerpilih", kategoriTerpilih);
        o.put("kondisi", kondisi);
        o.put("deskripsi", deskripsi);
        o.put("gambar1", gambar1);
        o.put("gambar2", gambar2);
        o.put("lokasi", lokasi);
        o.put("hashtag", hashtag);
        o.put("delay", delay);
        o.put("gambar1Duplicate", gambar1Duplicate);
        o.put("randomGambar1", randomGambar1);
        o.put("randomLokasi", randomLokasi);
        o.put("localGambar1Uris", new JSONArray(localGambar1Uris));
        o.put("localGambar2Uris", new JSONArray(localGambar2Uris));
        return o;
    }

    public static PostingData fromJson(String json) throws Exception {
        JSONObject o = new JSONObject(json);
        PostingData d = new PostingData();

        d.judul = o.optString("judul", "");
        d.harga = o.optString("harga", "");
        d.kategori = o.optString("kategori", "Mebel");
        d.kategoriTerpilih = o.optString("kategoriTerpilih", "");
        d.kondisi = o.optString("kondisi", "Baru");
        d.deskripsi = o.optString("deskripsi", "");
        d.gambar1 = o.optString("gambar1", "");
        d.gambar2 = o.optString("gambar2", "");
        d.lokasi = o.optString("lokasi", "");
        d.hashtag = o.optString("hashtag", "");
        d.delay = o.optString("delay", "25");
        d.gambar1Duplicate = o.optString("gambar1Duplicate", "1");
        d.randomGambar1 = o.optBoolean("randomGambar1", false);
        d.randomLokasi = o.optBoolean("randomLokasi", false);

        JSONArray g1 = o.optJSONArray("localGambar1Uris");
        if (g1 != null) {
            for (int i = 0; i < g1.length(); i++) {
                d.localGambar1Uris.add(g1.optString(i, ""));
            }
        }

        JSONArray g2 = o.optJSONArray("localGambar2Uris");
        if (g2 != null) {
            for (int i = 0; i < g2.length(); i++) {
                d.localGambar2Uris.add(g2.optString(i, ""));
            }
        }

        return d;
    }
}
