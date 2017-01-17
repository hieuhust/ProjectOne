package com.anonymous.carchecker.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.anonymous.carchecker.R;

/**
 * Created by Huy Hieu on 1/16/2017.
 */

public class PhoneUtil {

    public static void call(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        try {
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.can_not_call_number_msg) + " " + number, Toast.LENGTH_LONG).show();
        }

    }
}
