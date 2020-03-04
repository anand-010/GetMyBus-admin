package com.kaithavalappil.getmybus_admin.DataIntermediate;

import android.content.Context;

public class MainActicityContext {
    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MainActicityContext.context = context;
    }

    static Context context;
}
