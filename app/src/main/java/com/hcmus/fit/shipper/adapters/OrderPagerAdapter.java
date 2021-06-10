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
import com.hcmus.fit.shipper.fragments.OrderFragment;

import java.util.ArrayList;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class OrderPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<OrderFragment> orderFragmentList = new ArrayList<>();
    private final MapsActivity map = new MapsActivity();
    private final Context mContext;

    public OrderPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        Log.d("order", "SectionsPagerAdapter");
        mContext = context;

        OrderFragment orderFragment1 = new OrderFragment(R.string.process);
        orderFragmentList.add(orderFragment1);

        OrderFragment orderFragment2 = new OrderFragment(R.string.complete);
        orderFragmentList.add(orderFragment2);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Log.d("map", "get Item " + position);

        if (position == 0) {
            return map;
        } else {
            return this.orderFragmentList.get(position - 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getResources().getString(R.string.free_pick);
        } else {
            return mContext.getResources().getString(this.orderFragmentList.get(position - 1).getTitle());
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }


}