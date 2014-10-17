package it_ebook.avenwu.com.itebooks;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/7/4.
 */
public class Utils {
    private static final String TAG = Utils.class.getSimpleName();
    private static final String NAME = "config";
    private static final String KEY_SEARCH_HISTORY = "search_history";

    public static void addSearchItem(Context context, String... data) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        StringBuilder buffer = new StringBuilder(sp.getString(KEY_SEARCH_HISTORY, ""));
        for (String item : data) {
            if (!buffer.toString().contains(item)) {
                buffer.append("||").append(item);
                Log.d(TAG, "append search item:" + item);
            }
        }
        sp.edit().putString(KEY_SEARCH_HISTORY, buffer.toString()).apply();
    }

    public static String[] getSearchItems(Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        List<String> temp = new ArrayList<String>();
        for (String item : sp.getString(KEY_SEARCH_HISTORY, "").split("\\|\\|")) {
            if (!temp.contains(item)) {
                temp.add(item);
            }
        }
        String[] result = new String[temp.size()];
        temp.toArray(result);
        return result;
    }
}
