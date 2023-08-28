package com.appsforkids.pasz.nightlightpromax.Fragments.Images;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.appsforkids.pasz.nightlightpromax.Adapters.SampleFragmentPagerAdapter;
import com.appsforkids.pasz.nightlightpromax.Fragments.EmptyFragment;
import com.appsforkids.pasz.nightlightpromax.Fragments.Images.ImageGridFragment;
import com.appsforkids.pasz.nightlightpromax.Fragments.Images.ImageOnlineListFragment;
import com.appsforkids.pasz.nightlightpromax.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class TabImageFragment extends Fragment {

    List<Fragment> list;
    ViewPager2 pager;
    SampleFragmentPagerAdapter sampleFragmentPagerAdapter;

    public TabImageFragment() {
        super(R.layout.tab_layout_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pager = view.findViewById(R.id.pager);
        list = new ArrayList<>();
        list.add(new ImageGridFragment());
        list.add(new EmptyFragment());

        String[] tabTitle = {"Downloaded", "Online", "ss"};
        sampleFragmentPagerAdapter = new SampleFragmentPagerAdapter(getActivity(), list);
        pager.setAdapter(sampleFragmentPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {
                tab.setText(tabTitle[position]);
            }
        });
        tabLayoutMediator.attach();
    }

}
