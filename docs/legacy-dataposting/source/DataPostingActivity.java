package com.robotta.bot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.webkit.ProxyConfig;
import androidx.webkit.internal.AssetHelper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /data/data/com.termux/files/home/robotta-rebuild/robotta_rebuild/unpacked/classes.dex */
public class DataPostingActivity extends AppCompatActivity {
    private static final String ADDED_SLOTS_KEY = "addedSlots";
    private static final String DATA_EXPORT_FORMAT = "robotta_data_posting_backup";
    private static final int DATA_EXPORT_VERSION = 1;
    private static final int DEFAULT_FRAME_THICKNESS = 120;
    private static final int DEFAULT_PNG_SIZE = 350;
    private static final String GITHUB_FRAME_IMAGE_URL = "https://raw.githubusercontent.com/Raisrahman37/Linktautan/main/Link%20Gambar%20Tautan.txt";
    private static final String GITHUB_PNG_IMAGE_URL = "https://raw.githubusercontent.com/Raisrahman37/Linktautan/main/Png%20Link.txt";
    private static final String GOOGLE_SCRIPT_URL = "https://script.google.com/macros/s/AKfycbxgmbXWGA2QWh8UWJg3mcoi-cOJkmZ5SiYeHXZe-mGOpE-eLIDYs88uu1DZACR7N6kS/exec";
    private static final String GROQ_API_KEY = "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";
    private static final String GROQ_API_URL = "https://integrate.api.nvidia.com/v1/chat/completions";
    private static final int INITIAL_SLOTS = 10;
    private static final int JPEG_QUALITY = 82;
    private static final String LAST_SELECTED_SLOT_KEY = "lastSelectedSlot";
    private static final String LOKASI_CSV_URL = "https://docs.google.com/spreadsheets/d/e/2PACX-1vRoHHzJ4vovorugMtI30o_LAE0n6rPwScpTL25xM-QPLPL-Gr32GLXMAw3qtJEMMBES0FXmLeRsl44I/pub?output=csv";
    private static final int MAX_AI_RETRIES = 5;
    private static final int MAX_SLOTS = 1000;
    private static final String PREFS_NAME = "data_posting_native";
    private static final String PREF_AI_ADD_PHONE = "aiAddPhoneToTitle";
    private static final String PREF_AI_LINE_COUNT = "aiJudulLineCount";
    private static final String PREF_AI_PHONE = "aiPhoneNumber";
    private static final String PREF_AI_PRODUCT = "aiProductName";
    private static final String SLOT_NAME_PREFIX = "name_";
    private FrameLayout aiOverlay;
    private TextView aiOverlayPercentText;
    private ProgressBar aiOverlayProgressBar;
    private TextView aiOverlayStatusText;
    private Button btnCancelGambar1Progress;
    private Button btnCancelGambar2Progress;
    private CheckBox checkRandomGambar1;
    private CheckBox checkRandomLokasi;
    private AppThemeManager.ThemePalette currentTheme;
    private ExecutorService currentUploadPool;
    private Future<?> currentUploadTask;
    private EditText editDelay;
    private EditText editDeskripsi;
    private EditText editGambar1;
    private EditText editGambar1Duplicate;
    private EditText editGambar2;
    private EditText editHarga;
    private EditText editHashtag;
    private EditText editJudul;
    private EditText editKategoriTerpilih;
    private EditText editLokasi;
    private ActivityResultLauncher<String> exportJsonLauncher;
    private ActivityResultLauncher<String[]> gambar1PickerLauncher;
    private ActivityResultLauncher<Intent> gambar1UploadPickerLauncher;
    private ActivityResultLauncher<String[]> gambar2PickerLauncher;
    private ActivityResultLauncher<Intent> gambar2UploadPickerLauncher;
    private ActivityResultLauncher<String[]> importJsonLauncher;
    private ThemedSpinnerAdapter<String> kategoriAdapter;
    private ThemedSpinnerAdapter<String> kondisiAdapter;
    private LinearLayout layoutGambar1Progress;
    private LinearLayout layoutGambar2Progress;
    private String pendingExportJson;
    private SharedPreferences prefs;
    private ProgressBar progressGambar1;
    private ProgressBar progressGambar2;
    private ThemedSpinnerAdapter<SlotItem> slotAdapter;
    private Spinner spinnerDataSlot;
    private Spinner spinnerKategori;
    private Spinner spinnerKondisi;
    private Spinner spinnerUnicodeStyle;
    private TextView textGambar1Count;
    private TextView textGambar1ImportInfo;
    private TextView textGambar1Numbers;
    private TextView textGambar1Progress;
    private TextView textGambar2Count;
    private TextView textGambar2ImportInfo;
    private TextView textGambar2Numbers;
    private TextView textGambar2Progress;
    private TextView textJudulCount;
    private TextView textJudulNumbers;
    private TextView textLokasiCount;
    private TextView textLokasiNumbers;
    private ThemedSpinnerAdapter<String> unicodeAdapter;
    private static final List<String> KATEGORI_OPTIONS = Arrays.asList("Mebel", "Peralatan rumah tangga", "Perkakas", "Peralatan", "Kebun", "Elektronik & komputer", "Telepon seluler", "Cuci Gudang", "Lain-lain", "Olahraga & Outdoor", "Barang Antik & Koleksi", "Alat Musik", "Seni & Kerajinan", "Komponen otomotif", "Sepeda", "Pakaian & Sepatu Wanita", "Pakaian & Sepatu Pria", "Perhiasan & Aksesoris", "Tas & Koper", "Bayi & Anak-Anak", "Kesehatan & Kecantikan", "Mainan & Game", "Kebutuhan Hewan Peliharaan", "Video Game", "Buku, Film, & Musik");
    private static final List<String> UNICODE_STYLES = Arrays.asList("mixed", "bold", "bold-sans", "italic", "bold-italic", "monospace", "fullwidth", "small-caps", "leet-style");
    private static final List<String> KONDISI_OPTIONS = Arrays.asList("Baru", "Bekas");
    private static final Map<String, Map<Character, String>> UNICODE_STYLE_MAP = buildUnicodeStyleMap();
    private static final String[] KATA_BOMBASTIS = {"PROMO", "DISKON", "PROMO MURAH", "SALE", "TERLARIS", "BEST SELLER", "TERMURAH", "FLASH SALE", "SUPER MURAH", "HARGA GROSIR"};
    private final List<SlotItem> slotItems = new ArrayList();
    private boolean suppressSlotListener = false;
    private boolean suppressKategoriListener = false;
    private boolean kategoriSelectionInitialized = false;
    private String currentSlotValue = "data1";
    private final ExecutorService backgroundExecutor = Executors.newFixedThreadPool(3);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final List<String> lokasiData = new ArrayList();
    private final LinkedHashSet<String> selectedLokasi = new LinkedHashSet<>();
    private final List<Uri> selectedGambar1Uris = new ArrayList();
    private final List<Uri> selectedGambar2Uris = new ArrayList();
    private final List<String> availableFrameImageUrls = new ArrayList();
    private final List<String> availablePngUrls = new ArrayList();
    private final Map<String, Bitmap> remoteBitmapCache = new HashMap();
    private final Random random = new Random();
    private final List<Future<String>> currentUploadFutures = new ArrayList();
    private final List<HttpURLConnection> activeUploadConnections = Collections.synchronizedList(new ArrayList());
    private final AtomicBoolean cancelUploadGambar1 = new AtomicBoolean(false);
    private final AtomicBoolean cancelUploadGambar2 = new AtomicBoolean(false);
    private String originalJudulBeforeUnicode = "";

