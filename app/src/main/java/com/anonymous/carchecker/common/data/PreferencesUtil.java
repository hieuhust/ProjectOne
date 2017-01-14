package com.anonymous.carchecker.common.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.anonymous.carchecker.common.model.DataModel;
import com.google.gson.Gson;

/**
 * Created by Huy Hieu on 12/26/2016.
 */

public class PreferencesUtil {

    public String TAG = this.getClass().getSimpleName();

    private Context mContext;

    private SharedPreferences mSharedPreferences;

    private static PreferencesUtil sPreferencesUtil;

    private PreferencesUtil(Context context) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    public static PreferencesUtil newInstance(Context context) {
        if (sPreferencesUtil == null) {
            sPreferencesUtil = new PreferencesUtil(context);
        }
        return sPreferencesUtil;
    }


    public <T extends DataModel> T getDataModel(Class<T> cls) {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString(cls.getSimpleName(), null);
        if (json == null) {
            return null;
        }
        T dataModel = (T) gson.fromJson(json, cls);
        return dataModel;
    }

    public <T extends DataModel> void setDataModel(T dataModel, Class<T> cls) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        //Convert object to json
        Gson gson = new Gson();
        String json = gson.toJson(dataModel);
        editor.putString(cls.getSimpleName(), json);
        editor.commit();
    }

    public <T extends DataModel> void removeDataModel(Class<T> cls) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(cls.getSimpleName()).commit();
    }

}

