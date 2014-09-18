package com.thebiggestsaver.loaders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LoaderBroadcastReceiver extends BroadcastReceiver
{
    private BaseAsyncLoader loader;

    public LoaderBroadcastReceiver(BaseAsyncLoader loader)
    {
        this.loader = loader;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        loader.onContentChanged();
    }
}
