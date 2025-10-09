package com.example.study_app;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReaderActivity extends AppCompatActivity {

    private PDFView pdfView;
    private BroadcastReceiver onDownloadComplete;

    private Button btnBack, btnToggleToc;
    private TextView tvTitle;
    private ListView listToc;

    private final List<TocItem> tocItems = new ArrayList<>();
    private ArrayAdapter<String> tocAdapter;

    private String jsonAssetPath = null;

    private static class TocItem {
        final String title;
        final int pageIndex;
        final int level;

        TocItem(String title, int pageIndex, int level) {
            this.title = title;
            this.pageIndex = pageIndex;
            this.level = level;
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private void enqueueDownload(String url, File out) {
        try {
            DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

            DownloadManager.Request req = new DownloadManager.Request(Uri.parse(url))
                    .setTitle(out.getName())
                    .setDescription("Đang tải sách…")
                    .setMimeType("application/pdf")
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(false)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOCUMENTS, out.getName());

            dm.enqueue(req);
            onDownloadComplete = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (out.exists() && out.length() > 0) {
                        Toast.makeText(context, "Đã lưu: " + out.getAbsolutePath(), Toast.LENGTH_LONG).show();
                        openPdf(out);
                    } else {
                        Toast.makeText(context, "Tải thất bại", Toast.LENGTH_LONG).show();
                    }
                }
            };

            IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            registerReceiver(onDownloadComplete, filter);

        } catch (Exception e) {
            Toast.makeText(this, "Không thể bắt đầu tải: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void openPdf(File file) {
        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .spacing(4)
                .load();
    }

    private void loadTocFromJson(String assetPath) {
        try (InputStream is = getAssets().open(assetPath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            StringBuilder sb = new StringBuilder();
            String line; while ((line = br.readLine()) != null) sb.append(line);

            JSONObject root = new JSONObject(sb.toString());
            JSONArray chapters = root.optJSONArray("chapters");
            if (chapters == null) {
                listToc.setVisibility(View.GONE);
                btnToggleToc.setEnabled(false);
                btnToggleToc.setAlpha(0.5f);
                return;
            }

            tocItems.clear();
            List<String> display = new ArrayList<>();

            for (int i = 0; i < chapters.length(); i++) {
                JSONObject ch = chapters.getJSONObject(i);
                String chTitle = ch.optString("title", "Chương " + (i + 1));
                display.add(chTitle);
                tocItems.add(new TocItem(chTitle, -1, 0));

                JSONArray lessons = ch.optJSONArray("lessons");
                if (lessons == null) continue;

                for (int j = 0; j < lessons.length(); j++) {
                    JSONObject lesson = lessons.getJSONObject(j);
                    String lessonTitle = lesson.optString("title", "Bài " + (j + 1));
                    int page1Based = lesson.optInt("page", 1);
                    int page0Based = Math.max(0, page1Based - 1);

                    display.add("  • " + lessonTitle);
                    tocItems.add(new TocItem(lessonTitle, page0Based, 1));
                }
            }

            tocAdapter.clear();
            tocAdapter.addAll(display);
            tocAdapter.notifyDataSetChanged();

            boolean hasToc = !display.isEmpty();
            listToc.setVisibility(hasToc ? View.VISIBLE : View.GONE);
            btnToggleToc.setEnabled(hasToc);
            btnToggleToc.setAlpha(hasToc ? 1f : 0.5f);

        } catch (Exception e) {
            listToc.setVisibility(View.GONE);
            btnToggleToc.setEnabled(false);
            btnToggleToc.setAlpha(0.5f);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        pdfView = findViewById(R.id.pdfView);
        btnBack = findViewById(R.id.btnBack);
        btnToggleToc = findViewById(R.id.btnToggleToc);
        tvTitle = findViewById(R.id.tvTitle);
        listToc = findViewById(R.id.listToc);

        String title  = getIntent().getStringExtra("title");
        String pdfUrl = getIntent().getStringExtra("pdf_url");
        jsonAssetPath = getIntent().getStringExtra("jsonAssetPath");

        tvTitle.setText(title);

        String fileName = title + ".pdf";

        File out = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);

        btnBack.setOnClickListener(v -> finish());

        btnToggleToc.setOnClickListener(v -> {
            if (listToc.getVisibility() == View.VISIBLE) {
                listToc.setVisibility(View.GONE);
            } else {
                listToc.setVisibility(View.VISIBLE);
            }
        });

        tocAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listToc.setAdapter(tocAdapter);

        listToc.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < tocItems.size()) {
                int page = tocItems.get(position).pageIndex;
                if (page >= 0) pdfView.jumpTo(page, true);
            }
        });

        if (out.exists()) {
            openPdf(out);
        } else {
            enqueueDownload(pdfUrl, out);
        }

        if (jsonAssetPath != null && !jsonAssetPath.isEmpty()) {
            loadTocFromJson(jsonAssetPath);
        } else {
            listToc.setVisibility(View.GONE);
            btnToggleToc.setEnabled(false);
            btnToggleToc.setAlpha(0.5f);
        }
    }

    @Override
    protected void onDestroy() {
        if (onDownloadComplete != null) {
            unregisterReceiver(onDownloadComplete);
            onDownloadComplete = null;
        }
        super.onDestroy();
    }
}