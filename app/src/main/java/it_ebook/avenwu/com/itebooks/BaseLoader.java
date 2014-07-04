package it_ebook.avenwu.com.itebooks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Chaobin on 2014/7/4.
 */
public abstract class BaseLoader<D> extends AsyncTaskLoader<D> {
    public BaseLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public void deliverResult(D data) {
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }
}
