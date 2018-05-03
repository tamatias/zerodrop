package org.oftn.oswg.zerodrop;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;

public class ZerodropClient {

    private final Context mContext;
    private final Intent mIntent;

    ZerodropClient(Context context, Intent intent) {
        mContext = context;
        mIntent = intent;
    }

    @JavascriptInterface
    public String getMimeType() {
        return mIntent.getType();
    }

    @JavascriptInterface
    public Uri getStreamUri() {
        return mIntent.getParcelableExtra(Intent.EXTRA_STREAM);
    }

    @JavascriptInterface
    public String getText() {
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

