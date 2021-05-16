package com.hcmus.fit.shipper.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.fragments.MapsActivity;
import com.hcmus.fit.shipper.fragments.OrderHolderFragment;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class OrderPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.free_pick, R.string.process,
            R.string.complete};
    private final MapsActivity map = new MapsActivity();
    private final Context mContext;

    public OrderPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        Log.d("order", "SectionsPagerAdapter");
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Log.d("map", "get Item " + position);

        if (position == 0) {
            return map;
        } else {
            return OrderHolderFragment.newInstance(position);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }


}