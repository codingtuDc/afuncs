package cn.yuanye1818.autils.core.image;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

import cn.yuanye1818.autils.global.App;

public class ImageFunc {

    public static void updateAlbum(File file) {
        if (file == null)
            file = Environment.getExternalStorageDirectory();
        App.APP.sendBroadcast(
                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }

}
