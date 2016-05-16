package potato.driver.ui.appstore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mozillaonline.downloadprovider.DownLoadUtil;
import com.mozillaonline.providers.downloads.ui.DownloadListActivity;
import com.potato.library.adapter.PotatoBaseRecyclerViewAdapter;
import com.potato.library.util.L;

import butterknife.ButterKnife;
import butterknife.InjectView;
import potato.driver.R;
import potato.driver.chips.util.ImageLoaderUtil;
import potato.driver.data.bean.AppBean;

/**
 * Created by ztw on 2015/9/21.
 */
public class AppListAdapter extends PotatoBaseRecyclerViewAdapter {

    public AppListAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(
                R.layout.item_app_list,
                parent,
                false);
        VH holder = new VH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VH vh = ((VH) holder);
        final AppBean bean = (AppBean) mData.get(position);
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, DownloadListActivity.class);
                mContext.startActivity(intent);
            }
        });
        vh.tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                DownLoadUtil.startDownload(context, bean.getApkUrl(), bean.getTitle());
            }
        });
        L.i("with", bean.getIcon());

        ImageLoaderUtil.displayImage(bean.getIcon(), vh.iv_pic, R.drawable.def_gray_small);
    }


    public static class VH extends ViewHolder {

        @InjectView(R.id.iv_pic)
        RoundedImageView iv_pic;
        @InjectView(R.id.tv_download)
        TextView tv_download;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
