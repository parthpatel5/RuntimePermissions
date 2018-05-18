package com.factob.getuserdata.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.factob.mutualfund.R;

import java.util.ArrayList;

/**
 * Created by version-1 on 26-05-2017.
 */

public class PermissionUtility {

    public static boolean checkAndRequestPermissions(final Activity activity, int permissionReqCode, String... permissions) {
        int grantedPermissions = 0;
        ArrayList<String> requiredPermissions = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (!hasPermission(activity, permission)) {
                    requiredPermissions.add(permission);
                } else
                    grantedPermissions++;
            }

            if (grantedPermissions == permissions.length) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, requiredPermissions.toArray(new String[requiredPermissions.size()]), permissionReqCode);
                return false;
            }
        }
        return true;
    }

    public static boolean checkAndRequestPermissions(final Activity activity, Fragment fragment, int permissionReqCode, String... permissions) {
        int grantedPermissions = 0;
        ArrayList<String> requiredPermissions = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (!hasPermission(activity, permission)) {
                    requiredPermissions.add(permission);
                } else
                    grantedPermissions++;
            }

            if (grantedPermissions == permissions.length) {
                return true;
            } else {
                fragment.requestPermissions(requiredPermissions.toArray(new String[requiredPermissions.size()]), permissionReqCode);
                return false;
            }
        }
        return true;
    }

    public static boolean checkAndRequestPermission(final Activity activity, String permissionString, int permissionReqCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPermission(activity, permissionString)) {
                ActivityCompat.requestPermissions(activity, new String[]{permissionString}, permissionReqCode);
                return false;
            }
            return true;
        }
        return true;
    }

    public static boolean checkAndRequestPermission(final Activity activity, final Fragment fragment, String permissionString, int permissionReqCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPermission(activity, permissionString)) {
                fragment.requestPermissions(new String[]{permissionString}, permissionReqCode);
                return false;
            }
            return true;
        }
        return true;
    }

    public static boolean hasPermission(final Activity activity, String permissionString) {
        return ContextCompat.checkSelfPermission(activity, permissionString) == PackageManager.PERMISSION_GRANTED;
    }

    public static void showPermissionRequiredDialog(final Activity activity, String msg, DialogInterface.OnClickListener onClickListener) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(R.string.title_permission_required)
                .setMessage(msg)
                .setPositiveButton(R.string.lbl_settings, onClickListener)
                .setNegativeButton(R.string.lbl_not_now, null)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void openSettingsIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }
}
