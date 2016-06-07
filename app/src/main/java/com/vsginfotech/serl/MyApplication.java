package com.vsginfotech.serl;


import android.app.Application;

public class MyApplication extends Application {


    private static MyApplication mInstance;
    private MyPreferenceManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //     initImageLoader(getApplicationContext());
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public boolean IsInternetConnected() {
        return CheckInternetConnection.checkInternetConnection(this);

    }

    public MyPreferenceManager getPrefManager(String pref_key_value) {
        if (manager == null) {
            manager = new MyPreferenceManager(this, pref_key_value);
        }
        return manager;


    }
    //Initiate Image Loader Configuration
   /* public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
                context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());

    }*/

}
