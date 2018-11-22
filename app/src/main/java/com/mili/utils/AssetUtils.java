package com.mili.utils;

import com.mili.app.App;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by TeeMo111 on 2018/11/22.
 */
public class AssetUtils {
    public static String getStringFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(App.getInstance().getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
