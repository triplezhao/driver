package potato.driver.ui.ab;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.potato.library.view.refresh.PotatoRecyclerSwipeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import potato.driver.R;
import potato.driver.chips.base.BaseActivity;
import potato.driver.data.bean.ABean;

public class BActivity extends BaseActivity {

    BListAdapter mAdapter;
    List<ABean> mValues = new ArrayList<ABean>();
    @InjectView(R.id.swipe_container)
    PotatoRecyclerSwipeLayout swipeContainer;
    @InjectView(R.id.list)
    RecyclerView lv_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show);
//        ImageView iv = (ImageView)findViewById(R.id.iv_icon);
        setContentView(R.layout.activity_b);
        ButterKnife.inject(this);
        mAdapter = new BListAdapter(mContext);

        swipeContainer.setRecyclerView(lv_list, mAdapter);
        swipeContainer.setLayoutManager(new LinearLayoutManager(mContext));
        swipeContainer.setFooterView(lv_list, R.layout.potato_listview_footer);

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
//                        mAdapter.notifyDataSetChanged();
                        swipeContainer.setRefreshing(false);
                    }
                }, 300);
            }
        });
        swipeContainer.setOnLoadListener(new PotatoRecyclerSwipeLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mValues.addAll(getRandomSublist(Cheeses.sCheeseStrings, 30));
                        int lastCount = mValues.size();
                        mAdapter.setDataList(mValues);
//                        mAdapter.notifyDataSetChanged();
                        mAdapter.notifyItemInserted(lastCount);
                        swipeContainer.setLoading(false);
                    }
                }, 300);

            }
        });

        swipeContainer.setScrollStateLisener(new PotatoRecyclerSwipeLayout.ScrollStateLisener() {
            @Override
            public void pause() {
                ImageLoader.getInstance().pause();

            }

            @Override
            public void resume() {
                ImageLoader.getInstance().resume();
            }
        });

        swipeContainer.setRefreshing(true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mValues = getRandomSublist(Cheeses.sCheeseStrings, 30);
                mAdapter.setDataList(mValues);
                swipeContainer.setRefreshing(false);
            }
        }, 300);
    }

    private List<ABean> getRandomSublist(String[] array, int amount) {

        ArrayList<ABean> list = new ArrayList<ABean>(amount);
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            list.add(new ABean("title=" + i, Cheeses.getRandomIcon(), "content=" + array[random.nextInt(array.length)]));
        }
        return list;
    }

}
