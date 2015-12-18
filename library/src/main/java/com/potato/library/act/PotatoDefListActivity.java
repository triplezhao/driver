package com.potato.library.act;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.potato.library.adapter.BaseRecyclerViewAdapter;
import com.potato.library.data.PotatoBaseParser;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.util.L;
import com.potato.library.view.NormalEmptyView;
import com.potato.library.view.refresh.HFRecyclerSwipeLayout;
import com.potato.library.view.refresh.HFRefreshImpl;

import java.util.ArrayList;

public abstract class PotatoDefListActivity extends PotatoBaseActivity implements HFRefreshImpl {

    public static final String TAG = PotatoDefListActivity.class.getSimpleName();

    public HFRecyclerSwipeLayout mSwipeContainer;
    public RecyclerView listview;
    public NormalEmptyView mNormalEmptyView;

    public int mTotal = 0;
    public int mPage = 0;
    public ArrayList mList = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public void initListView(View view) {

        mSwipeContainer = (HFRecyclerSwipeLayout) view.findViewById(com.potato.library.R.id.swipe_container);
        listview = (RecyclerView) mSwipeContainer.findViewById(com.potato.library.R.id.list);
        mNormalEmptyView = (NormalEmptyView) view.findViewById(com.potato.library.R.id.empty_view);
        mSwipeContainer.setRecyclerView(listview, getAdapter());
        mSwipeContainer.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mSwipeContainer.setFooterView(listview, com.potato.library.R.layout.listview_footer);

        mSwipeContainer.setColorSchemeResources(com.potato.library.R.color.google_blue,
                com.potato.library.R.color.google_green,
                com.potato.library.R.color.google_red,
                com.potato.library.R.color.google_yellow);

        mSwipeContainer.setScrollStateLisener(new HFRecyclerSwipeLayout.ScrollStateLisener() {
            @Override
            public void pause() {
                ImageLoader.getInstance().pause();
            }

            @Override
            public void resume() {
                ImageLoader.getInstance().resume();
            }
        });
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reqRefresh();
            }
        });
        mSwipeContainer.setOnLoadListener(new HFRecyclerSwipeLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                reqLoadMore();
            }
        });

        mSwipeContainer.setEmptyView(mNormalEmptyView);
        mNormalEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSwipeContainer.showProgress();
                reqRefresh();
            }
        });

    }


    public abstract BaseRecyclerViewAdapter getAdapter();

    public abstract Request getRefreshRequest();

    public abstract Request getLoadMoreRequest();


    public abstract PotatoBaseParser getParser(String json);


    public void onRefreshSucc(String json) {

        PotatoBaseParser parser = getParser(json);
        if (parser.isSucc()) {

            mPage = Integer.parseInt(parser.page);
            mTotal = Integer.parseInt(parser.total);
            mList = parser.list;
            getAdapter().setDataList(mList);
            getAdapter().notifyDataSetChanged();
            if (mList != null && mList.size() != 0 && mList.size() < mTotal) {
                mSwipeContainer.setLoadEnable(true);
            }
            mSwipeContainer.showSucc();
        } else {
            mSwipeContainer.showEmptyViewFail();
        }

    }

    public void onRefreshFail(String err) {
        mSwipeContainer.showEmptyViewFail();
    }

    public void onLoadMoreSucc(String json) {
        PotatoBaseParser parser = getParser(json);
        if (parser.isSucc()) {

            mPage = Integer.parseInt(parser.page);
            mTotal = Integer.parseInt(parser.total);
            if (parser.list != null && parser.list.size() > 0) {
                mList.addAll(parser.list);
            }
            getAdapter().setDataList(mList);
            getAdapter().notifyDataSetChanged();
            if (mList == null || mList.size() == 0 || mList.size() >= mTotal) {
                mSwipeContainer.setLoadEnable(false);
            } else {
                mSwipeContainer.setLoadEnable(true);
            }
        } else {
            mSwipeContainer.showEmptyViewFail();
        }
        mSwipeContainer.setLoading(false);
    }

    public void onLoadMoreFail(String err) {
        mSwipeContainer.showEmptyViewFail();
    }

    public void onCacheLoaded(String json) {

    }

    public void reqRefresh() {

        RequestManager.DataLoadListener dataLoadListener = new RequestManager.DataLoadListener() {


            @Override
            public void onSuccess(int statusCode, String content) {
                L.e(TAG, "onSuccess" + this);
                onRefreshSucc(content);
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                L.e("拉取数据失败!!!,EMPTY_TYPE_ERROR" + this);

                onRefreshFail(errMsg);

            }

            @Override
            public void onCacheLoaded(String content) {
                L.e(TAG, "onCacheLoaded," + this);
                PotatoDefListActivity.this.onCacheLoaded(content);
            }
        };
        RequestManager.requestData(
                getRefreshRequest(),
                dataLoadListener,
                RequestManager.CACHE_TYPE_NOCACHE
        );
    }

    public void reqLoadMore() {
        RequestManager.DataLoadListener dataLoadListener = new RequestManager.DataLoadListener() {


            @Override
            public void onSuccess(int statusCode, String content) {
                L.e(TAG, "onSuccess" + this);
                onLoadMoreSucc(content);
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                L.e("拉取数据失败!!!,EMPTY_TYPE_ERROR" + this);

                onLoadMoreFail(errMsg);
            }

            @Override
            public void onCacheLoaded(String content) {
                L.e(TAG, "onCacheLoaded," + this);
                PotatoDefListActivity.this.onCacheLoaded(content);
            }
        };
        RequestManager.requestData(
                getLoadMoreRequest(),
                dataLoadListener,
                RequestManager.CACHE_TYPE_NOCACHE
        );
    }
}
