package com.radius;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by Pavan on 09-08-2018.
 */

public class Utils {
    public static Drawable getDrawableFromName (Context mContext,String fileName){
        Resources res = mContext.getResources();
        String mDrawableName = fileName;
        int resID = res.getIdentifier(mDrawableName , "drawable", mContext.getPackageName());
        Drawable drawable = res.getDrawable(resID );
        return drawable;
    }
    public static String replaceUnderScores(String name){
        String arr[] = name.split("-");
        String ans = arr[0];
        for(int i=1;i<arr.length;i++){
            ans = ans + "_"+arr[i];
        }
        return ans;
    }
}
