package com.example.whatsapp1.main;

import android.content.Context;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsapp1.Fragment.Image;
import com.example.whatsapp1.Fragment.Private;
import com.example.whatsapp1.Fragment.Videos;
import com.example.whatsapp1.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.Image, R.string.video,R.string.private_};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        Fragment frag = null;
        switch (position){
            case 0:
                frag = Image.newInstance("","");
                break;
            case 1:
                frag = Videos.newInstance("","");
                break;
            case 2:
                frag = Private.newInstance("","");
                break;
        }
return frag;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Images";
            case 1:
                return "Videos";
            case 2:
                return "Private";
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}