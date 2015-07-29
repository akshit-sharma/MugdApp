package org.mugd.mugdapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

/**
 * Created by akshi_000 on 29-07-2015.
 */
public class EventFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;

    public EventFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ArrayListFragment.newInstance(0);
            case 1:
                return ArrayListFragment.newInstance(1);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}