    private TextView getLineNumberView(EditText editText) {
        if (editText == this.editJudul) {
            return this.textJudulNumbers;
        }
        if (editText == this.editGambar1) {
            return this.textGambar1Numbers;
        }
        if (editText == this.editGambar2) {
            return this.textGambar2Numbers;
        }
        if (editText == this.editLokasi) {
            return this.textLokasiNumbers;
        }
        return null;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_data_posting);
        this.prefs = getSharedPreferences(PREFS_NAME, 0);
        this.currentTheme = AppThemeManager.getPalette(this);
        bindViews();
        setupSlotSpinner();
        setupKategoriSpinner();
        setupKondisiSpinner();
        setupUnicodeSpinner();
        setupListeners();
        setupScrollableEditors();
        setupLineCounters();
        setupPickers();
        applyCurrentTheme();
        rebuildDataSlotOptions(this.prefs.getString(LAST_SELECTED_SLOT_KEY, "data1"));
        buildAiOverlay();
    }

    private void buildAiOverlay() {
        float f = getResources().getDisplayMetrics().density;
        FrameLayout frameLayout = new FrameLayout(this);
        this.aiOverlay = frameLayout;
        frameLayout.setBackgroundColor(-870703060);
        this.aiOverlay.setClickable(true);
        this.aiOverlay.setFocusable(true);
        this.aiOverlay.setVisibility(8);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        linearLayout.setBackgroundColor(-1);
        int i = (int) (24.0f * f);
        int i2 = (int) (32.0f * f);
        linearLayout.setPadding(i, i2, i, i2);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) (280.0f * f), -2);
        layoutParams.gravity = 17;
        linearLayout.setElevation(12.0f * f);
        TextView textView = new TextView(this);
        textView.setText("⏳ Menggenerate Konten AI");
        textView.setTextSize(16.0f);
        textView.setTextColor(-15065044);
        textView.setTypeface(null, 1);
        textView.setGravity(17);
        linearLayout.addView(textView);
        ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        this.aiOverlayProgressBar = progressBar;
        progressBar.setMax(100);
        this.aiOverlayProgressBar.setProgress(0);
        int i3 = (int) (8.0f * f);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, i3);
        layoutParams2.topMargin = (int) (16.0f * f);
        this.aiOverlayProgressBar.setLayoutParams(layoutParams2);
        linearLayout.addView(this.aiOverlayProgressBar);
        TextView textView2 = new TextView(this);
        this.aiOverlayPercentText = textView2;
        textView2.setText("0%");
        this.aiOverlayPercentText.setTextSize(22.0f);
        this.aiOverlayPercentText.setTextColor(-14326805);
        this.aiOverlayPercentText.setTypeface(null, 1);
        this.aiOverlayPercentText.setGravity(17);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams3.topMargin = i3;
        this.aiOverlayPercentText.setLayoutParams(layoutParams3);
        linearLayout.addView(this.aiOverlayPercentText);
        TextView textView3 = new TextView(this);
        this.aiOverlayStatusText = textView3;
        textView3.setText("Menyiapkan...");
        this.aiOverlayStatusText.setTextSize(13.0f);
        this.aiOverlayStatusText.setTextColor(-10193781);
        this.aiOverlayStatusText.setGravity(17);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams4.topMargin = (int) (f * 6.0f);
        this.aiOverlayStatusText.setLayoutParams(layoutParams4);
        linearLayout.addView(this.aiOverlayStatusText);
        this.aiOverlay.addView(linearLayout, layoutParams);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.rootDataPosting);
        if (viewGroup instanceof LinearLayout) {
            FrameLayout frameLayout2 = new FrameLayout(this);
            frameLayout2.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
            int iIndexOfChild = viewGroup2.indexOfChild(viewGroup);
            viewGroup2.removeView(viewGroup);
            frameLayout2.addView(viewGroup);
            frameLayout2.addView(this.aiOverlay, new FrameLayout.LayoutParams(-1, -1));
            viewGroup2.addView(frameLayout2, iIndexOfChild);
        }
    }

    private void showAiOverlay() {
        this.mainHandler.post(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showAiOverlay$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAiOverlay$0() {
        if (this.aiOverlay != null) {
            this.aiOverlayProgressBar.setProgress(0);
            this.aiOverlayPercentText.setText("0%");
            this.aiOverlayStatusText.setText("Menyiapkan...");
            this.aiOverlay.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: hideAiOverlay, reason: merged with bridge method [inline-methods] */
    public void lambda$generateAiContent$33() {
        this.mainHandler.post(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$hideAiOverlay$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideAiOverlay$1() {
        FrameLayout frameLayout = this.aiOverlay;
        if (frameLayout != null) {
            frameLayout.setVisibility(8);
        }
    }

    private void updateAiOverlayProgress(final int i, final String str) {
        this.mainHandler.post(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateAiOverlayProgress$2(i, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateAiOverlayProgress$2(int i, String str) {
        ProgressBar progressBar = this.aiOverlayProgressBar;
        if (progressBar != null) {
            progressBar.setProgress(i);
        }
        TextView textView = this.aiOverlayPercentText;
        if (textView != null) {
            textView.setText(i + "%");
        }
        TextView textView2 = this.aiOverlayStatusText;
        if (textView2 != null) {
            textView2.setText(str);
        }
    }

    private void bindViews() {
        this.spinnerDataSlot = (Spinner) findViewById(R.id.spinnerDataSlot);
        this.editJudul = (EditText) findViewById(R.id.editJudul);
        this.editHarga = (EditText) findViewById(R.id.editHarga);
        this.spinnerKategori = (Spinner) findViewById(R.id.spinnerKategori);
        this.spinnerKondisi = (Spinner) findViewById(R.id.spinnerKondisi);
        this.spinnerUnicodeStyle = (Spinner) findViewById(R.id.spinnerUnicodeStyle);
        this.editKategoriTerpilih = (EditText) findViewById(R.id.editKategoriTerpilih);
        this.editDeskripsi = (EditText) findViewById(R.id.editDeskripsi);
        this.editGambar1 = (EditText) findViewById(R.id.editGambar1);
        this.editGambar2 = (EditText) findViewById(R.id.editGambar2);
        this.editGambar1Duplicate = (EditText) findViewById(R.id.editGambar1Duplicate);
        this.editLokasi = (EditText) findViewById(R.id.editLokasi);
        this.checkRandomGambar1 = (CheckBox) findViewById(R.id.checkRandomGambar1);
        this.checkRandomLokasi = (CheckBox) findViewById(R.id.checkRandomLokasi);
        this.editHashtag = (EditText) findViewById(R.id.editHashtag);
        this.editDelay = (EditText) findViewById(R.id.editDelay);
        this.textJudulCount = (TextView) findViewById(R.id.textJudulCount);
        this.textGambar1Count = (TextView) findViewById(R.id.textGambar1Count);
        this.textGambar2Count = (TextView) findViewById(R.id.textGambar2Count);
        this.textLokasiCount = (TextView) findViewById(R.id.textLokasiCount);
        this.textJudulNumbers = (TextView) findViewById(R.id.textJudulNumbers);
        this.textGambar1Numbers = (TextView) findViewById(R.id.textGambar1Numbers);
        this.textGambar2Numbers = (TextView) findViewById(R.id.textGambar2Numbers);
        this.textLokasiNumbers = (TextView) findViewById(R.id.textLokasiNumbers);
        this.textGambar1ImportInfo = (TextView) findViewById(R.id.textGambar1ImportInfo);
        this.textGambar2ImportInfo = (TextView) findViewById(R.id.textGambar2ImportInfo);
        this.textGambar1Progress = (TextView) findViewById(R.id.textGambar1Progress);
        this.textGambar2Progress = (TextView) findViewById(R.id.textGambar2Progress);
        this.layoutGambar1Progress = (LinearLayout) findViewById(R.id.layoutGambar1Progress);
        this.layoutGambar2Progress = (LinearLayout) findViewById(R.id.layoutGambar2Progress);
        this.progressGambar1 = (ProgressBar) findViewById(R.id.progressGambar1);
        this.progressGambar2 = (ProgressBar) findViewById(R.id.progressGambar2);
        this.btnCancelGambar1Progress = (Button) findViewById(R.id.btnCancelGambar1Progress);
        this.btnCancelGambar2Progress = (Button) findViewById(R.id.btnCancelGambar2Progress);
    }

    private void setupSlotSpinner() {
        ThemedSpinnerAdapter<SlotItem> themedSpinnerAdapter = new ThemedSpinnerAdapter<>(this, this.slotItems, this.currentTheme);
        this.slotAdapter = themedSpinnerAdapter;
        this.spinnerDataSlot.setAdapter((SpinnerAdapter) themedSpinnerAdapter);
    }

    private void setupKategoriSpinner() {
        List<String> list = KATEGORI_OPTIONS;
        ThemedSpinnerAdapter<String> themedSpinnerAdapter = new ThemedSpinnerAdapter<>(this, list, this.currentTheme);
        this.kategoriAdapter = themedSpinnerAdapter;
        this.spinnerKategori.setAdapter((SpinnerAdapter) themedSpinnerAdapter);
        this.spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.robotta.bot.DataPostingActivity.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (DataPostingActivity.this.suppressKategoriListener) {
                    return;
                }
                if (!DataPostingActivity.this.kategoriSelectionInitialized) {
                    DataPostingActivity.this.kategoriSelectionInitialized = true;
                } else {
                    DataPostingActivity.this.addSelectedKategori();
                }
            }
        });
        this.suppressKategoriListener = true;
        this.spinnerKategori.setSelection(list.indexOf("Mebel"));
        this.suppressKategoriListener = false;
    }

    private void setupUnicodeSpinner() {
        ThemedSpinnerAdapter<String> themedSpinnerAdapter = new ThemedSpinnerAdapter<>(this, UNICODE_STYLES, this.currentTheme);
        this.unicodeAdapter = themedSpinnerAdapter;
        this.spinnerUnicodeStyle.setAdapter((SpinnerAdapter) themedSpinnerAdapter);
        this.spinnerUnicodeStyle.setSelection(0);
    }

    private void setupKondisiSpinner() {
        ThemedSpinnerAdapter<String> themedSpinnerAdapter = new ThemedSpinnerAdapter<>(this, KONDISI_OPTIONS, this.currentTheme);
        this.kondisiAdapter = themedSpinnerAdapter;
        this.spinnerKondisi.setAdapter((SpinnerAdapter) themedSpinnerAdapter);
        this.spinnerKondisi.setSelection(0);
    }

    private void applyCurrentTheme() {
        View viewFindViewById = findViewById(R.id.rootDataPosting);
        View viewFindViewById2 = findViewById(R.id.topBarDataPosting);
        viewFindViewById.setBackgroundColor(Color.parseColor(this.currentTheme.background));
        viewFindViewById2.setBackgroundColor(Color.parseColor(this.currentTheme.header));
        tintTree((ViewGroup) viewFindViewById);
        ThemedSpinnerAdapter<SlotItem> themedSpinnerAdapter = this.slotAdapter;
        if (themedSpinnerAdapter != null) {
            themedSpinnerAdapter.setPalette(AppThemeManager.LIGHT);
        }
        ThemedSpinnerAdapter<String> themedSpinnerAdapter2 = this.kategoriAdapter;
        if (themedSpinnerAdapter2 != null) {
            themedSpinnerAdapter2.setPalette(AppThemeManager.LIGHT);
        }
        ThemedSpinnerAdapter<String> themedSpinnerAdapter3 = this.kondisiAdapter;
        if (themedSpinnerAdapter3 != null) {
            themedSpinnerAdapter3.setPalette(AppThemeManager.LIGHT);
        }
        ThemedSpinnerAdapter<String> themedSpinnerAdapter4 = this.unicodeAdapter;
        if (themedSpinnerAdapter4 != null) {
            themedSpinnerAdapter4.setPalette(AppThemeManager.LIGHT);
        }
    }

    private void tintTree(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            int id = childAt.getId();
            if (id == R.id.rootDataPosting || id == R.id.topBarDataPosting) {
                if (childAt instanceof ViewGroup) {
                    tintTree((ViewGroup) childAt);
                }
            } else {
                if (childAt.getBackground() != null && !(childAt instanceof ProgressBar)) {
                    if (isPlainInnerEditor(childAt)) {
                        childAt.setBackgroundColor(0);
                    } else {
                        childAt.setBackground(AppThemeManager.outlined("#FFFFFF", "#2D8CFF", 18));
                    }
                }
                boolean z = childAt instanceof Button;
                if (z) {
                    Button button = (Button) childAt;
                    boolean z2 = true;
                    boolean z3 = id == R.id.btnOpenAiDialog || id == R.id.btnPilihGambar1 || id == R.id.btnBuatFrameGambar1 || id == R.id.btnTanpaFrameGambar1 || id == R.id.btnUploadGambar2NoFrame || id == R.id.btnSaveData;
                    if (id != R.id.btnClearData && id != R.id.btnClearKategoriTerpilih) {
                        z2 = false;
                    }
                    styleButton(button, z3, z2);
                } else {
                    boolean z4 = childAt instanceof EditText;
                    if (z4) {
                        EditText editText = (EditText) childAt;
                        if (isPlainInnerEditor(editText)) {
                            editText.setBackgroundColor(0);
                        } else {
                            editText.setBackground(AppThemeManager.outlined("#FFFFFF", "#2D8CFF", 14));
                        }
                        editText.setTextColor(Color.parseColor("#16345F"));
                        editText.setHintTextColor(Color.parseColor("#7E9AC2"));
                    } else if ((childAt instanceof TextView) && !z && !z4) {
                        TextView textView = (TextView) childAt;
                        if (id == R.id.textGambar1Progress || id == R.id.textGambar2Progress) {
                            textView.setTextColor(Color.parseColor(this.currentTheme.accent));
                        } else if (id == R.id.textJudulCount || id == R.id.textGambar1Count || id == R.id.textGambar2Count || id == R.id.textLokasiCount || id == R.id.textJudulNumbers || id == R.id.textGambar1Numbers || id == R.id.textGambar2Numbers || id == R.id.textLokasiNumbers || id == R.id.textGambar1ImportInfo || id == R.id.textGambar2ImportInfo) {
                            textView.setTextColor(Color.parseColor("#7E9AC2"));
                        } else {
                            textView.setTextColor(Color.parseColor("#16345F"));
                        }
                    } else if (childAt instanceof CheckBox) {
                        ((CheckBox) childAt).setTextColor(Color.parseColor("#16345F"));
                    } else if (childAt instanceof Spinner) {
                        childAt.setBackground(AppThemeManager.outlined("#FFFFFF", "#2D8CFF", 14));
                    }
                }
                if (childAt instanceof ViewGroup) {
                    tintTree((ViewGroup) childAt);
                }
            }
        }
    }

    private void styleButton(Button button, boolean z, boolean z2) {
        if (button.getId() == R.id.btnCancelGambar1Progress || button.getId() == R.id.btnCancelGambar2Progress) {
            button.setBackground(AppThemeManager.filled("#E53935", 10));
            button.setTextColor(-1);
            return;
        }
        if (button.getId() == R.id.btnOpenAiDialog) {
            button.setBackground(AppThemeManager.filled("#FF3B30", 14));
            button.setTextColor(-1);
        } else if (z2) {
            button.setBackground(AppThemeManager.outlined(this.currentTheme.dangerBackground, this.currentTheme.dangerText, 14));
            button.setTextColor(Color.parseColor(this.currentTheme.dangerText));
        } else if (z) {
            button.setBackground(AppThemeManager.filled(this.currentTheme.accent, 14));
            button.setTextColor(Color.parseColor(this.currentTheme.accentText));
        } else {
            button.setBackground(AppThemeManager.outlined(this.currentTheme.outlineBackground, this.currentTheme.accent, 14));
            button.setTextColor(Color.parseColor(this.currentTheme.outlineText));
        }
    }

    private String darken(String str) {
        int color = Color.parseColor(str);
        return String.format("#%02X%02X%02X", Integer.valueOf(Math.max((int) (Color.red(color) * 0.75f), 0)), Integer.valueOf(Math.max((int) (Color.green(color) * 0.75f), 0)), Integer.valueOf(Math.max((int) (Color.blue(color) * 0.75f), 0)));
    }

    private boolean isPlainInnerEditor(View view) {
        int id = view.getId();
        return id == R.id.editJudul || id == R.id.editGambar1 || id == R.id.editGambar2 || id == R.id.editLokasi;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$3(View view) {
        finish();
    }

    private void setupListeners() {
        findViewById(R.id.btnBackDataPosting).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$3(view);
            }
        });
        findViewById(R.id.btnRenameSlot).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$4(view);
            }
        });
        findViewById(R.id.btnAddSlot).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$5(view);
            }
        });
        findViewById(R.id.btnSaveData).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$6(view);
            }
        });
        findViewById(R.id.btnClearData).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$7(view);
            }
        });
        findViewById(R.id.btnExportData).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$8(view);
            }
        });
        findViewById(R.id.btnImportData).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$9(view);
            }
        });
        findViewById(R.id.btnAddKategori).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$10(view);
            }
        });
        findViewById(R.id.btnClearKategoriTerpilih).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$11(view);
            }
        });
        findViewById(R.id.btnOpenAiDialog).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$12(view);
            }
        });
        findViewById(R.id.btnConvertUnicode).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda22
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$13(view);
            }
        });
        findViewById(R.id.btnResetUnicode).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda33
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$14(view);
            }
        });
        findViewById(R.id.btnPilihGambar1).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda44
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$15(view);
            }
        });
        findViewById(R.id.btnBuatFrameGambar1).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda55
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$16(view);
            }
        });
        findViewById(R.id.btnTanpaFrameGambar1).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda63
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$17(view);
            }
        });
        findViewById(R.id.btnUploadGambar2NoFrame).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda64
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$18(view);
            }
        });
        this.btnCancelGambar1Progress.setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda65
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$19(view);
            }
        });
        this.btnCancelGambar2Progress.setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda66
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$20(view);
            }
        });
        findViewById(R.id.btnCariLokasi).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupListeners$21(view);
            }
        });
        setupFullscreenEditorTrigger(this.editJudul, "Judul");
        setupFullscreenEditorTrigger(this.editGambar1, "Gambar 1");
        setupFullscreenEditorTrigger(this.editGambar2, "Gambar 2");
        setupFullscreenEditorTrigger(this.editLokasi, "Lokasi");
        setupFullscreenEditorTrigger(this.editDeskripsi, "Deskripsi");
        setupFullscreenEditorTrigger(this.editHashtag, "Hashtag");
        setupExpandButton(R.id.btnExpandJudul, this.editJudul, "Judul");
        setupExpandButton(R.id.btnExpandDeskripsi, this.editDeskripsi, "Deskripsi");
        setupExpandButton(R.id.btnExpandGambar1, this.editGambar1, "Gambar 1");
        setupExpandButton(R.id.btnExpandGambar2, this.editGambar2, "Gambar 2");
        setupExpandButton(R.id.btnExpandLokasi, this.editLokasi, "Lokasi");
        setupExpandButton(R.id.btnExpandHashtag, this.editHashtag, "Hashtag");
        this.spinnerDataSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.robotta.bot.DataPostingActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (DataPostingActivity.this.suppressSlotListener || i < 0 || i >= DataPostingActivity.this.slotItems.size()) {
                    return;
                }
                DataPostingActivity dataPostingActivity = DataPostingActivity.this;
                dataPostingActivity.currentSlotValue = ((SlotItem) dataPostingActivity.slotItems.get(i)).value;
                DataPostingActivity.this.prefs.edit().putString(DataPostingActivity.LAST_SELECTED_SLOT_KEY, DataPostingActivity.this.currentSlotValue).commit();
                DataPostingActivity.this.loadData();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$4(View view) {
        renameSlot();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$5(View view) {
        addSlot();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$6(View view) {
        saveData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$7(View view) {
        clearData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$8(View view) {
        exportData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$9(View view) {
        importData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$10(View view) {
        addSelectedKategori();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$11(View view) {
        this.editKategoriTerpilih.setText("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$12(View view) {
        openAiDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$13(View view) {
        convertJudulToUnicode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$14(View view) {
        resetUnicode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$15(View view) {
        launchGambar1UploadPicker();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$16(View view) {
        processImageUpload(1, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$17(View view) {
        processImageUpload(1, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$18(View view) {
        launchGambar2UploadPicker();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$19(View view) {
        requestCancelUpload(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$20(View view) {
        requestCancelUpload(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupListeners$21(View view) {
        openLokasiDialog();
    }

    private void setupPickers() {
        this.exportJsonLauncher = registerForActivityResult(new ActivityResultContracts.CreateDocument("application/json"), new ActivityResultCallback() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda36
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$setupPickers$22((Uri) obj);
            }
        });
        this.importJsonLauncher = registerForActivityResult(new ActivityResultContracts.OpenDocument(), new ActivityResultCallback() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda37
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$setupPickers$23((Uri) obj);
            }
        });
        this.gambar1PickerLauncher = registerForActivityResult(new ActivityResultContracts.OpenMultipleDocuments(), new ActivityResultCallback() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda38
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$setupPickers$24((List) obj);
            }
        });
        this.gambar2PickerLauncher = registerForActivityResult(new ActivityResultContracts.OpenMultipleDocuments(), new ActivityResultCallback() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda39
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$setupPickers$25((List) obj);
            }
        });
        this.gambar1UploadPickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda40
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$setupPickers$26((ActivityResult) obj);
            }
        });
        this.gambar2UploadPickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda41
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$setupPickers$27((ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupPickers$22(Uri uri) {
        String str;
        if (uri != null && (str = this.pendingExportJson) != null) {
            writeExportFile(uri, str);
        }
        this.pendingExportJson = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupPickers$23(Uri uri) {
        if (uri != null) {
            readImportFile(uri);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupPickers$24(List list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.selectedGambar1Uris.clear();
        this.selectedGambar1Uris.addAll(persistAndFilterUris(list));
        updateSelectedImageInfo(1);
        Toast.makeText(this, this.selectedGambar1Uris.size() + " gambar dipilih untuk Gambar 1.", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupPickers$25(List list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.selectedGambar2Uris.clear();
        this.selectedGambar2Uris.addAll(persistAndFilterUris(list));
        updateSelectedImageInfo(2);
        Toast.makeText(this, this.selectedGambar2Uris.size() + " gambar dipilih untuk Gambar 2.", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupPickers$26(ActivityResult activityResult) {
        if (activityResult.getResultCode() != -1) {
            return;
        }
        List<Uri> listExtractUrisFromPickerResult = extractUrisFromPickerResult(activityResult.getData());
        if (listExtractUrisFromPickerResult.isEmpty()) {
            Toast.makeText(this, "Tidak ada gambar dipilih.", 0).show();
            return;
        }
        this.selectedGambar1Uris.clear();
        this.selectedGambar1Uris.addAll(persistAndFilterUris(listExtractUrisFromPickerResult));
        updateSelectedImageInfo(1);
        Toast.makeText(this, this.selectedGambar1Uris.size() + " gambar dipilih untuk Gambar 1.", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupPickers$27(ActivityResult activityResult) {
        if (activityResult.getResultCode() != -1) {
            return;
        }
        List<Uri> listExtractUrisFromPickerResult = extractUrisFromPickerResult(activityResult.getData());
        if (listExtractUrisFromPickerResult.isEmpty()) {
            Toast.makeText(this, "Tidak ada gambar dipilih.", 0).show();
            return;
        }
        this.selectedGambar2Uris.clear();
        this.selectedGambar2Uris.addAll(persistAndFilterUris(listExtractUrisFromPickerResult));
        updateSelectedImageInfo(2);
        Toast.makeText(this, this.selectedGambar2Uris.size() + " gambar dipilih untuk Gambar 2.", 0).show();
        processImageUpload(2, false);
    }

    private void launchGambar1UploadPicker() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        this.gambar1UploadPickerLauncher.launch(Intent.createChooser(intent, "Pilih Gambar 1 dari Galeri/Foto"));
    }

    private void launchGambar2UploadPicker() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        this.gambar2UploadPickerLauncher.launch(Intent.createChooser(intent, "Pilih Gambar 2 dari Galeri/Foto"));
    }

    private List<Uri> extractUrisFromPickerResult(Intent intent) {
        ArrayList arrayList = new ArrayList();
        if (intent == null) {
            return arrayList;
        }
        Uri data = intent.getData();
        if (data != null) {
            arrayList.add(data);
        }
        ClipData clipData = intent.getClipData();
        if (clipData != null) {
            for (int i = 0; i < clipData.getItemCount(); i++) {
                Uri uri = clipData.getItemAt(i).getUri();
                if (uri != null) {
                    arrayList.add(uri);
                }
            }
        }
        return arrayList;
    }

    private List<Uri> persistAndFilterUris(List<Uri> list) {
        ArrayList arrayList = new ArrayList();
        for (Uri uri : list) {
            if (uri != null) {
                try {
                    getContentResolver().takePersistableUriPermission(uri, 1);
                } catch (SecurityException unused) {
                }
                arrayList.add(uri);
            }
        }
        return arrayList;
    }

    private void setupScrollableEditors() {
        configureScrollableEditor(this.editJudul);
        configureScrollableEditor(this.editGambar1);
        configureScrollableEditor(this.editGambar2);
        configureScrollableEditor(this.editLokasi);
        configureLineNumberView(this.textJudulNumbers);
        configureLineNumberView(this.textGambar1Numbers);
        configureLineNumberView(this.textGambar2Numbers);
        configureLineNumberView(this.textLokasiNumbers);
        configureCopyableField(this.editJudul);
        configureCopyableField(this.editHarga);
        configureCopyableField(this.editKategoriTerpilih);
        configureCopyableField(this.editDeskripsi);
        configureCopyableField(this.editGambar1);
        configureCopyableField(this.editGambar2);
        configureCopyableField(this.editGambar1Duplicate);
        configureCopyableField(this.editLokasi);
        configureCopyableField(this.editHashtag);
        configureCopyableField(this.editDelay);
    }

    private void configureScrollableEditor(final EditText editText) {
        editText.setHorizontallyScrolling(true);
        editText.setHorizontalScrollBarEnabled(true);
        editText.setVerticalScrollBarEnabled(true);
        editText.setMovementMethod(new ScrollingMovementMethod());
        editText.setSingleLine(false);
        editText.setMaxLines(Integer.MAX_VALUE);
        editText.setTypeface(Typeface.MONOSPACE);
        editText.setTextSize(2, 16.0f);
        editText.setLineSpacing(0.0f, 1.45f);
        editText.setIncludeFontPadding(false);
        editText.setPadding(0, dpToPx(2), 0, dpToPx(2));
        editText.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda16
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i, int i2, int i3, int i4) {
                this.f$0.lambda$configureScrollableEditor$28(editText, view, i, i2, i3, i4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$configureScrollableEditor$28(EditText editText, View view, int i, int i2, int i3, int i4) {
        TextView lineNumberView = getLineNumberView(editText);
        if (lineNumberView != null) {
            lineNumberView.setScrollY(i2);
        }
    }

    private void configureCopyableField(EditText editText) {
        editText.setTextIsSelectable(true);
        editText.setLongClickable(true);
        editText.setFocusableInTouchMode(true);
        editText.setOnTouchListener(new View.OnTouchListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda51
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return DataPostingActivity.lambda$configureCopyableField$29(view, motionEvent);
            }
        });
    }

    static /* synthetic */ boolean lambda$configureCopyableField$29(View view, MotionEvent motionEvent) {
        ViewParent parent = view.getParent();
        if (parent != null) {
            if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
                parent.requestDisallowInterceptTouchEvent(true);
            } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                parent.requestDisallowInterceptTouchEvent(false);
            }
        }
        return false;
    }

    private void configureLineNumberView(TextView textView) {
        textView.setTypeface(Typeface.MONOSPACE);
        textView.setTextSize(2, 16.0f);
        textView.setLineSpacing(0.0f, 1.45f);
        textView.setIncludeFontPadding(false);
        textView.setPadding(0, dpToPx(2), dpToPx(8), dpToPx(2));
    }

    private void setupLineCounters() {
        attachLineCounter(this.editJudul, this.textJudulCount);
        attachLineCounter(this.editGambar1, this.textGambar1Count);
        attachLineCounter(this.editGambar2, this.textGambar2Count);
        attachLineCounter(this.editLokasi, this.textLokasiCount);
        updateLineCount(this.editJudul, this.textJudulCount);
        updateLineCount(this.editGambar1, this.textGambar1Count);
        updateLineCount(this.editGambar2, this.textGambar2Count);
        updateLineCount(this.editLokasi, this.textLokasiCount);
    }

    private void attachLineCounter(final EditText editText, final TextView textView) {
        editText.addTextChangedListener(new TextWatcher() { // from class: com.robotta.bot.DataPostingActivity.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                DataPostingActivity.this.updateLineCount(editText, textView);
                if (editText == DataPostingActivity.this.editJudul) {
                    DataPostingActivity.this.maybeResetUnicodeState();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLineCount(EditText editText, TextView textView) {
        String string = editText.getText().toString();
        int length = string.isEmpty() ? 1 : string.split("\n", -1).length;
        textView.setText("Baris: " + length);
        TextView lineNumberView = getLineNumberView(editText);
        if (lineNumberView != null) {
            lineNumberView.setText(buildLineNumbers(length));
        }
    }

    private String buildLineNumbers(int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 1; i2 <= i; i2++) {
            if (i2 > 1) {
                sb.append('\n');
            }
            sb.append(i2);
        }
        return sb.toString();
    }

    private int dpToPx(int i) {
        return Math.round(i * getResources().getDisplayMetrics().density);
    }

    private void rebuildDataSlotOptions(String str) {
        List<String> allDataSlotValues = getAllDataSlotValues();
        ArrayList<String> arrayList = new ArrayList();
        for (String str2 : allDataSlotValues) {
            if (slotHasSavedContent(str2)) {
                arrayList.add(str2);
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add(str != null ? str : "data1");
        } else if (str != null && !arrayList.contains(str) && allDataSlotValues.contains(str)) {
            arrayList.add(0, str);
        }
        this.slotItems.clear();
        for (String str3 : arrayList) {
            this.slotItems.add(new SlotItem(str3, getSlotName(str3)));
        }
        this.suppressSlotListener = true;
        this.slotAdapter.notifyDataSetChanged();
        if (!arrayList.contains(str)) {
            str = (String) arrayList.get(0);
        }
        this.currentSlotValue = str;
        this.spinnerDataSlot.setSelection(findSlotIndex(str));
        this.suppressSlotListener = false;
        loadData();
    }

    private int findSlotIndex(String str) {
        for (int i = 0; i < this.slotItems.size(); i++) {
            if (this.slotItems.get(i).value.equals(str)) {
                return i;
            }
        }
        return 0;
    }

    private List<String> getAllDataSlotValues() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (int i = 1; i <= 10; i++) {
            linkedHashSet.add("data" + i);
        }
        try {
            JSONArray jSONArray = new JSONArray(this.prefs.getString(ADDED_SLOTS_KEY, "[]"));
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                linkedHashSet.add(jSONArray.optString(i2));
            }
        } catch (JSONException unused) {
        }
        return new ArrayList(linkedHashSet);
    }

    private boolean slotHasSavedContent(String str) {
        String string = this.prefs.getString(str, null);
        if (string == null || string.trim().isEmpty()) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(string);
            JSONArray jSONArrayNames = jSONObject.names();
            if (jSONArrayNames == null) {
                return false;
            }
            for (int i = 0; i < jSONArrayNames.length(); i++) {
                Object objOpt = jSONObject.opt(jSONArrayNames.optString(i));
                if (objOpt != null && objOpt.toString().trim().length() > 0) {
                    return true;
                }
            }
            return false;
        } catch (JSONException unused) {
            return string.trim().length() > 0;
        }
    }

    private String getSlotName(String str) {
        return this.prefs.getString(SLOT_NAME_PREFIX + str, str);
    }

    private void renameSlot() {
        final EditText editTextBuildDialogInput = buildDialogInput(getSlotName(this.currentSlotValue), 1);
        new AlertDialog.Builder(this).setTitle("Ubah Nama Slot").setView(editTextBuildDialogInput).setPositiveButton("Simpan", new DialogInterface.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda57
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$renameSlot$30(editTextBuildDialogInput, dialogInterface, i);
            }
        }).setNegativeButton("Batal", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$renameSlot$30(EditText editText, DialogInterface dialogInterface, int i) {
        String strTrim = editText.getText().toString().trim();
        if (strTrim.isEmpty()) {
            return;
        }
        this.prefs.edit().putString(SLOT_NAME_PREFIX + this.currentSlotValue, strTrim).commit();
        rebuildDataSlotOptions(this.currentSlotValue);
        Toast.makeText(this, "Nama slot diperbarui.", 0).show();
    }

    private void addSlot() {
        List<String> allDataSlotValues = getAllDataSlotValues();
        int i = 1;
        while (true) {
            if (!allDataSlotValues.contains("data" + i) || i > 1000) {
                break;
            } else {
                i++;
            }
        }
        if (i > 1000) {
            Toast.makeText(this, "Batas maksimum slot tercapai.", 0).show();
            return;
        }
        String str = "data" + i;
        try {
            JSONArray jSONArray = new JSONArray(this.prefs.getString(ADDED_SLOTS_KEY, "[]"));
            jSONArray.put(str);
            this.prefs.edit().putString(ADDED_SLOTS_KEY, jSONArray.toString()).putString(LAST_SELECTED_SLOT_KEY, str).commit();
        } catch (JSONException unused) {
        }
        rebuildDataSlotOptions(str);
        Toast.makeText(this, "Slot baru ditambahkan.", 0).show();
    }

    private boolean validateMultiNumberField(EditText editText, String str) {
        for (String str2 : editText.getText().toString().split("\n")) {
            String strTrim = str2.trim();
            if (!strTrim.isEmpty() && !strTrim.matches("^\\d+$")) {
                Toast.makeText(this, str + " hanya boleh angka per baris.", 0).show();
                return false;
            }
        }
        return true;
    }

    private void saveData() {
        if (persistCurrentForm()) {
            rebuildDataSlotOptions(this.currentSlotValue);
            Toast.makeText(this, "Data berhasil disimpan.", 0).show();
        }
    }

    private boolean persistCurrentForm() {
        if (!validateMultiNumberField(this.editHarga, "Harga") || !validateMultiNumberField(this.editDelay, "Delay")) {
            return false;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("judul", this.editJudul.getText().toString());
            jSONObject.put("harga", this.editHarga.getText().toString());
            jSONObject.put("kategori", this.spinnerKategori.getSelectedItem().toString());
            jSONObject.put("kategoriTerpilih", this.editKategoriTerpilih.getText().toString());
            jSONObject.put("kondisi", String.valueOf(this.spinnerKondisi.getSelectedItem()));
            jSONObject.put("deskripsi", this.editDeskripsi.getText().toString());
            jSONObject.put("gambar1", this.editGambar1.getText().toString());
            jSONObject.put("gambar2", this.editGambar2.getText().toString());
            jSONObject.put("lokasi", this.editLokasi.getText().toString());
            jSONObject.put("hashtag", this.editHashtag.getText().toString());
            jSONObject.put("delay", this.editDelay.getText().toString());
            jSONObject.put("gambar1Duplicate", this.editGambar1Duplicate.getText().toString());
            jSONObject.put("randomGambar1", this.checkRandomGambar1.isChecked());
            jSONObject.put("randomLokasi", this.checkRandomLokasi.isChecked());
            jSONObject.put("localGambar1Uris", toJsonArray(this.selectedGambar1Uris));
            jSONObject.put("localGambar2Uris", toJsonArray(this.selectedGambar2Uris));
        } catch (JSONException unused) {
        }
        if (!this.prefs.edit().putString(this.currentSlotValue, jSONObject.toString()).commit()) {
            Toast.makeText(this, "Data gagal disimpan.", 0).show();
            return false;
        }
        setResult(-1);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadData() {
        clearFormFields();
        selectKategori("Mebel");
        selectKondisi("Baru");
        this.editDelay.setText("25");
        this.editGambar1Duplicate.setText("1");
        String string = this.prefs.getString(this.currentSlotValue, null);
        if (string != null && !string.trim().isEmpty()) {
            try {
                JSONObject jSONObject = new JSONObject(string);
                this.editJudul.setText(jSONObject.optString("judul", ""));
                this.editHarga.setText(jSONObject.optString("harga", ""));
                selectKategori(jSONObject.optString("kategori", "Mebel"));
                this.editKategoriTerpilih.setText(jSONObject.optString("kategoriTerpilih", ""));
                selectKondisi(jSONObject.optString("kondisi", "Baru"));
                this.editDeskripsi.setText(jSONObject.optString("deskripsi", ""));
                this.editGambar1.setText(jSONObject.optString("gambar1", ""));
                this.editGambar2.setText(jSONObject.optString("gambar2", ""));
                this.editLokasi.setText(jSONObject.optString("lokasi", ""));
                this.editHashtag.setText(jSONObject.optString("hashtag", ""));
                this.editDelay.setText(jSONObject.optString("delay", "25"));
                this.editGambar1Duplicate.setText(jSONObject.optString("gambar1Duplicate", "1"));
                this.checkRandomGambar1.setChecked(jSONObject.optBoolean("randomGambar1", false));
                this.checkRandomLokasi.setChecked(jSONObject.optBoolean("randomLokasi", false));
                restorePersistedUris(jSONObject.optJSONArray("localGambar1Uris"), this.selectedGambar1Uris);
                restorePersistedUris(jSONObject.optJSONArray("localGambar2Uris"), this.selectedGambar2Uris);
            } catch (JSONException unused) {
                Toast.makeText(this, "Data slot rusak dan tidak bisa dimuat.", 0).show();
            }
        }
        updateSelectedImageInfo(1);
        updateSelectedImageInfo(2);
        updateLineCount(this.editJudul, this.textJudulCount);
        updateLineCount(this.editGambar1, this.textGambar1Count);
        updateLineCount(this.editGambar2, this.textGambar2Count);
        updateLineCount(this.editLokasi, this.textLokasiCount);
    }

    private void clearFormFields() {
        this.editJudul.setText("");
        this.editHarga.setText("");
        this.editKategoriTerpilih.setText("");
        this.editDeskripsi.setText("");
        this.editGambar1.setText("");
        this.editGambar2.setText("");
        this.editLokasi.setText("");
        this.editHashtag.setText("");
        this.checkRandomGambar1.setChecked(false);
        this.checkRandomLokasi.setChecked(false);
        this.selectedGambar1Uris.clear();
        this.selectedGambar2Uris.clear();
        updateSelectedImageInfo(1);
        updateSelectedImageInfo(2);
        this.selectedLokasi.clear();
        this.originalJudulBeforeUnicode = "";
    }

    private JSONArray toJsonArray(List<Uri> list) {
        JSONArray jSONArray = new JSONArray();
        for (Uri uri : list) {
            if (uri != null) {
                jSONArray.put(uri.toString());
            }
        }
        return jSONArray;
    }

    private void restorePersistedUris(JSONArray jSONArray, List<Uri> list) {
        list.clear();
        if (jSONArray == null) {
            return;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            String strTrim = jSONArray.optString(i, "").trim();
            if (!strTrim.isEmpty()) {
                Uri uri = Uri.parse(strTrim);
                try {
                    getContentResolver().takePersistableUriPermission(uri, 1);
                } catch (SecurityException unused) {
                }
                list.add(uri);
            }
        }
    }

    private void selectKategori(String str) {
        int iIndexOf = KATEGORI_OPTIONS.indexOf(str);
        this.suppressKategoriListener = true;
        if (iIndexOf >= 0) {
            this.spinnerKategori.setSelection(iIndexOf);
        } else {
            this.spinnerKategori.setSelection(0);
        }
        this.suppressKategoriListener = false;
    }

    private void selectKondisi(String str) {
        int iIndexOf = KONDISI_OPTIONS.indexOf(str);
        if (iIndexOf >= 0) {
            this.spinnerKondisi.setSelection(iIndexOf);
        } else {
            this.spinnerKondisi.setSelection(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSelectedKategori() {
        String string = this.spinnerKategori.getSelectedItem().toString();
        String strTrim = this.editKategoriTerpilih.getText().toString().trim();
        ArrayList arrayList = new ArrayList();
        if (!strTrim.isEmpty()) {
            arrayList.addAll(Arrays.asList(strTrim.split("\n")));
        }
        if (arrayList.contains(string)) {
            return;
        }
        if (strTrim.isEmpty()) {
            this.editKategoriTerpilih.setText(string);
            return;
        }
        this.editKategoriTerpilih.append("\n" + string);
    }

    private void openAiDialog() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        int i = (int) (getResources().getDisplayMetrics().density * 16.0f);
        linearLayout.setPadding(i, i, i, i);
        final EditText editTextBuildDialogInput = buildDialogInput(this.prefs.getString(PREF_AI_PRODUCT, ""), 1);
        editTextBuildDialogInput.setHint("Nama produk");
        final EditText editTextBuildDialogInput2 = buildDialogInput(this.prefs.getString(PREF_AI_LINE_COUNT, "5"), 2);
        editTextBuildDialogInput2.setHint("Jumlah baris judul");
        final EditText editTextBuildDialogInput3 = buildDialogInput(this.prefs.getString(PREF_AI_PHONE, ""), 3);
        editTextBuildDialogInput3.setHint("Nomor HP/WhatsApp");
        final CheckBox checkBox = new CheckBox(this);
        checkBox.setText("Tambahkan nomor HP di akhir judul");
        checkBox.setChecked(this.prefs.getBoolean(PREF_AI_ADD_PHONE, true));
        final CheckBox checkBox2 = new CheckBox(this);
        checkBox2.setText("Kata Bombastis di depan judul");
        checkBox2.setChecked(this.prefs.getBoolean("pref_ai_bombastis", false));
        linearLayout.addView(editTextBuildDialogInput);
        linearLayout.addView(editTextBuildDialogInput2);
        linearLayout.addView(editTextBuildDialogInput3);
        linearLayout.addView(checkBox);
        linearLayout.addView(checkBox2);
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setTitle("Buat Konten AI").setView(linearLayout).setPositiveButton("Generate", (DialogInterface.OnClickListener) null).setNegativeButton("Batal", (DialogInterface.OnClickListener) null).create();
        alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda52
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f$0.lambda$openAiDialog$32(alertDialogCreate, editTextBuildDialogInput, editTextBuildDialogInput2, editTextBuildDialogInput3, checkBox, checkBox2, dialogInterface);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openAiDialog$32(final AlertDialog alertDialog, final EditText editText, final EditText editText2, final EditText editText3, final CheckBox checkBox, final CheckBox checkBox2, DialogInterface dialogInterface) {
        alertDialog.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$openAiDialog$31(editText, editText2, editText3, alertDialog, checkBox, checkBox2, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openAiDialog$31(EditText editText, EditText editText2, EditText editText3, AlertDialog alertDialog, CheckBox checkBox, CheckBox checkBox2, View view) {
        String strTrim = editText.getText().toString().trim();
        String strTrim2 = editText2.getText().toString().trim();
        String strTrim3 = editText3.getText().toString().trim();
        if (strTrim.isEmpty()) {
            editText.setError("Nama produk wajib diisi");
            return;
        }
        try {
            int iMax = Math.max(1, Integer.parseInt(strTrim2));
            alertDialog.dismiss();
            this.prefs.edit().putString(PREF_AI_PRODUCT, strTrim).putString(PREF_AI_LINE_COUNT, String.valueOf(iMax)).putString(PREF_AI_PHONE, strTrim3).putBoolean(PREF_AI_ADD_PHONE, checkBox.isChecked()).putBoolean("pref_ai_bombastis", checkBox2.isChecked()).apply();
            generateAiContent(strTrim, iMax, strTrim3, checkBox.isChecked(), checkBox2.isChecked());
        } catch (NumberFormatException unused) {
            editText2.setError("Jumlah baris tidak valid");
        }
    }

    private void generateAiContent(final String str, final int i, final String str2, final boolean z, final boolean z2) {
        showToast("Sedang menggenerate konten AI...");
        showAiOverlay();
        this.backgroundExecutor.execute(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda48
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$generateAiContent$36(i, str, z2, str2, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$generateAiContent$36(int i, String str, boolean z, String str2, boolean z2) throws Throwable {
        try {
            updateAiOverlayProgress(5, "Membuat judul produk...");
            List<String> listSplitNonEmptyLines = splitNonEmptyLines(makeGroqRequest("Kamu adalah asisten ahli SEO untuk e-commerce. Buatkan " + i + " variasi judul yang menarik dan menjual untuk produk \"" + str + "\" dalam Bahasa Indonesia.\nSetiap judul harus:\n1. SANGAT SINGKAT: maksimal 2-3 kata saja\n2. Mengandung kata kunci yang relevan\n3. Menarik perhatian pembeli\n4. Sesuai untuk marketplace\nPENTING: Berikan HANYA daftar judul, tanpa penomoran, tanpa tanda hubung, dan setiap judul di baris baru."));
            updateAiOverlayProgress(20, "Judul selesai ✓ Membuat deskripsi...");
            StringBuilder sb = new StringBuilder("Kamu adalah copywriter profesional. Buatkan deskripsi produk untuk \"");
            sb.append(str);
            sb.append("\" dalam Bahasa Indonesia dengan format berikut:\n1. Dua kalimat pembuka yang ringkas dan menarik tanpa emoticon\n2. Lalu 2-3 poin keunggulan menggunakan simbol ✅\n3. Setiap poin: ✅ Judul poin: deskripsi singkat\n4. Akhiri dengan 15-20 kata kunci relevan setelah beberapa baris kosong\nPENTING: Berikan HANYA deskripsi sesuai format.");
            String strMakeGroqRequest = makeGroqRequest(sb.toString());
            updateAiOverlayProgress(40, "Deskripsi selesai ✓ Membuat hashtag...");
            String strMakeGroqRequest2 = makeGroqRequest("Buatkan 6 kata kunci relevan untuk produk \"" + str + "\". Jawab hanya daftar kata kunci dipisahkan koma. Maksimal 6 kata kunci saja.");
            updateAiOverlayProgress(60, "Hashtag selesai ✓ Membuat harga...");
            String strMakeGroqRequest3 = makeGroqRequest("Berikan 5 kisaran harga wajar dalam angka Rupiah (tanpa titik, tanpa 'Rp', HANYA angka) untuk produk \"" + str + "\" yang dijual di marketplace Indonesia.\nSetiap baris satu angka harga saja.\nContoh format:\n150000\n175000\n200000\n225000\n250000\nPENTING: Berikan HANYA 5 baris angka, tanpa teks lain.");
            ArrayList arrayList = new ArrayList();
            for (String str3 : strMakeGroqRequest3.split("\n")) {
                String strReplaceAll = str3.trim().replaceAll("[^0-9]", "");
                if (!strReplaceAll.isEmpty()) {
                    arrayList.add(strReplaceAll);
                }
                if (arrayList.size() >= 5) {
                    break;
                }
            }
            updateAiOverlayProgress(80, "Harga selesai ✓ Membuat kategori...");
            StringBuilder sb2 = new StringBuilder();
            Iterator<String> it = KATEGORI_OPTIONS.iterator();
            while (it.hasNext()) {
                sb2.append(it.next());
                sb2.append(", ");
            }
            String strMakeGroqRequest4 = makeGroqRequest("Dari daftar kategori berikut: " + ((Object) sb2) + "\nPilih 5 kategori yang paling relevan untuk produk \"" + str + "\".\nJawab HANYA nama kategori, satu per baris, tanpa penomoran.");
            ArrayList arrayList2 = new ArrayList();
            for (String str4 : strMakeGroqRequest4.split("\n")) {
                String strReplaceAll2 = str4.trim().replaceAll("^[\\d.\\-]+\\s*", "");
                if (!strReplaceAll2.isEmpty() && KATEGORI_OPTIONS.contains(strReplaceAll2) && !arrayList2.contains(strReplaceAll2)) {
                    arrayList2.add(strReplaceAll2);
                }
                if (arrayList2.size() >= 5) {
                    break;
                }
            }
            updateAiOverlayProgress(95, "Kategori selesai ✓ Menerapkan hasil...");
            final ArrayList arrayList3 = new ArrayList();
            for (int i2 = 0; i2 < Math.min(i, listSplitNonEmptyLines.size()); i2++) {
                String str5 = listSplitNonEmptyLines.get(i2);
                if (z) {
                    String[] strArr = KATA_BOMBASTIS;
                    str5 = strArr[i2 % strArr.length] + " " + str5;
                }
                if (!str2.isEmpty() && z2) {
                    str5 = str5 + " WA. " + str2;
                }
                arrayList3.add(str5);
            }
            String strTrim = strMakeGroqRequest.trim();
            if (!str2.isEmpty()) {
                strTrim = "📞 WA. " + str2 + "\n\n" + strTrim;
            }
            final String strReplace = strTrim.replace("<br>", "").replace("<br/>", "").replace("<br />", "").replace(ProxyConfig.MATCH_ALL_SCHEMES, "");
            final String strNormalizeKeywordList = normalizeKeywordList(strMakeGroqRequest2);
            final String strJoinLines = joinLines(arrayList);
            final String strJoinLines2 = joinLines(arrayList2);
            this.mainHandler.post(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda59
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$generateAiContent$34(arrayList3, strReplace, strNormalizeKeywordList, strJoinLines, strJoinLines2);
                }
            });
        } catch (Exception e) {
            lambda$generateAiContent$33();
            this.mainHandler.post(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda60
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$generateAiContent$35(e);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$generateAiContent$34(List list, String str, String str2, String str3, String str4) {
        this.editJudul.setText(joinLines(list));
        this.editDeskripsi.setText(str);
        this.editHashtag.setText(str2);
        if (!str3.isEmpty()) {
            this.editHarga.setText(str3);
        }
        if (!str4.isEmpty()) {
            this.editKategoriTerpilih.setText(str4);
        }
        updateLineCount(this.editJudul, this.textJudulCount);
        this.originalJudulBeforeUnicode = "";
        updateAiOverlayProgress(100, "Selesai! ✓");
        this.mainHandler.postDelayed(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda34
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$generateAiContent$33();
            }
        }, 500L);
        Toast.makeText(this, "Konten AI berhasil dibuat.", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$generateAiContent$35(Exception exc) {
        Toast.makeText(this, "Generate AI gagal: " + exc.getMessage(), 1).show();
    }

    /* JADX WARN: Code duplicated, block: B:70:0x0162 A[PHI: r5 r7
      0x0162: PHI (r5v7 'e' java.io.IOException) = (r5v5 'e' java.io.IOException), (r5v8 'e' java.io.IOException) binds: [B:69:0x0160, B:76:0x0171] A[DONT_GENERATE, DONT_INLINE]
      0x0162: PHI (r7v3 java.net.HttpURLConnection) = (r7v2 java.net.HttpURLConnection), (r7v4 java.net.HttpURLConnection) binds: [B:69:0x0160, B:76:0x0171] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:82:0x017c  */
    /* JADX WARN: Code duplicated, block: B:86:0x0184  */
    /* JADX WARN: Code duplicated, block: B:87:0x0189  */
    private String makeGroqRequest(String str) throws Throwable {
        String message;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        HttpURLConnection httpURLConnection2 = null;
        IOException e = null;
        int i = 1;
        while (true) {
            if (i <= 5) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("model", "openai/gpt-oss-120b");
                    jSONObject.put("stream", false);
                    jSONObject.put("max_tokens", 4096);
                    jSONObject.put("temperature", 1);
                    jSONObject.put("top_p", 1);
                    jSONObject.put("frequency_penalty", 0);
                    jSONObject.put("presence_penalty", 0);
                    JSONArray jSONArray = new JSONArray();
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("role", "user");
                    jSONObject2.put("content", str);
                    jSONArray.put(jSONObject2);
                    jSONObject.put("messages", jSONArray);
                    httpURLConnection = (HttpURLConnection) new URL(GROQ_API_URL).openConnection();
                    try {
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setConnectTimeout(30000);
                        httpURLConnection.setReadTimeout(45000);
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setRequestProperty("Authorization", "Bearer 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");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.setRequestProperty("Accept", "application/json");
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        try {
                            outputStream.write(jSONObject.toString().getBytes(StandardCharsets.UTF_8));
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            int responseCode = httpURLConnection.getResponseCode();
                            if (responseCode >= 400) {
                                inputStream = httpURLConnection.getErrorStream();
                            } else {
                                inputStream = httpURLConnection.getInputStream();
                            }
                            if (inputStream == null) {
                                throw new IOException("Respons AI kosong");
                            }
                            try {
                                String streamFully = readStreamFully(inputStream);
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                if (responseCode == 429 || responseCode == 503) {
                                    throw new RetryableException("AI sedang sibuk, mencoba ulang", responseCode);
                                }
                                if (responseCode >= 400) {
                                    throw new IOException("API Error " + responseCode + ": " + streamFully);
                                }
                                JSONArray jSONArrayOptJSONArray = new JSONObject(streamFully).optJSONArray("choices");
                                if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() == 0) {
                                    throw new IOException("AI tidak mengembalikan pilihan konten");
                                }
                                String strTrim = jSONArrayOptJSONArray.getJSONObject(0).getJSONObject("message").optString("content", "").trim();
                                if (strTrim.isEmpty()) {
                                    throw new IOException("Konten AI kosong");
                                }
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                }
                                return strTrim;
                            } catch (Throwable th) {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (Throwable th4) {
                                    th3.addSuppressed(th4);
                                }
                            }
                            throw th3;
                        }
                    } catch (RetryableException e2) {
                        e = e2;
                        sleepBeforeRetry(i);
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        i++;
                    } catch (IOException e3) {
                        e = e3;
                        if (i < 5) {
                            try {
                                if (isTransientAiError(e)) {
                                    sleepBeforeRetry(i);
                                    if (httpURLConnection != null) {
                                        httpURLConnection.disconnect();
                                    }
                                    i++;
                                }
                            } catch (Throwable th5) {
                                th = th5;
                                httpURLConnection2 = httpURLConnection;
                                if (httpURLConnection2 != null) {
                                    httpURLConnection2.disconnect();
                                }
                                throw th;
                            }
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        if (e != null) {
                            message = e.getMessage();
                        } else {
                            message = "Gagal menghubungi AI";
                        }
                        throw new IOException(message);
                    }
                } catch (RetryableException e4) {
                    e = e4;
                    httpURLConnection = null;
                } catch (IOException e5) {
                    e = e5;
                    httpURLConnection = null;
                } catch (Throwable th6) {
                    th = th6;
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    throw th;
                }
            }
            if (e != null) {
                message = e.getMessage();
            } else {
                message = "Gagal menghubungi AI";
            }
            throw new IOException(message);
            i++;
        }
    }

    private List<String> splitNonEmptyLines(String str) {
        ArrayList arrayList = new ArrayList();
        for (String str2 : str.split("\n")) {
            String strTrim = str2.trim();
            if (!strTrim.isEmpty()) {
                arrayList.add(strTrim);
            }
        }
        return arrayList;
    }

    private void updateSelectedImageInfo(int i) {
        String str;
        List<Uri> list = i == 1 ? this.selectedGambar1Uris : this.selectedGambar2Uris;
        TextView textView = i == 1 ? this.textGambar1ImportInfo : this.textGambar2ImportInfo;
        if (list.isEmpty()) {
            str = "JP: 0 gambar dipilih";
        } else {
            str = "JP: " + list.size() + " gambar dipilih";
        }
        textView.setText(str);
    }

    private void updateImageProgress(int i, final boolean z, final String str) {
        final LinearLayout linearLayout = i == 1 ? this.layoutGambar1Progress : this.layoutGambar2Progress;
        final TextView textView = i == 1 ? this.textGambar1Progress : this.textGambar2Progress;
        final ProgressBar progressBar = i == 1 ? this.progressGambar1 : this.progressGambar2;
        this.mainHandler.post(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda32
            @Override // java.lang.Runnable
            public final void run() {
                DataPostingActivity.lambda$updateImageProgress$37(linearLayout, z, progressBar, textView, str);
            }
        });
    }

    static /* synthetic */ void lambda$updateImageProgress$37(LinearLayout linearLayout, boolean z, ProgressBar progressBar, TextView textView, String str) {
        linearLayout.setVisibility(z ? 0 : 8);
        progressBar.setIndeterminate(z);
        textView.setText(str);
    }

    private void processImageUpload(final int i, final boolean z) {
        int iMax = 1;
        final List<Uri> list = i == 1 ? this.selectedGambar1Uris : this.selectedGambar2Uris;
        if (list.isEmpty()) {
            Toast.makeText(this, "Pilih gambar terlebih dahulu.", 0).show();
            return;
        }
        Future<?> future = this.currentUploadTask;
        if (future != null && !future.isDone()) {
            Toast.makeText(this, "Masih ada proses upload berjalan.", 0).show();
            return;
        }
        if (i == 1) {
            try {
                iMax = Math.max(1, Integer.parseInt(this.editGambar1Duplicate.getText().toString().trim()));
            } catch (NumberFormatException unused) {
            }
        }
        final int i2 = iMax;
        this.cancelUploadGambar1.set(false);
        this.cancelUploadGambar2.set(false);
        Toast.makeText(this, "Memproses upload gambar...", 0).show();
        this.currentUploadTask = this.backgroundExecutor.submit(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$processImageUpload$42(z, list, i2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Bottom block not found for handler: all -> 0x0177 */
    /* JADX WARN: Not initialized variable reg: 14, insn: 0x016f: MOVE (r3 I:??[OBJECT, ARRAY]) = (r14 I:??[OBJECT, ARRAY]), block:B:63:0x016f */
    /* JADX WARN: Not initialized variable reg: 14, insn: 0x0173: MOVE (r3 I:??[OBJECT, ARRAY]) = (r14 I:??[OBJECT, ARRAY]), block:B:65:0x0173 */
    /* JADX WARN: Not initialized variable reg: 14, insn: 0x0175: MOVE (r3 I:??[OBJECT, ARRAY]) = (r14 I:??[OBJECT, ARRAY]), block:B:66:0x0175 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$processImageUpload$42(boolean r24, java.util.List r25, int r26, final int r27) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 522
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.robotta.bot.DataPostingActivity.lambda$processImageUpload$42(boolean, java.util.List, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$processImageUpload$38(int i, byte[] bArr, String str, AtomicInteger atomicInteger, List list, AtomicInteger atomicInteger2, int i2) throws Exception {
        throwIfUploadCancelled(i);
        try {
            String strUploadImageToServer = uploadImageToServer(bArr, str);
            if (strUploadImageToServer != null && !strUploadImageToServer.trim().isEmpty()) {
                atomicInteger.incrementAndGet();
            }
            return strUploadImageToServer;
        } catch (Exception e) {
            String message = e.getMessage();
            if (message == null || message.trim().isEmpty()) {
                message = "Upload gagal tanpa pesan error.";
            }
            list.add(str + ": " + message);
            return null;
        } finally {
            updateImageProgress(i, true, "Upload " + atomicInteger2.incrementAndGet() + "/" + i2 + " | Berhasil " + atomicInteger.get() + " | Gagal " + list.size());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processImageUpload$39(List list, int i, AtomicInteger atomicInteger, List list2) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null && !str.trim().isEmpty()) {
                arrayList.add(str.trim());
            }
        }
        String strJoinLines = joinLines(arrayList);
        if (i == 1) {
            this.editGambar1.setText(strJoinLines);
        } else {
            this.editGambar2.setText(strJoinLines);
        }
        if (atomicInteger.get() > 0 && list2.isEmpty()) {
            Toast.makeText(this, "Upload " + atomicInteger.get() + " gambar selesai!", 0).show();
            return;
        }
        if (atomicInteger.get() > 0) {
            Toast.makeText(this, "Upload selesai sebagian. Berhasil " + atomicInteger.get() + ", gagal " + list2.size() + ".", 1).show();
            return;
        }
        StringBuilder sb = new StringBuilder("Upload gambar gagal: ");
        sb.append(list2.isEmpty() ? "Tidak ada gambar yang berhasil diupload." : (String) list2.get(0));
        Toast.makeText(this, sb.toString(), 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processImageUpload$40() {
        Toast.makeText(this, "Upload gambar dibatalkan.", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processImageUpload$41(String str) {
        Toast.makeText(this, "Upload gambar gagal: " + str, 1).show();
    }

    private void requestCancelUpload(int i) {
        Future<?> future = this.currentUploadTask;
        if (future == null || future.isDone()) {
            Toast.makeText(this, "Tidak ada proses upload aktif.", 0).show();
            return;
        }
        if (i == 1) {
            this.cancelUploadGambar1.set(true);
        } else {
            this.cancelUploadGambar2.set(true);
        }
        synchronized (this.currentUploadFutures) {
            for (Future<String> future2 : this.currentUploadFutures) {
                if (future2 != null) {
                    future2.cancel(true);
                }
            }
        }
        ExecutorService executorService = this.currentUploadPool;
        if (executorService != null) {
            executorService.shutdownNow();
        }
        disconnectActiveUploadConnections();
        updateImageProgress(i, true, "Membatalkan proses...");
    }

    private void throwIfUploadCancelled(int i) throws UploadCancelledException {
        if ((i == 1 && this.cancelUploadGambar1.get()) || ((i == 2 && this.cancelUploadGambar2.get()) || Thread.currentThread().isInterrupted())) {
            throw new UploadCancelledException();
        }
    }

    private static final class UploadCancelledException extends Exception {
        private UploadCancelledException() {
        }
    }

    private byte[] prepareImageBytes(Uri uri, boolean z) throws IOException {
        InputStream inputStreamOpenInputStream = getContentResolver().openInputStream(uri);
        try {
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream);
            if (bitmapDecodeStream == null) {
                throw new IOException("Bitmap tidak bisa dibaca");
            }
            if (z) {
                bitmapDecodeStream = addMarketplaceFrame(bitmapDecodeStream);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmapDecodeStream.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (inputStreamOpenInputStream != null) {
                inputStreamOpenInputStream.close();
            }
            return byteArray;
        } catch (Throwable th) {
            if (inputStreamOpenInputStream != null) {
                try {
                    inputStreamOpenInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private Bitmap addMarketplaceFrame(Bitmap bitmap) throws IOException {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth() + 240, bitmap.getHeight() + 240, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        addFrame(canvas, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight(), DEFAULT_FRAME_THICKNESS);
        float f = DEFAULT_FRAME_THICKNESS;
        canvas.drawBitmap(bitmap, f, f, (Paint) null);
        addRandomPng(canvas, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
        return bitmapCreateBitmap;
    }

    private void ensureImageAssetsLoaded(boolean z) throws IOException {
        if (z) {
            if (this.availableFrameImageUrls.isEmpty()) {
                this.availableFrameImageUrls.addAll(loadUrlsFromGithub(GITHUB_FRAME_IMAGE_URL));
            }
            if (this.availablePngUrls.isEmpty()) {
                this.availablePngUrls.addAll(loadUrlsFromGithub(GITHUB_PNG_IMAGE_URL));
            }
        }
    }

    private List<String> loadUrlsFromGithub(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setConnectTimeout(12000);
        httpURLConnection.setReadTimeout(AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH);
        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            try {
                ArrayList arrayList = new ArrayList();
                for (String str2 : readStreamFully(inputStream).split("\n")) {
                    String strTrim = str2.trim();
                    if (!strTrim.isEmpty()) {
                        arrayList.add(strTrim);
                    }
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                httpURLConnection.disconnect();
                return arrayList;
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            httpURLConnection.disconnect();
            throw th3;
        }
    }

    private void addFrame(Canvas canvas, int i, int i2, int i3) throws IOException {
        Paint paint = new Paint(1);
        if (!this.availableFrameImageUrls.isEmpty()) {
            List<String> list = this.availableFrameImageUrls;
            try {
                paint.setShader(new BitmapShader(downloadBitmapFromUrl(list.get(this.random.nextInt(list.size()))), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            } catch (IOException unused) {
                paint.setShader(null);
                paint.setColor(getRandomColor());
            }
        } else {
            paint.setColor(getRandomColor());
        }
        float f = i;
        float f2 = i3;
        canvas.drawRect(0.0f, 0.0f, f, f2, paint);
        float f3 = i2 - i3;
        float f4 = i2;
        canvas.drawRect(0.0f, f3, f, f4, paint);
        canvas.drawRect(0.0f, 0.0f, f2, f4, paint);
        canvas.drawRect(i - i3, 0.0f, f, f4, paint);
    }

    private void addRandomPng(Canvas canvas, int i, int i2) {
        if (this.availablePngUrls.isEmpty()) {
            return;
        }
        try {
            List<String> list = this.availablePngUrls;
            Bitmap bitmapDownloadBitmapFromUrl = downloadBitmapFromUrl(list.get(this.random.nextInt(list.size())));
            if (bitmapDownloadBitmapFromUrl == null) {
                return;
            }
            float width = 350.0f / (bitmapDownloadBitmapFromUrl.getWidth() / bitmapDownloadBitmapFromUrl.getHeight());
            float f = i / 3.0f;
            float f2 = i2 / 3.0f;
            float f3 = f * 2.0f;
            float f4 = 2.0f * f2;
            float[] fArr = new float[][]{new float[]{0.0f, 0.0f, f, f2}, new float[]{f3, 0.0f, f, f2}, new float[]{0.0f, f4, f, f2}, new float[]{f3, f4, f, f2}, new float[]{0.0f, f2, f, f2}, new float[]{f3, f2, f, f2}, new float[]{f, 0.0f, f, f2}, new float[]{f, f4, f, f2}}[this.random.nextInt(8)];
            float fMax = fArr[0] + (Math.max(0.0f, fArr[2] - 350.0f) * this.random.nextFloat());
            float fMax2 = fArr[1] + (Math.max(0.0f, fArr[3] - width) * this.random.nextFloat());
            canvas.drawBitmap(bitmapDownloadBitmapFromUrl, (Rect) null, new RectF(fMax, fMax2, 350.0f + fMax, width + fMax2), (Paint) null);
        } catch (Exception unused) {
        }
    }

    private Bitmap downloadBitmapFromUrl(String str) throws IOException {
        Bitmap bitmap = this.remoteBitmapCache.get(str);
        if (bitmap != null && !bitmap.isRecycled()) {
            return bitmap;
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setConnectTimeout(12000);
        httpURLConnection.setReadTimeout(AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH);
        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            try {
                Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStream);
                if (bitmapDecodeStream == null) {
                    throw new IOException("Bitmap URL tidak valid");
                }
                this.remoteBitmapCache.put(str, bitmapDecodeStream);
                if (inputStream != null) {
                    inputStream.close();
                }
                httpURLConnection.disconnect();
                return bitmapDecodeStream;
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            httpURLConnection.disconnect();
            throw th3;
        }
    }

    private int getRandomColor() {
        return Color.rgb(this.random.nextInt(255), this.random.nextInt(255), this.random.nextInt(255));
    }

    private String buildUploadFilename(Uri uri, boolean z, int i) {
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment == null || lastPathSegment.trim().isEmpty()) {
            lastPathSegment = "image";
        }
        String strReplaceAll = lastPathSegment.replaceAll("\\.[^.]+$", "");
        long jCurrentTimeMillis = System.currentTimeMillis();
        int iNextInt = this.random.nextInt(1000);
        return strReplaceAll + (z ? "" : "_noframe") + "_" + jCurrentTimeMillis + "_" + iNextInt + "_" + i + ".jpg";
    }

    private String uploadImageToServer(byte[] bArr, String str) throws IOException {
        String streamFully;
        InputStream inputStream;
        String str2 = "image=" + URLEncoder.encode(Base64.encodeToString(bArr, 2), "UTF-8") + "&mimetype=" + URLEncoder.encode("image/jpeg", "UTF-8") + "&filename=" + URLEncoder.encode(str, "UTF-8");
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(GOOGLE_SCRIPT_URL).openConnection();
        registerActiveUploadConnection(httpURLConnection);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setConnectTimeout(AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        OutputStream outputStream = httpURLConnection.getOutputStream();
        try {
            outputStream.write(str2.getBytes(StandardCharsets.UTF_8));
            if (outputStream != null) {
                outputStream.close();
            }
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode >= 300 && responseCode < 400) {
                String headerField = httpURLConnection.getHeaderField("Location");
                unregisterActiveUploadConnection(httpURLConnection);
                httpURLConnection.disconnect();
                if (headerField != null && !headerField.isEmpty()) {
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(headerField).openConnection();
                    registerActiveUploadConnection(httpURLConnection2);
                    httpURLConnection2.setConnectTimeout(AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH);
                    httpURLConnection2.setReadTimeout(60000);
                    httpURLConnection2.setInstanceFollowRedirects(true);
                    httpURLConnection2.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
                    try {
                        if (httpURLConnection2.getResponseCode() >= 400) {
                            inputStream = httpURLConnection2.getErrorStream();
                        } else {
                            inputStream = httpURLConnection2.getInputStream();
                        }
                        if (inputStream == null) {
                            throw new IOException("Respons redirect upload kosong");
                        }
                        try {
                            String uploadResponse = parseUploadResponse(readStreamFully(inputStream));
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            unregisterActiveUploadConnection(httpURLConnection2);
                            httpURLConnection2.disconnect();
                            return uploadResponse;
                        } catch (Throwable th) {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        unregisterActiveUploadConnection(httpURLConnection2);
                        httpURLConnection2.disconnect();
                        throw th3;
                    }
                }
                throw new IOException("Redirect URL kosong dari Google Script");
            }
            if (responseCode >= 400) {
                InputStream errorStream = httpURLConnection.getErrorStream();
                if (errorStream != null) {
                    try {
                        streamFully = readStreamFully(errorStream);
                        if (errorStream != null) {
                            errorStream.close();
                        }
                    } catch (Throwable th4) {
                        if (errorStream != null) {
                            try {
                                errorStream.close();
                            } catch (Throwable th5) {
                                th4.addSuppressed(th5);
                            }
                        }
                        throw th4;
                    }
                } else {
                    streamFully = "";
                }
                unregisterActiveUploadConnection(httpURLConnection);
                httpURLConnection.disconnect();
                throw new IOException("Upload gagal HTTP " + responseCode + ": " + extractReadableError(streamFully));
            }
            InputStream inputStream2 = httpURLConnection.getInputStream();
            try {
                if (inputStream2 == null) {
                    unregisterActiveUploadConnection(httpURLConnection);
                    httpURLConnection.disconnect();
                    throw new IOException("Respons upload kosong");
                }
                try {
                    String uploadResponse2 = parseUploadResponse(readStreamFully(inputStream2));
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    unregisterActiveUploadConnection(httpURLConnection);
                    httpURLConnection.disconnect();
                    return uploadResponse2;
                } catch (Throwable th6) {
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (Throwable th7) {
                            th6.addSuppressed(th7);
                        }
                    }
                    throw th6;
                }
            } catch (Throwable th8) {
                unregisterActiveUploadConnection(httpURLConnection);
                httpURLConnection.disconnect();
                throw th8;
            }
        } catch (Throwable th9) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable th10) {
                    th9.addSuppressed(th10);
                }
            }
            throw th9;
        }
    }

    private String parseUploadResponse(String str) throws IOException {
        String strTrim = str == null ? "" : str.trim();
        if (strTrim.isEmpty()) {
            throw new IOException("Respons upload kosong.");
        }
        if (isValidHttpUrl(strTrim)) {
            return strTrim;
        }
        if (strTrim.startsWith("{")) {
            try {
                JSONObject jSONObject = new JSONObject(strTrim);
                String strOptString = jSONObject.optString(LocalHtmlViewerActivity.EXTRA_URL, jSONObject.optString("link", ""));
                if (strOptString.isEmpty() && jSONObject.has("data")) {
                    Object objOpt = jSONObject.opt("data");
                    if (objOpt instanceof JSONObject) {
                        strOptString = ((JSONObject) objOpt).optString(LocalHtmlViewerActivity.EXTRA_URL, "");
                    }
                }
                if (isValidHttpUrl(strOptString)) {
                    return strOptString.trim();
                }
                String strOptString2 = jSONObject.optString("error", jSONObject.optString("message", jSONObject.optString("detail", "")));
                if (!strOptString2.trim().isEmpty()) {
                    throw new IOException(strOptString2.trim());
                }
            } catch (JSONException unused) {
            }
        }
        throw new IOException(extractReadableError(strTrim));
    }

    private boolean isValidHttpUrl(String str) {
        try {
            String protocol = new URL(str == null ? "" : str.trim()).getProtocol();
            return ProxyConfig.MATCH_HTTP.equalsIgnoreCase(protocol) || ProxyConfig.MATCH_HTTPS.equalsIgnoreCase(protocol);
        } catch (Exception unused) {
            return false;
        }
    }

    private String extractReadableError(String str) {
        String strTrim = str == null ? "" : str.trim();
        if (strTrim.isEmpty()) {
            return "Respons upload kosong.";
        }
        if (!strTrim.startsWith("<!DOCTYPE html") && !strTrim.startsWith("<html")) {
            return strTrim;
        }
        Matcher matcher = Pattern.compile("Exception:\\s*(.+?)(?:\\s*\\(|$)", 2).matcher(strTrim.replaceAll("(?is)<script.*?</script>", " ").replaceAll("(?is)<style.*?</style>", " ").replaceAll("(?is)<[^>]+>", " ").replaceAll("\\s+", " ").trim());
        if (!matcher.find()) {
            return "Upload gagal karena server mengembalikan halaman error, bukan link gambar.";
        }
        return "Google Script error: " + matcher.group(1).trim();
    }

    private void registerActiveUploadConnection(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            this.activeUploadConnections.add(httpURLConnection);
        }
    }

    private void unregisterActiveUploadConnection(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            this.activeUploadConnections.remove(httpURLConnection);
        }
    }

    private void disconnectActiveUploadConnections() {
        synchronized (this.activeUploadConnections) {
            Iterator it = new ArrayList(this.activeUploadConnections).iterator();
            while (it.hasNext()) {
                try {
                    ((HttpURLConnection) it.next()).disconnect();
                } catch (Exception unused) {
                }
            }
            this.activeUploadConnections.clear();
        }
    }

    private String joinLines(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append('\n');
            }
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    private void convertJudulToUnicode() {
        String string = this.editJudul.getText().toString();
        if (string.trim().isEmpty()) {
            Toast.makeText(this, "Silakan isi judul terlebih dahulu.", 0).show();
            return;
        }
        if (this.originalJudulBeforeUnicode.isEmpty()) {
            this.originalJudulBeforeUnicode = string;
        }
        this.editJudul.setText(applyUnicodeStyle(string, String.valueOf(this.spinnerUnicodeStyle.getSelectedItem())));
        updateLineCount(this.editJudul, this.textJudulCount);
        Toast.makeText(this, "Judul berhasil dikonversi ke Unicode.", 0).show();
    }

    private void resetUnicode() {
        if (this.originalJudulBeforeUnicode.isEmpty()) {
            Toast.makeText(this, "Tidak ada teks asli yang tersimpan.", 0).show();
            return;
        }
        this.editJudul.setText(this.originalJudulBeforeUnicode);
        updateLineCount(this.editJudul, this.textJudulCount);
        this.originalJudulBeforeUnicode = "";
        Toast.makeText(this, "Font judul dikembalikan ke semula.", 0).show();
    }

    private String applyUnicodeStyle(String str, String str2) {
        int iIndexOf;
        Map<Character, String> map = UNICODE_STYLE_MAP.get(str2);
        if (map == null) {
            return str;
        }
        ArrayList arrayList = new ArrayList();
        Matcher matcher = Pattern.compile("(WA\\.?\\s*\\d{3}[-\\s]?\\d{3}[-\\s]?\\d{3,4}|\\d{4}[-\\s]?\\d{4}[-\\s]?\\d{3,4}|\\d{3}[-\\s]?\\d{3}[-\\s]?\\d{3,4})", 2).matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (matcher.find()) {
            StringBuilder sb = new StringBuilder("__PHONE_");
            int i2 = i + 1;
            sb.append(i);
            sb.append("__");
            String string = sb.toString();
            arrayList.add(matcher.group());
            matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(string));
            i = i2;
        }
        matcher.appendTail(stringBuffer);
        String string2 = stringBuffer.toString();
        StringBuilder sb2 = new StringBuilder();
        Random random = new Random();
        int i3 = 0;
        while (i3 < string2.length()) {
            if (string2.startsWith("__PHONE_", i3) && (iIndexOf = string2.indexOf("__", i3 + 8)) > i3) {
                sb2.append((CharSequence) string2, i3, iIndexOf + 2);
                i3 = iIndexOf + 1;
            } else {
                char cCharAt = string2.charAt(i3);
                if ("mixed".equals(str2)) {
                    sb2.append(mapMixedChar(cCharAt, random));
                } else {
                    sb2.append((String) map.getOrDefault(Character.valueOf(cCharAt), String.valueOf(cCharAt)));
                }
            }
            i3++;
        }
        String string3 = sb2.toString();
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            string3 = string3.replace("__PHONE_" + i4 + "__", (CharSequence) arrayList.get(i4));
        }
        return string3;
    }

    private String mapMixedChar(char c, Random random) {
        Map<Character, String> map;
        ArrayList arrayList = new ArrayList();
        for (String str : UNICODE_STYLES) {
            if (!"mixed".equals(str) && (map = UNICODE_STYLE_MAP.get(str)) != null && map.containsKey(Character.valueOf(c))) {
                arrayList.add(map.get(Character.valueOf(c)));
            }
        }
        if (arrayList.isEmpty()) {
            return String.valueOf(c);
        }
        return (String) arrayList.get(random.nextInt(arrayList.size()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeResetUnicodeState() {
        if (this.originalJudulBeforeUnicode.isEmpty() || this.editJudul.getText().length() >= this.originalJudulBeforeUnicode.length() * 0.5f) {
            return;
        }
        this.originalJudulBeforeUnicode = "";
    }

    private void openLokasiDialog() {
        syncSelectedLokasiFromField();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        int i = (int) (getResources().getDisplayMetrics().density * 16.0f);
        linearLayout.setPadding(i, i, i, i);
        final EditText editTextBuildDialogInput = buildDialogInput("", 1);
        editTextBuildDialogInput.setHint("Cari lokasi minimal 2 huruf");
        final TextView textView = new TextView(this);
        textView.setTextColor(-12303292);
        int i2 = i / 2;
        textView.setPadding(0, i2, 0, i2);
        ScrollView scrollView = new ScrollView(this);
        final LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(1);
        linearLayout2.setBackgroundColor(-1);
        linearLayout2.setPadding(i, i, i, i);
        scrollView.addView(linearLayout2);
        linearLayout.addView(editTextBuildDialogInput);
        linearLayout.addView(textView);
        linearLayout.addView(scrollView);
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setTitle("Pilih Lokasi").setView(linearLayout).setPositiveButton("Terapkan", new DialogInterface.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda17
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                this.f$0.lambda$openLokasiDialog$43(dialogInterface, i3);
            }
        }).setNeutralButton("Muat Ulang", (DialogInterface.OnClickListener) null).setNegativeButton("Tutup", (DialogInterface.OnClickListener) null).create();
        editTextBuildDialogInput.addTextChangedListener(new TextWatcher() { // from class: com.robotta.bot.DataPostingActivity.4
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                DataPostingActivity.this.updateLokasiSearchResult(editable.toString(), textView, linearLayout2);
            }
        });
        alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda18
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f$0.lambda$openLokasiDialog$47(alertDialogCreate, editTextBuildDialogInput, textView, linearLayout2, dialogInterface);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openLokasiDialog$43(DialogInterface dialogInterface, int i) {
        applySelectedLokasi();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openLokasiDialog$47(AlertDialog alertDialog, final EditText editText, final TextView textView, final LinearLayout linearLayout, DialogInterface dialogInterface) {
        alertDialog.getButton(-3).setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda30
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$openLokasiDialog$45(editText, textView, linearLayout, view);
            }
        });
        updateLokasiSearchResult("", textView, linearLayout);
        if (this.lokasiData.isEmpty()) {
            loadLokasiData(false, new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda31
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$openLokasiDialog$46(editText, textView, linearLayout);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openLokasiDialog$45(final EditText editText, final TextView textView, final LinearLayout linearLayout, View view) {
        loadLokasiData(true, new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda47
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openLokasiDialog$44(editText, textView, linearLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openLokasiDialog$44(EditText editText, TextView textView, LinearLayout linearLayout) {
        updateLokasiSearchResult(editText.getText().toString(), textView, linearLayout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openLokasiDialog$46(EditText editText, TextView textView, LinearLayout linearLayout) {
        updateLokasiSearchResult(editText.getText().toString(), textView, linearLayout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLokasiSearchResult(String str, TextView textView, LinearLayout linearLayout) {
        String lowerCase = str == null ? "" : str.trim().toLowerCase(Locale.ROOT);
        linearLayout.removeAllViews();
        if (this.lokasiData.isEmpty()) {
            textView.setText("Data lokasi belum dimuat.");
            addInfoText(linearLayout, "Tekan 'Muat Ulang' atau tunggu proses pemuatan selesai.");
            return;
        }
        if (lowerCase.length() < 2) {
            textView.setText("Masukkan minimal 2 huruf.");
            addInfoText(linearLayout, "Mulai ketik nama lokasi untuk menampilkan checklist.");
            return;
        }
        ArrayList<String> arrayList = new ArrayList();
        for (String str2 : this.lokasiData) {
            if (str2.toLowerCase(Locale.ROOT).contains(lowerCase)) {
                arrayList.add(str2);
            }
            if (arrayList.size() >= 30) {
                break;
            }
        }
        textView.setText(arrayList.size() + " lokasi ditemukan");
        if (arrayList.isEmpty()) {
            addInfoText(linearLayout, "Tidak ada lokasi yang cocok.");
            return;
        }
        for (final String str3 : arrayList) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(str3);
            checkBox.setChecked(this.selectedLokasi.contains(str3));
            checkBox.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda26
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    this.f$0.lambda$updateLokasiSearchResult$48(str3, compoundButton, z);
                }
            });
            linearLayout.addView(checkBox);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateLokasiSearchResult$48(String str, CompoundButton compoundButton, boolean z) {
        if (z) {
            this.selectedLokasi.add(str);
        } else {
            this.selectedLokasi.remove(str);
        }
    }

    private void applySelectedLokasi() {
        this.editLokasi.setText(joinLines(new ArrayList(this.selectedLokasi)));
        updateLineCount(this.editLokasi, this.textLokasiCount);
        Toast.makeText(this, this.selectedLokasi.size() + " lokasi diterapkan.", 0).show();
    }

    private void syncSelectedLokasiFromField() {
        this.selectedLokasi.clear();
        Iterator<String> it = splitNonEmptyLines(this.editLokasi.getText().toString()).iterator();
        while (it.hasNext()) {
            this.selectedLokasi.add(it.next());
        }
    }

    private void addInfoText(LinearLayout linearLayout, String str) {
        TextView textView = new TextView(this);
        textView.setText(str);
        textView.setTextColor(-12303292);
        linearLayout.addView(textView);
    }

    private void loadLokasiData(boolean z, final Runnable runnable) {
        if (z || this.lokasiData.isEmpty()) {
            showToast("Memuat data lokasi...");
            this.backgroundExecutor.execute(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda35
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$loadLokasiData$51(runnable);
                }
            });
        } else if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadLokasiData$51(final Runnable runnable) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(LOKASI_CSV_URL).openConnection();
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(30000);
            ArrayList arrayList = new ArrayList();
            try {
                InputStream inputStream = httpURLConnection.getInputStream();
                try {
                    for (String str : readStreamFully(inputStream).split("\n")) {
                        String strReplace = str.trim().replaceAll("^\"|\"$", "").replace("\"\"", "\"");
                        if (!strReplace.isEmpty()) {
                            arrayList.add(strReplace);
                        }
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    httpURLConnection.disconnect();
                    this.lokasiData.clear();
                    this.lokasiData.addAll(arrayList);
                    this.mainHandler.post(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda13
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$loadLokasiData$49(runnable);
                        }
                    });
                } catch (Throwable th) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                httpURLConnection.disconnect();
                throw th3;
            }
        } catch (Exception e) {
            this.mainHandler.post(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$loadLokasiData$50(e);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadLokasiData$49(Runnable runnable) {
        Toast.makeText(this, "Data lokasi dimuat: " + this.lokasiData.size(), 0).show();
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadLokasiData$50(Exception exc) {
        Toast.makeText(this, "Gagal memuat lokasi: " + exc.getMessage(), 1).show();
    }

    private String normalizeKeywordList(String str) {
        ArrayList arrayList = new ArrayList();
        for (String str2 : str.split(",")) {
            String strTrim = str2.trim();
            if (!strTrim.isEmpty()) {
                arrayList.add(strTrim);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayList.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append((String) arrayList.get(i));
        }
        return sb.toString();
    }

    private boolean isTransientAiError(IOException iOException) {
        String message = iOException.getMessage();
        if (message == null) {
            return false;
        }
        String lowerCase = message.toLowerCase(Locale.ROOT);
        return message.contains("429") || message.contains("503") || lowerCase.contains("timeout") || lowerCase.contains("connection");
    }

    private void sleepBeforeRetry(int i) {
        try {
            Thread.sleep(Math.min(((1 << Math.min(i, 4)) * 1000) + ((long) new Random().nextInt(500)), 10000L));
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showToast$52(String str) {
        Toast.makeText(this, str, 0).show();
    }

    private void showToast(final String str) {
        this.mainHandler.post(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showToast$52(str);
            }
        });
    }

    private String readStreamFully(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int i = inputStream.read(bArr);
            if (i != -1) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
            }
        }
    }

    private void clearData() {
        new AlertDialog.Builder(this).setTitle("Hapus Data Slot").setMessage("Hapus semua data pada slot " + getSlotName(this.currentSlotValue) + "?").setPositiveButton("Hapus", new DialogInterface.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda20
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$clearData$53(dialogInterface, i);
            }
        }).setNegativeButton("Batal", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearData$53(DialogInterface dialogInterface, int i) {
        this.prefs.edit().remove(this.currentSlotValue).commit();
        setResult(-1);
        rebuildDataSlotOptions(this.currentSlotValue);
        Toast.makeText(this, "Data slot dihapus.", 0).show();
    }

    private void exportData() {
        if (persistCurrentForm()) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("format", DATA_EXPORT_FORMAT);
                jSONObject.put("version", 1);
                jSONObject.put("exportedAt", System.currentTimeMillis());
                jSONObject.put(LAST_SELECTED_SLOT_KEY, this.currentSlotValue);
                JSONArray jSONArray = new JSONArray();
                for (String str : getAllDataSlotValues()) {
                    String string = this.prefs.getString(str, null);
                    if (string != null && !string.trim().isEmpty()) {
                        JSONObject jSONObjectMakePortableSlotData = makePortableSlotData(new JSONObject(string));
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("slotKey", str);
                        jSONObject2.put("slotName", getSlotName(str));
                        jSONObject2.put("data", jSONObjectMakePortableSlotData);
                        jSONArray.put(jSONObject2);
                    }
                }
                if (jSONArray.length() == 0) {
                    Toast.makeText(this, "Tidak ada data untuk diekspor.", 0).show();
                    return;
                }
                jSONObject.put("slots", jSONArray);
                this.pendingExportJson = jSONObject.toString(2);
                String str2 = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(new Date());
                this.exportJsonLauncher.launch("robotta-data-posting-" + str2 + ".json");
            } catch (JSONException unused) {
                this.pendingExportJson = null;
                Toast.makeText(this, "Gagal menyiapkan file export.", 1).show();
            }
        }
    }

    private void importData() {
        this.importJsonLauncher.launch(new String[]{"application/json", AssetHelper.DEFAULT_MIME_TYPE, "application/octet-stream"});
    }

    private JSONObject makePortableSlotData(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject(jSONObject.toString());
        jSONObject2.remove("localGambar1Uris");
        jSONObject2.remove("localGambar2Uris");
        return jSONObject2;
    }

    private void writeExportFile(final Uri uri, final String str) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda49
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$writeExportFile$56(uri, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$writeExportFile$56(Uri uri, String str) {
        try {
            OutputStream outputStreamOpenOutputStream = getContentResolver().openOutputStream(uri, "wt");
            try {
                if (outputStreamOpenOutputStream == null) {
                    throw new IOException("File tujuan tidak dapat dibuka");
                }
                outputStreamOpenOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
                outputStreamOpenOutputStream.flush();
                this.mainHandler.post(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda54
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$writeExportFile$54();
                    }
                });
                if (outputStreamOpenOutputStream != null) {
                    outputStreamOpenOutputStream.close();
                }
            } catch (Throwable th) {
                if (outputStreamOpenOutputStream != null) {
                    try {
                        outputStreamOpenOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Exception e) {
            this.mainHandler.post(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda56
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$writeExportFile$55(e);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$writeExportFile$54() {
        Toast.makeText(this, "Export JSON berhasil disimpan.", 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$writeExportFile$55(Exception exc) {
        Toast.makeText(this, "Export gagal: " + safeErrorMessage(exc), 1).show();
    }

    private void readImportFile(final Uri uri) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda53
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$readImportFile$59(uri);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readImportFile$59(Uri uri) {
        try {
            InputStream inputStreamOpenInputStream = getContentResolver().openInputStream(uri);
            try {
                if (inputStreamOpenInputStream == null) {
                    throw new IOException("File tidak dapat dibuka");
                }
                String strTrim = readStreamFully(inputStreamOpenInputStream).trim();
                if (strTrim.isEmpty()) {
                    throw new JSONException("File kosong");
                }
                final JSONObject jSONObject = new JSONObject(strTrim);
                final int iCountImportableSlots = countImportableSlots(jSONObject);
                this.mainHandler.post(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda61
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$readImportFile$57(jSONObject, iCountImportableSlots);
                    }
                });
                if (inputStreamOpenInputStream != null) {
                    inputStreamOpenInputStream.close();
                }
            } catch (Throwable th) {
                if (inputStreamOpenInputStream != null) {
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Exception e) {
            this.mainHandler.post(new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda62
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$readImportFile$58(e);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readImportFile$58(Exception exc) {
        Toast.makeText(this, "Import gagal: " + safeErrorMessage(exc), 1).show();
    }

    private int countImportableSlots(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("slots");
        if (jSONArrayOptJSONArray != null) {
            if (!DATA_EXPORT_FORMAT.equals(jSONObject.optString("format", ""))) {
                throw new JSONException("Format backup tidak dikenali");
            }
            int i = 0;
            for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i2);
                if (jSONObjectOptJSONObject != null && isValidSlotKey(jSONObjectOptJSONObject.optString("slotKey", "")) && jSONObjectOptJSONObject.has("data")) {
                    i++;
                }
            }
            if (i != 0) {
                return i;
            }
            throw new JSONException("Tidak ada slot yang valid");
        }
        if (!jSONObject.has("data")) {
            throw new JSONException("Data posting tidak ditemukan");
        }
        parsePortableData(jSONObject.opt("data"));
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: confirmImport, reason: merged with bridge method [inline-methods] */
    public void lambda$readImportFile$57(final JSONObject jSONObject, int i) {
        String str;
        if (jSONObject.optJSONArray("slots") != null) {
            str = "Import " + i + " slot? Slot dengan nomor yang sama akan ditimpa.";
        } else {
            str = "Import data ke slot " + getSlotName(this.currentSlotValue) + "? Data slot ini akan ditimpa.";
        }
        new AlertDialog.Builder(this).setTitle("Konfirmasi Import JSON").setMessage(str).setPositiveButton("Import", new DialogInterface.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda28
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f$0.lambda$confirmImport$60(jSONObject, dialogInterface, i2);
            }
        }).setNegativeButton("Batal", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$confirmImport$60(JSONObject jSONObject, DialogInterface dialogInterface, int i) {
        applyImport(jSONObject);
    }

    private void applyImport(JSONObject jSONObject) {
        try {
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("slots");
            if (jSONArrayOptJSONArray == null) {
                importLegacySlot(jSONObject);
                return;
            }
            LinkedHashSet<String> addedSlotSet = readAddedSlotSet();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            SharedPreferences.Editor editorEdit = this.prefs.edit();
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                if (jSONObjectOptJSONObject != null) {
                    String strTrim = jSONObjectOptJSONObject.optString("slotKey", "").trim();
                    if (isValidSlotKey(strTrim) && jSONObjectOptJSONObject.has("data")) {
                        editorEdit.putString(strTrim, parsePortableData(jSONObjectOptJSONObject.opt("data")).toString());
                        String strTrim2 = jSONObjectOptJSONObject.optString("slotName", "").trim();
                        if (!strTrim2.isEmpty()) {
                            editorEdit.putString(SLOT_NAME_PREFIX + strTrim, strTrim2);
                        }
                        if (getSlotNumber(strTrim) > 10) {
                            addedSlotSet.add(strTrim);
                        }
                        linkedHashSet.add(strTrim);
                    }
                }
            }
            if (linkedHashSet.isEmpty()) {
                throw new JSONException("Tidak ada slot yang dapat diimport");
            }
            JSONArray jSONArray = new JSONArray();
            Iterator<String> it = addedSlotSet.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next());
            }
            String strOptString = jSONObject.optString(LAST_SELECTED_SLOT_KEY, "");
            if (!linkedHashSet.contains(strOptString)) {
                strOptString = (String) linkedHashSet.iterator().next();
            }
            editorEdit.putString(ADDED_SLOTS_KEY, jSONArray.toString());
            editorEdit.putString(LAST_SELECTED_SLOT_KEY, strOptString);
            if (!editorEdit.commit()) {
                throw new IOException("Penyimpanan perangkat menolak perubahan");
            }
            setResult(-1);
            rebuildDataSlotOptions(strOptString);
            Toast.makeText(this, linkedHashSet.size() + " slot berhasil diimport.", 1).show();
        } catch (Exception e) {
            Toast.makeText(this, "Import gagal: " + safeErrorMessage(e), 1).show();
        }
    }

    private void importLegacySlot(JSONObject jSONObject) throws JSONException, IOException {
        JSONObject portableData = parsePortableData(jSONObject.opt("data"));
        SharedPreferences.Editor editorEdit = this.prefs.edit();
        editorEdit.putString(this.currentSlotValue, portableData.toString());
        String strTrim = jSONObject.optString("slotName", "").trim();
        if (!strTrim.isEmpty()) {
            editorEdit.putString(SLOT_NAME_PREFIX + this.currentSlotValue, strTrim);
        }
        editorEdit.putString(LAST_SELECTED_SLOT_KEY, this.currentSlotValue);
        if (!editorEdit.commit()) {
            throw new IOException("Penyimpanan perangkat menolak perubahan");
        }
        setResult(-1);
        rebuildDataSlotOptions(this.currentSlotValue);
        Toast.makeText(this, "Data berhasil diimport.", 1).show();
    }

    private JSONObject parsePortableData(Object obj) throws JSONException {
        JSONObject jSONObject;
        if (obj instanceof JSONObject) {
            jSONObject = new JSONObject(obj.toString());
        } else {
            if (obj instanceof String) {
                String str = (String) obj;
                if (!str.trim().isEmpty()) {
                    jSONObject = new JSONObject(str.trim());
                }
            }
            throw new JSONException("Isi data slot tidak valid");
        }
        jSONObject.remove("localGambar1Uris");
        jSONObject.remove("localGambar2Uris");
        return jSONObject;
    }

    private LinkedHashSet<String> readAddedSlotSet() {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        try {
            JSONArray jSONArray = new JSONArray(this.prefs.getString(ADDED_SLOTS_KEY, "[]"));
            for (int i = 0; i < jSONArray.length(); i++) {
                String strOptString = jSONArray.optString(i, "");
                if (isValidSlotKey(strOptString) && getSlotNumber(strOptString) > 10) {
                    linkedHashSet.add(strOptString);
                }
            }
        } catch (JSONException unused) {
        }
        return linkedHashSet;
    }

    private boolean isValidSlotKey(String str) {
        return getSlotNumber(str) >= 1 && getSlotNumber(str) <= 1000;
    }

    private int getSlotNumber(String str) {
        if (str != null && str.matches("^data\\d+$")) {
            try {
                return Integer.parseInt(str.substring(4));
            } catch (NumberFormatException unused) {
            }
        }
        return -1;
    }

    private String safeErrorMessage(Exception exc) {
        String message = exc.getMessage();
        return (message == null || message.trim().isEmpty()) ? "Kesalahan tidak diketahui" : message;
    }

    private EditText buildDialogInput(String str, int i) {
        EditText editText = new EditText(this);
        editText.setText(str);
        editText.setInputType(i);
        int i2 = (int) (getResources().getDisplayMetrics().density * 16.0f);
        editText.setPadding(i2, i2, i2, i2);
        editText.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        return editText;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.backgroundExecutor.shutdownNow();
    }

    private static Map<String, Map<Character, String>> buildUnicodeStyleMap() {
        HashMap map = new HashMap();
        map.put("bold", buildStyleMap("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", "𝐚𝐛𝐜𝐝𝐞𝐟𝐠𝐡𝐢𝐣𝐤𝐥𝐦𝐧𝐨𝐩𝐪𝐫𝐬𝐭𝐮𝐯𝐰𝐱𝐲𝐳𝐀𝐁𝐂𝐃𝐄𝐅𝐆𝐇𝐈𝐉𝐊𝐋𝐌𝐍𝐎𝐏𝐐𝐑𝐒𝐓𝐔𝐕𝐖𝐗𝐘𝐙"));
        map.put("bold-sans", buildStyleMap("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", "𝗮𝗯𝗰𝗱𝗲𝗳𝗴𝗵𝗶𝗷𝗸𝗹𝗺𝗻𝗼𝗽𝗾𝗿𝘀𝘁𝘂𝘃𝘄𝘅𝘆𝘇𝗔𝗕𝗖𝗗𝗘𝗙𝗚𝗛𝗜𝗝𝗞𝗟𝗠𝗡𝗢𝗣𝗤𝗥𝗦𝗧𝗨𝗩𝗪𝗫𝗬𝗭"));
        map.put("italic", buildStyleMap("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", "𝘢𝘣𝘤𝘥𝘦𝘧𝘨𝘩𝘪𝘫𝘬𝘭𝘮𝘯𝘰𝘱𝘲𝘳𝘴𝘵𝘶𝘷𝘸𝘹𝘺𝘻𝘈𝘉𝘊𝘋𝘌𝘍𝘎𝘏𝘐𝘑𝘒𝘓𝘔𝘕𝘖𝘗𝘘𝘙𝘚𝘛𝘜𝘝𝘞𝘟𝘠𝘡"));
        map.put("bold-italic", buildStyleMap("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", "𝙖𝙗𝙘𝙙𝙚𝙛𝙜𝙝𝙞𝙟𝙠𝙡𝙢𝙣𝙤𝙥𝙦𝙧𝙨𝙩𝙪𝙫𝙬𝙭𝙮𝙯𝘼𝘽𝘾𝘿𝙀𝙁𝙂𝙃𝙄𝙅𝙆𝙇𝙈𝙉𝙊𝙋𝙌𝙍𝙎𝙏𝙐𝙑𝙒𝙓𝙔𝙕"));
        map.put("monospace", buildStyleMap("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", "𝚊𝚋𝚌𝚍𝚎𝚏𝚐𝚑𝚒𝚓𝚔𝚕𝚖𝚗𝚘𝚙𝚚𝚛𝚜𝚝𝚞𝚟𝚠𝚡𝚢𝚣𝙰𝙱𝙲𝙳𝙴𝙵𝙶𝙷𝙸𝙹𝙺𝙻𝙼𝙽𝙾𝙿𝚀𝚁𝚂𝚃𝚄𝚅𝚆𝚇𝚈𝚉"));
        map.put("fullwidth", buildStyleMap("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"));
        map.put("small-caps", buildStyleMap("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀꜱᴛᴜᴠᴡxʏᴢᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀꜱᴛᴜᴠᴡxʏᴢ"));
        HashMap map2 = new HashMap();
        map2.put('a', "4");
        map2.put('e', "3");
        map2.put('i', "1");
        map2.put('o', "0");
        map2.put('s', "5");
        map2.put('A', "4");
        map2.put('E', "3");
        map2.put('I', "1");
        map2.put('O', "0");
        map2.put('S', "5");
        map2.put(' ', "_");
        map.put("leet-style", map2);
        map.put("mixed", Collections.emptyMap());
        return map;
    }

    private static Map<Character, String> buildStyleMap(String str, String str2) {
        HashMap map = new HashMap();
        int i = 0;
        int iCharCount = 0;
        while (i < str.length() && iCharCount < str2.length()) {
            int iCodePointAt = str2.codePointAt(iCharCount);
            map.put(Character.valueOf(str.charAt(i)), new String(Character.toChars(iCodePointAt)));
            i++;
            iCharCount += Character.charCount(iCodePointAt);
        }
        return map;
    }

    private static final class SlotItem {
        final String label;
        final String value;

        public String toString() {
            return this.label;
        }

        SlotItem(String str, String str2) {
            this.value = str;
            this.label = str2;
        }
    }

    private static final class RetryableException extends IOException {
        RetryableException(String str, int i) {
            super(str + " (" + i + ")");
        }
    }

    private void setupFullscreenEditorTrigger(final EditText editText, final String str) {
        editText.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda50
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.lambda$setupFullscreenEditorTrigger$61(editText, str, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setupFullscreenEditorTrigger$61(EditText editText, String str, View view) {
        showFullscreenEditor(editText, str);
        return true;
    }

    private void setupExpandButton(int i, final EditText editText, final String str) {
        View viewFindViewById = findViewById(i);
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda58
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$setupExpandButton$62(editText, str, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupExpandButton$62(EditText editText, String str, View view) {
        showFullscreenEditor(editText, str);
    }

    private void showFullscreenEditor(final EditText editText, final String str) {
        final Dialog dialog = new Dialog(this, android.R.style.Theme.Material.Light.NoActionBar);
        dialog.setCancelable(false);
        float f = getResources().getDisplayMetrics().density;
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        linearLayout.setBackgroundColor(Color.parseColor("#F0F4F8"));
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(0);
        linearLayout2.setGravity(16);
        linearLayout2.setBackgroundColor(-1);
        linearLayout2.setElevation(6.0f * f);
        int i = (int) (f * 14.0f);
        linearLayout2.setPadding(i, 0, i, 0);
        linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (56.0f * f)));
        TextView textView = new TextView(this);
        textView.setText("✏ " + str);
        textView.setTextSize(17.0f);
        textView.setTextColor(Color.parseColor("#1A202C"));
        textView.setTypeface(null, 1);
        textView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        linearLayout2.addView(textView);
        final TextView textView2 = new TextView(this);
        textView2.setTextSize(12.0f);
        textView2.setTextColor(Color.parseColor("#64748B"));
        int i2 = (int) (8.0f * f);
        int i3 = (int) (12.0f * f);
        textView2.setPadding(i2, 0, i3, 0);
        linearLayout2.addView(textView2);
        Button button = new Button(this);
        button.setText("✕");
        button.setTextColor(Color.parseColor("#64748B"));
        button.setTextSize(16.0f);
        button.setBackgroundTintList(null);
        button.setBackground(AppThemeManager.outlined("#FFFFFF", "#CBD5E1", 8));
        int i4 = (int) (40.0f * f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i4, i4);
        layoutParams.setMarginEnd(i2);
        button.setLayoutParams(layoutParams);
        linearLayout2.addView(button);
        Button button2 = new Button(this);
        button2.setText("Simpan");
        button2.setTextColor(-1);
        button2.setTextSize(13.0f);
        button2.setTypeface(null, 1);
        button2.setBackgroundTintList(null);
        button2.setBackground(AppThemeManager.filled("#2563EB", 10));
        button2.setLayoutParams(new LinearLayout.LayoutParams(-2, i4));
        int i5 = (int) (16.0f * f);
        button2.setPadding(i5, 0, i5, 0);
        linearLayout2.addView(button2);
        linearLayout.addView(linearLayout2);
        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setVerticalScrollBarEnabled(true);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, 0, 1.0f);
        layoutParams2.topMargin = (int) (2.0f * f);
        scrollView.setLayoutParams(layoutParams2);
        LinearLayout linearLayout3 = new LinearLayout(this);
        linearLayout3.setOrientation(0);
        linearLayout3.setBackgroundColor(-1);
        linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        final TextView textView3 = new TextView(this);
        textView3.setTypeface(Typeface.MONOSPACE);
        textView3.setTextSize(14.0f);
        textView3.setTextColor(Color.parseColor("#94A3B8"));
        textView3.setBackgroundColor(Color.parseColor("#F8FAFC"));
        textView3.setGravity(8388661);
        int i6 = (int) (10.0f * f);
        textView3.setPadding(i6, i3, i2, i3);
        textView3.setLineSpacing(0.0f, 1.45f);
        textView3.setIncludeFontPadding(false);
        textView3.setLayoutParams(new LinearLayout.LayoutParams((int) (f * 48.0f), -2));
        View view = new View(this);
        view.setBackgroundColor(Color.parseColor("#E2E8F0"));
        view.setLayoutParams(new LinearLayout.LayoutParams((int) (f * 1.0f), -1));
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
        horizontalScrollView.setFillViewport(true);
        horizontalScrollView.setHorizontalScrollBarEnabled(true);
        horizontalScrollView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        final EditText editText2 = new EditText(this);
        editText2.setTypeface(Typeface.MONOSPACE);
        editText2.setTextSize(14.0f);
        editText2.setTextColor(Color.parseColor("#1A202C"));
        editText2.setHintTextColor(Color.parseColor("#94A3B8"));
        editText2.setHint(editText.getHint());
        editText2.setBackgroundColor(0);
        editText2.setGravity(8388659);
        editText2.setPadding(i6, i3, i6, i3);
        editText2.setLineSpacing(0.0f, 1.45f);
        editText2.setIncludeFontPadding(false);
        editText2.setInputType(655361);
        editText2.setSingleLine(false);
        editText2.setMaxLines(Integer.MAX_VALUE);
        editText2.setHorizontallyScrolling(true);
        editText2.setVerticalScrollBarEnabled(false);
        editText2.setScrollContainer(false);
        editText2.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        editText2.setText(editText.getText());
        final Runnable runnable = new Runnable() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                DataPostingActivity.lambda$showFullscreenEditor$63(editText2, textView2, textView3);
            }
        };
        runnable.run();
        editText2.addTextChangedListener(new TextWatcher() { // from class: com.robotta.bot.DataPostingActivity.5
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i7, int i8, int i9) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i7, int i8, int i9) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                runnable.run();
            }
        });
        horizontalScrollView.addView(editText2);
        linearLayout3.addView(textView3);
        linearLayout3.addView(view);
        linearLayout3.addView(horizontalScrollView);
        scrollView.addView(linearLayout3);
        linearLayout.addView(scrollView);
        dialog.setContentView(linearLayout);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda23
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                dialog.dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.robotta.bot.DataPostingActivity$$ExternalSyntheticLambda24
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$showFullscreenEditor$65(editText, editText2, dialog, str, view2);
            }
        });
        dialog.show();
        editText2.requestFocus();
        editText2.setSelection(editText2.getText().length());
    }

    static /* synthetic */ void lambda$showFullscreenEditor$63(EditText editText, TextView textView, TextView textView2) {
        String string = editText.getText().toString();
        int length = string.isEmpty() ? 1 : string.split("\n", -1).length;
        textView.setText("Baris: " + length);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= length; i++) {
            if (i > 1) {
                sb.append('\n');
            }
            sb.append(i);
        }
        textView2.setText(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFullscreenEditor$65(EditText editText, EditText editText2, Dialog dialog, String str, View view) {
        editText.setText(editText2.getText());
        dialog.dismiss();
        Toast.makeText(this, str + " telah diperbarui.", 0).show();
    }
}
