package com.hcmus.fit.shipper.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.fragments.DetailOrderFragment;
import com.hcmus.fit.shipper.models.OrderModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DetailPagerAdapter extends FragmentPagerAdapter {

    private final List<DetailOrderFragment> detailOrderList = new ArrayList<>();
    private final Context mContext;

    public DetailPagerAdapter(Context context, FragmentManager fm, OrderModel orderModel) {
        super(fm);
        Log.d("order detail", "DetailPagerAdapter");
        mContext = context;

        DetailOrderFragment merchant = new DetailOrderFragment(R.string.merchant, orderModel);
        DetailOrderFragment customer = new DetailOrderFragment(R.string.customer, orderModel);
        detailOrderList.add(merchant);
        detailOrderList.add(customer);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        return this.detailOrderList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(detailOrderList.get(position).getTitle());
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return this.detailOrderList.size();
    }


}