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

package potato.driver.ui.jiongtu;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.potato.library.adapter.PotatoBaseListAdapter;
import com.potato.library.net.RequestManager;
import com.potato.library.net.RequestWraper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import potato.driver.R;
import potato.driver.chips.base.BaseActivity;
import potato.driver.data.bean.JiongtuAlbum;
import potato.driver.data.bean.JiongtuPhoto;
import potato.driver.data.parser.JiongtuPhotoListParser;
import potato.driver.data.request.JiongtuRequestBuilder;

public class JiongTuDetailActivity extends BaseActivity {

    public static final String TAG = "JiongTuDetailActivity";

    /**
     * extrars
     */
    public static final String EXTRA_ALBUM = "EXTRAS_ALBUM";
    /**
     * adapters
     */
    private PotatoBaseListAdapter mAdapter;
    /** data */
    /**
     * 图片数据集合
     */
    private ArrayList<JiongtuPhoto> mPhotos;
    private JiongtuAlbum mCurrentAlbum;
    private String mAlbumId;

    @InjectView(R.id.lv_list)
    ListView lv_list;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiongtu_detail);
        ButterKnife.inject(this);
        mCurrentAlbum = (JiongtuAlbum) getIntent().getSerializableExtra(EXTRA_ALBUM);
        if (mCurrentAlbum != null) {// 来自囧图图册列表
            mAlbumId = String.valueOf(mCurrentAlbum.getId());
            tv_title.setText(mCurrentAlbum.getTitle());
        }

        mAdapter = new JiongTuDetailAdapter(mContext);
        mPhotos = new ArrayList<>();
        mAdapter.setDataList(mPhotos);
        lv_list.setAdapter(mAdapter);

        bindData();

    }


    public void bindData() {
        if (!TextUtils.isEmpty(mAlbumId)) {// 来自囧图图册列表
            RequestWraper request = JiongtuRequestBuilder.getPhotoListRequest(mAlbumId);
            RequestManager.requestData(request,
                    new RequestManager.DataLoadListener() {
                        @Override
                        public void onSuccess(int statusCode, String content) {
                            onRefreshSucc(content);
                        }

                        @Override
                        public void onFailure(Throwable error, String errMsg) {
                        }

                        @Override
                        public void onCacheLoaded(String content) {
                            onRefreshSucc(content);
                        }
                    }, RequestManager.CACHE_TYPE_NORMAL);// 使用缓存
        }
    }


    private void onRefreshSucc(String content) {
        mPhotos = JiongtuPhotoListParser.parseToPhotoList(content);
        mAdapter.setDataList(mPhotos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
