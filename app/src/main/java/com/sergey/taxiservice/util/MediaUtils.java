package com.sergey.taxiservice.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;

public class MediaUtils {

    public static Uri getSetupAvatarUri(Context context) {
        File avatarFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/avatarSetup.jpg");
        if (Build.VERSION.SDK_INT > 21) {
            return FileProvider.getUriForFile(context, context.getPackageName()+".fileprovider", avatarFile);
        } else return Uri.fromFile(avatarFile);
    }
}
