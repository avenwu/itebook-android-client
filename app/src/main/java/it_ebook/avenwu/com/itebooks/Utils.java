package it_ebook.avenwu.com.itebooks;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Administrator on 2014/7/4.
 */
public class Utils {
    private static final String TAG = Utils.class.getSimpleName();
    private static final String NAME = "config";
    private static final String KEY_SEARCH_HISTORY = "search_history";

    public static void addSearchItem(Context context, String... data) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        StringBuffer buffer = new StringBuffer(sp.getString(KEY_SEARCH_HISTORY, ""));
        for (String item : data) {
            buffer.append("||").append(item);
            Log.d(TAG, "append search item:" + item);
        }
        sp.edit().putString(KEY_SEARCH_HISTORY, buffer.toString()).commit();
    }

    public static String[] getSearchItems(Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String items = sp.getString(KEY_SEARCH_HISTORY, "");
        return items.split("\\|\\|");
    }
}
