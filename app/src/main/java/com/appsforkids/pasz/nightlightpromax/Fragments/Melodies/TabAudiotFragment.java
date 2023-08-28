package com.appsforkids.pasz.nightlightpromax.Fragments.Melodies;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.appsforkids.pasz.nightlightpromax.Adapters.SampleFragmentPagerAdapter;
import com.appsforkids.pasz.nightlightpromax.Fragments.Melodies.InternetListFragment;
import com.appsforkids.pasz.nightlightpromax.Fragments.Melodies.MelodyListFragment;
import com.appsforkids.pasz.nightlightpromax.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class TabAudiotFragment extends Fragment {

    public TabAudiotFragment(){
        super(R.layout.tab_layout_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager2 pager = view.findViewById(R.id.pager);

        List<Fragment> list = new ArrayList<>();

        list.add(new MelodyListFragment());
        list.add(new InternetListFragment());
        String[] tabTitle = {"Downloaded", "Gallery"};

        SampleFragmentPagerAdapter sampleFragmentPagerAdapter = new SampleFragmentPagerAdapter(getActivity(), list);
        pager.setAdapter(sampleFragmentPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator= new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy(){

            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {
                tab.setText("Страница " + (position + 1));
                tab.setText(tabTitle[position]);
            }
        });
        tabLayoutMediator.attach();
    }
}
