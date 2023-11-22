package com.common.lib.tint.utils;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import androidx.annotation.RestrictTo;

import java.util.LinkedList;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public class TintInfo {
    public ColorStateList mTintList;
    public PorterDuff.Mode mTintMode;
    public boolean mHasTintMode;
    public boolean mHasTintList;

    int[] mTintColors;
    int[][] mTintStates;

    public TintInfo() {

    }

    public TintInfo(LinkedList<int[]> stateList, LinkedList<Integer> colorList) {
        if (colorList == null || stateList == null) return;

        mTintColors = new int[colorList.size()];
        for (int i = 0; i < colorList.size(); i++)
            mTintColors[i] = colorList.get(i);
        mTintStates = stateList.toArray(new int[stateList.size()][]);
    }

    public boolean isInvalid() {
        return mTintColors == null || mTintStates == null || mTintColors.length != mTintStates.length;
    }
}
