package org.oftn.oswg.zerodrop;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;
import android.util.Log;
import android.webkit.JavascriptInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static android.content.ContentValues.TAG;

public class ShareIntentInterface {

    private final Context mContext;
    private final Intent mIntent;

    ShareIntentInterface(Context context, Intent intent) {
        mContext = context;
        mIntent = intent;
    }

    @JavascriptInterface
    public String getMimeType() {
        return mIntent.getType();
    }

    @JavascriptInterface
    public String getBase64Data() {
        final Uri dataUri = mIntent.getParcelableExtra(Intent.EXTRA_STREAM);
        final InputStream stream;
        try {
            stream = mContext.getContentResolver().openInputStream(dataUri);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "could not open share intent stream", e);
            return null;
        }
        if (stream == null) {
            return null;
        }
        final byte[] buffer;
        try {
            buffer = IoUtils.readStreamAsByteArray(stream);
        } catch (IOException e) {
            Log.e(TAG, "could not read share intent stream", e);
            return null;
        } finally {
            try {
                stream.close();
            } catch (IOException ignored) {
            }
        }
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    @JavascriptInterface
    public String getTextData() {
        return mIntent.getStringExtra(Intent.EXTRA_TEXT);
    }

    @JavascriptInterface
    public void onUrlAvailable(String url) {
        final ClipboardManager clipboard = (ClipboardManager)
                mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText(
                    mContext.getString(R.string.clipboard_data_label), url));
        }
    }

}

