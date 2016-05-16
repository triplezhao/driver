package com.mozillaonline.downloadprovider;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.mozillaonline.providers.DownloadManager;
import potato.driver.chips.app.MainApplication;

/**
 * Created by ztw on 2015/12/25.
 */
public class DownLoadUtil {

    public static void startDownload(Context context,String url,String name) {
        Uri srcUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(srcUri);
        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, "/");
        request.setDescription(name);
        MainApplication.mDownloadManager.enqueue(request);
    }
}
