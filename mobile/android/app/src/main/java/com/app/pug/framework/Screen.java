package com.app.pug.framework;

import android.support.v4.app.Fragment;

public class Screen extends Fragment {
    /**
     * Returns the Screen Fragment's parent
     */
    public Act getParent() {
        return (Act) getActivity();
    }
}
