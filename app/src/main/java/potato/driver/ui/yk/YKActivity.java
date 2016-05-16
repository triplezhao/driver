package potato.driver.ui.yk;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.potato.library.adapter.PotatoBaseRecyclerViewAdapter;
import com.potato.library.net.RequestWraper;
import com.potato.library.view.hfrecyclerview.HFGridlayoutSpanSizeLookup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import potato.driver.R;
import potato.driver.chips.app.GlobleConstant;
import potato.driver.chips.base.BaseDefaultListActivity;
import potato.driver.chips.base.BaseParser;
import potato.driver.data.parser.YKVideosByUserParser;
import potato.driver.data.request.YKRequestBuilder;

/**
 * Created by ztw on 2015/7/3.
 */
public class YKActivity extends BaseDefaultListActivity {

    public static final String TAG = YKActivity.class.getSimpleName();

    private PotatoBaseRecyclerViewAdapter mAdapter;

    @InjectView(R.id.include_a)
    LinearLayout include_a;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yk_videos);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);

        mAdapter = new YKVideoAdapter(mContext);

        initListView(include_a);

        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        manager.setSpanSizeLookup(new HFGridlayoutSpanSizeLookup(mSwipeContainer.getHFAdapter(), manager.getSpanCount()) {
            @Override
            public int getSpanSize(int position) {
                boolean isHeaderOrFooter = adapter.isHeader(position) || adapter.isFooter(position);
                if (isHeaderOrFooter) {
                    return mSpanSize;
                } else {
                    return (3 - position % 3);
                }
            }
        });
        mSwipeContainer.setLayoutManager(manager);
        mSwipeContainer.showProgress();
        reqRefresh();
    }

    @Override
    public PotatoBaseRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public RequestWraper getRefreshRequest() {
        return YKRequestBuilder.videosByUser(GlobleConstant.youku_client_id, GlobleConstant.youku_def_uid, "", "", "1", "", "");
    }

    @Override
    public RequestWraper getLoadMoreRequest() {
        return YKRequestBuilder.videosByUser(GlobleConstant.youku_client_id, GlobleConstant.youku_def_uid, "", "", mPage + 1 + "", "", "");
    }

    public BaseParser getParser(String json) {
        return new YKVideosByUserParser(json);
    }


}
