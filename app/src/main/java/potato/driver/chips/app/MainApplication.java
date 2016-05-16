package potato.driver.chips.app;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.mozillaonline.providers.DownloadManager;
import com.potato.library.net.RequestManager;

import cn.sharesdk.framework.ShareSDK;
import potato.driver.chips.util.ImageLoaderUtil;
import potato.driver.chips.util.PhoneUtils;

/**
 * Created by zhaobingfeng on 14-12-22.
 */
public class MainApplication extends Application {
    /** 获取屏幕的宽和高 */
    public static int screenHight = 0;
    public static int screenWidth = 0;
    public static DisplayMetrics displayMetrices;
    /**
     * 设备的 IMEI
     */
    public static String IMEI = "";
    /**
     * 获取全局的上下文
     */
    public static Context context;
    public static DownloadManager mDownloadManager;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        init();
    }

    /**
     */
    private void init() {
        context = getApplicationContext();

        initDeviceWidthAndHeight();

        ShareSDK.initSDK(context);
        //获取imei
        PhoneUtils.getIMEI(context);
        //请求缓存管理
        RequestManager.init(context);
        //请求初始化
//        RequestConfig.addHttpClientRASHead(instence);
//        instence.setUserAgent(PhoneUtils.getDeviceUA(context));
        initPicasso();
        initUIL();
        initDownloadManager();
        
    }

    private void initDownloadManager() {
        mDownloadManager = new DownloadManager(getContentResolver(),
                getPackageName());
    }


    private void initPicasso() {
//            final String imageCacheDir = /* 自定义目录 */ +"image";
//        Picasso picasso = new Picasso.Builder(context).memoryCache(
//                new LruCache(1024*1024*20)).downloader();
//            Picasso picasso = new Picasso.Builder(this).downloader(new OkHttpDownloader(new File(imageCacheDir))).build();
//            Picasso.setSingletonInstance(picasso);
    }

    public void initUIL(){
        ImageLoaderUtil.init(context);
    }

    /**
     * 获取设备的宽和高 WangQing 2013-8-12 void
     */
    private void initDeviceWidthAndHeight() {
        displayMetrices = PhoneUtils.getAppWidthAndHeight(this);
        screenHight = displayMetrices.heightPixels;
        screenWidth = displayMetrices.widthPixels;
    }
}
