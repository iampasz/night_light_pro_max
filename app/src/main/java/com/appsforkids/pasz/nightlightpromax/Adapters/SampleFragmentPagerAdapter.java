package com.appsforkids.pasz.nightlightpromax.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.appsforkids.pasz.nightlightpromax.Fragments.MelodyListFragment;

import java.util.List;

public class SampleFragmentPagerAdapter extends FragmentStateAdapter {

    List<Fragment> list;

    public SampleFragmentPagerAdapter(FragmentActivity fragmentActivity, List<Fragment> list) {
        super(fragmentActivity);

        this.list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {



      return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}