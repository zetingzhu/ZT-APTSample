package com.common.lib.tint.utils;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.SparseArray;

import com.common.lib.R;
import com.common.lib.tint.drawables.FilterableStateListDrawable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

class StateListDrawableInflateImpl implements DrawableInflateDelegate {

    @Override
    public Drawable inflateDrawable(Context context, XmlPullParser parser, AttributeSet attrs) throws IOException, XmlPullParserException {
        StateListDrawable sd = null;
        ArrayList<int[]> states = new ArrayList<>();
        ArrayList<Drawable> drawables = new ArrayList<>();
        SparseArray<ColorFilter> mColorFilterMap = null;
        final int innerDepth = parser.getDepth() + 1;
        int type;
        int depth;

        while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
                && ((depth = parser.getDepth()) >= innerDepth
                || type != XmlPullParser.END_TAG)) {
            if (type != XmlPullParser.START_TAG) {
                continue;
            }

            if (depth > innerDepth || !parser.getName().equals("item")) {
                continue;
            }

            Drawable dr = DrawableUtils.getAttrDrawable(context, attrs, android.R.attr.drawable);

            states.add(DrawableUtils.extractStateSet(attrs));

            // Loading child elements modifies the state of the AttributeSet's
            // underlying parser, so it needs to happen after obtaining
            // attributes and extracting states.
            if (dr == null) {
                while ((type = parser.next()) == XmlPullParser.TEXT) {
                }
                if (type != XmlPullParser.START_TAG) {
                    throw new XmlPullParserException(
                            parser.getPositionDescription()
                                    + ": <item> tag requires a 'drawable' attribute or "
                                    + "child tag defining a drawable");
                }
                dr = DrawableUtils.createFromXmlInner(context, parser, attrs);
            } else {
                ColorFilter colorFilter = DrawableUtils.getAttrColorFilter(context, attrs, R.attr.drawableTint, R.attr.drawableTintMode_new);
                if (colorFilter != null) {
                    if (mColorFilterMap == null) {
                        mColorFilterMap = new SparseArray<>();
                    }
                    mColorFilterMap.put(drawables.size(), colorFilter);
                }
            }
            drawables.add(dr);
        }
        if (states.size() >= 1) {
            if (mColorFilterMap != null) {
                sd = new FilterableStateListDrawable();
                for (int i = 0; i < states.size(); i++) {
                    ((FilterableStateListDrawable) sd).addState(states.get(i), drawables.get(i), mColorFilterMap.get(i));
                }
            } else {
                sd = new StateListDrawable();
                for (int i = 0; i < states.size(); i++) {
                    sd.addState(states.get(i), drawables.get(i));
                }
            }
        }
        return sd;
    }
}
