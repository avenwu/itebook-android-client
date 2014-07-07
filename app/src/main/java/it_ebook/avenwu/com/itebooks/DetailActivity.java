package it_ebook.avenwu.com.itebooks;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<BookDetail> {
    TextView title;
    TextView description;
    ImageView image;
    Button download;
    DownloadManager downloadManager;
    long lastDownload = -1L;
    String bookId;
    BookDetail bookDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        image = (ImageView) findViewById(R.id.image);
        download = (Button) findViewById(R.id.btn_download);
        final Book book = getIntent().getParcelableExtra("book");
        if (book != null) {
            bookId = book.getID();
            title.setText(book.getTitle());
            description.setText(book.getDescription());
            if (URLUtil.isNetworkUrl(book.getImage())) {
                Picasso.with(this).load(book.getImage()).into(image);
            }
        }
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                download(bookDetail.getDownload());
            }
        });
        download.setEnabled(bookDetail != null);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter f = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        f.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        LocalBroadcastManager.getInstance(this).registerReceiver(onEvent, f);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(onEvent);
    }

    void download(String path) {
        Uri uri = Uri.parse(path);
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .mkdirs();

        DownloadManager.Request req = new DownloadManager.Request(uri);
        req.allowScanningByMediaScanner();
        downloadManager.enqueue(req);
    }

    private void queryStatus(View v) {
        Cursor c = downloadManager.query(new DownloadManager.Query().setFilterById(lastDownload));

        if (c == null) {
            Toast.makeText(this, "download not found", Toast.LENGTH_LONG).show();
        } else {
            c.moveToFirst();

            Log.d(getClass().getName(),
                    "COLUMN_ID: "
                            + c.getLong(c.getColumnIndex(DownloadManager.COLUMN_ID))
            );
            Log.d(getClass().getName(),
                    "COLUMN_BYTES_DOWNLOADED_SO_FAR: "
                            + c.getLong(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
            );
            Log.d(getClass().getName(),
                    "COLUMN_LAST_MODIFIED_TIMESTAMP: "
                            + c.getLong(c.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP))
            );
            Log.d(getClass().getName(),
                    "COLUMN_LOCAL_URI: "
                            + c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
            );
            Log.d(getClass().getName(),
                    "COLUMN_STATUS: "
                            + c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))
            );
            Log.d(getClass().getName(),
                    "COLUMN_REASON: "
                            + c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON))
            );
            Toast.makeText(this, statusMessage(c), Toast.LENGTH_LONG)
                    .show();
            c.close();
        }
    }

    private String statusMessage(Cursor c) {
        String msg;
        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED:
                msg = getString(R.string.download_failed);
                break;
            case DownloadManager.STATUS_PAUSED:
                msg = getString(R.string.download_paused);
                break;
            case DownloadManager.STATUS_PENDING:
                msg = getString(R.string.download_pending);
                break;
            case DownloadManager.STATUS_RUNNING:
                msg = getString(R.string.download_in_progress);
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                msg = getString(R.string.download_sucess);
                break;
            default:
                msg = getString(R.string.download_is_nowhere_in_sight);
                break;
        }
        return msg;
    }

    private BroadcastReceiver onEvent = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent i) {
            if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(i.getAction())) {
                Toast.makeText(ctxt, "Download clicked", Toast.LENGTH_LONG).show();
            } else {
//                start.setEnabled(true);
            }
        }
    };

    @Override
    public Loader<BookDetail> onCreateLoader(int id, Bundle args) {
        return new BaseLoader<BookDetail>(this) {
            @Override
            public BookDetail loadInBackground() {
                return ApiService.getService().getBookById(bookId);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<BookDetail> loader, BookDetail data) {
        if (data != null) {
            bookDetail = data;
            download.setEnabled(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<BookDetail> loader) {

    }
}
