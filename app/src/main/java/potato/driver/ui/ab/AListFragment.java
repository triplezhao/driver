/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package potato.driver.ui.ab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.potato.library.adapter.PotatoBaseListAdapter;
import com.potato.library.view.refresh.PotatoListSwipeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import potato.driver.R;
import potato.driver.chips.base.BaseFragment;
import potato.driver.data.bean.ABean;

public class AListFragment extends BaseFragment {
    /** extrars */
    /** views */
    /** adapters */
    /** data */
    /**
     * logic
     */
    private List<ABean> mValues = new ArrayList<ABean>();
    private PotatoBaseListAdapter mAdapter;
    private View mView;

    @InjectView(R.id.swipe_container)
    PotatoListSwipeLayout swipeContainer;
    @InjectView(R.id.list)
    ListView lv_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(
                R.layout.fragment_a_list,
                container,
                false);

        ButterKnife.inject(this, mView);

        swipeContainer.setFooterView(getActivity(), lv_list, R.layout.potato_listview_footer);

        mAdapter = new AListAdapter(getActivity());
        lv_list.setAdapter(mAdapter);

        swipeContainer.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mValues = getRandomSublist(Cheeses.sCheeseStrings, 30);
                        mAdapter.setDataList(mValues);
                        mAdapter.notifyDataSetChanged();
                        swipeContainer.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        swipeContainer.setOnLoadListener(new PotatoListSwipeLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mValues.addAll(getRandomSublist(Cheeses.sCheeseStrings, 30));
                        mAdapter.setDataList(mValues);
                        mAdapter.notifyDataSetChanged();
                        swipeContainer.setLoading(false);
                    }
                }, 3000);

            }
        });
        swipeContainer.setRefreshing(true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mValues = getRandomSublist(Cheeses.sCheeseStrings, 30);
                mAdapter.setDataList(mValues);
                mAdapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        }, 3000);
        return mView;
    }

    private List<ABean> getRandomSublist(String[] array, int amount) {

        ArrayList<ABean> list = new ArrayList<ABean>(amount);
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            list.add(new ABean("title=" + i, Cheeses.getRandomIcon(), "content=" + array[random.nextInt(array.length)]));
        }
        return list;
    }


    @Override
    public void onClick(View view) {

    }


}
