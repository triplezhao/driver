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

package potato.demo.mvvm.v.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import potato.demo.chips.base.BaseDefaultListFragment;
import potato.demo.chips.base.BaseParser;
import potato.demo.R;
import potato.demo.databinding.FragmentJiongtuListBinding;
import potato.demo.mvvm.m.request.JiongtuRequestBuilder;
import potato.demo.mvvm.m.bean.JiongtuAlbum;
import potato.demo.mvvm.m.parser.JiongtuAlbumListParser;
import potato.demo.mvvm.v.adapter.JiongTuListAdapter;
import com.potato.library.adapter.PotatoBaseRecyclerViewAdapter;
import com.potato.library.net.Request;
import com.potato.library.view.hfrecyclerview.HFGridlayoutSpanSizeLookup;

import java.util.ArrayList;

public class JiongTuListFragment extends BaseDefaultListFragment {
    private static final String TAG = "ListFragmentJiongtu";
    /**
     * extrars
     */
    public static final String EXTRARS_SECTION_ID = "EXTRARS_SECTION_ID";
    public static final String EXTRARS_TITLE = "EXTRARS_TITLE";
    private long mSectionId;
    private String mTitle;
    private ArrayList<JiongtuAlbum> mList = new ArrayList<JiongtuAlbum>();
    private PotatoBaseRecyclerViewAdapter mAdapter;
    private JiongtuAlbumListParser mParser;
    private FragmentJiongtuListBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mSectionId = getArguments() == null ? 0 : getArguments().getLong(EXTRARS_SECTION_ID);
        mTitle = getArguments() == null ? "" : getArguments().getString(EXTRARS_TITLE);


        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.fragment_jiongtu_list,
                container,
                false);

        mAdapter = new JiongTuListAdapter(mContext);
        mParser = new JiongtuAlbumListParser();

        initListView(mBinding.includeA.llSwipe);

        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        manager.setSpanSizeLookup(new HFGridlayoutSpanSizeLookup(mSwipeContainer.getHFAdapter(), manager.getSpanCount()) {
            @Override
            public int getSpanSize(int position) {
                boolean isHeaderOrFooter = adapter.isHeader(position) || adapter.isFooter(position);
                if (isHeaderOrFooter) {
                    return mSpanSize;
                } else if (position % 3 == 0) {
                    return mSpanSize;
                } else {
                    return 1;
                }
            }
        });
        mSwipeContainer.setLayoutManager(manager);
        mSwipeContainer.showProgress();
        reqRefresh();

        return mBinding.getRoot();
    }


    @Override
    public PotatoBaseRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public Request getRefreshRequest() {
        return JiongtuRequestBuilder.getAlbumListRequest(mSectionId, 0);
    }

    @Override
    public Request getLoadMoreRequest() {
        return JiongtuRequestBuilder.getAlbumListRequest(mSectionId, mParser.maxPublicDate);
    }

    public BaseParser getParser(String json) {
        return null;
    }

    @Override
    public void onRefreshSucc(String content) {
        mSwipeContainer.showSucc();
        mList = mParser.parseToAlbumList(content);
        mAdapter.setDataList(mList);
        mAdapter.notifyDataSetChanged();
        mSwipeContainer.setRefreshing(false);
        if (mList != null && mList.size() != 0) {
            mSwipeContainer.setLoadEnable(true);
        }

    }

    @Override
    public void onLoadMoreSucc(String json) {
        mSwipeContainer.setLoading(false);
        ArrayList<JiongtuAlbum> moreData = mParser.parseToAlbumList(json);
        if (moreData == null || moreData.size() == 0) {
            mSwipeContainer.setLoadEnable(false);
            return;
        }
        mList.addAll(moreData);
        mAdapter.setDataList(mList);
        mAdapter.notifyItemInserted(mList.size() - 1);
    }

    @Override
    public void onClick(View view) {

    }
}
