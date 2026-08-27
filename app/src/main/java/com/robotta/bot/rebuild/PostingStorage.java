package com.robotta.bot.rebuild;

import android.content.Context;
import android.content.SharedPreferences;

/** Storage bridge for the recovered DataPostingActivity slot format. */
public final class PostingStorage {
    private static final String PREFS_NAME = "robotta_data_posting_backup";
    private final SharedPreferences prefs;

    public PostingStorage(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean save(String slotKey, PostingData data) {
        if (slotKey == null || slotKey.trim().isEmpty() || data == null) return false;
        try {
            return prefs.edit()
                    .putString(slotKey, data.toJson().toString())
                    .commit();
        } catch (Exception e) {
            return false;
        }
    }

    public PostingData load(String slotKey) {
        if (slotKey == null || slotKey.trim().isEmpty()) return null;

        String json = prefs.getString(slotKey, null);
        if (json == null || json.trim().isEmpty()) return null;

        try {
            return PostingData.fromJson(json);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean exists(String slotKey) {
        return slotKey != null && prefs.contains(slotKey);
    }

    public boolean delete(String slotKey) {
        if (slotKey == null || slotKey.trim().isEmpty()) return false;
        return prefs.edit().remove(slotKey).commit();
    }
}
