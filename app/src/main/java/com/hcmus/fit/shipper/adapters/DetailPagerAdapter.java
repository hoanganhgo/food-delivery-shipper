package com.hcmus.fit.shipper.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.fragments.DetailHolderFragment;
import com.hcmus.fit.shipper.fragments.MapsActivity;
import com.hcmus.fit.shipper.fragments.OrderHolderFragment;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DetailPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.merchant, R.string.customer};
    private final Context mContext;

    public DetailPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        Log.d("order detail", "DetailPagerAdapter");
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        return DetailHolderFragment.newInstance(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }


}