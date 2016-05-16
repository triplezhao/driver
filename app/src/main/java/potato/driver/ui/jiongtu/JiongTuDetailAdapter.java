package potato.driver.ui.jiongtu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.potato.library.adapter.PotatoBaseListAdapter;
import com.potato.library.adapter.PotatoBaseViewHolder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import potato.driver.R;
import potato.driver.chips.util.ImageLoaderUtil;
import potato.driver.chips.util.ShareUtil;
import potato.driver.data.bean.JiongtuPhoto;

/**
 * Created by ztw on 2015/9/21.
 */
public class JiongTuDetailAdapter extends PotatoBaseListAdapter {

    public JiongTuDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int type) {

        View view = mInflater.inflate(
                R.layout.item_jiongtu_detail,
                parent,
                false);
        VH holder = new VH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VH vh = ((VH) holder);
        final JiongtuPhoto bean = (JiongtuPhoto) mData.get(position);
//        L.i("Picasso", "URL," + bean.getBigUrl());
        vh.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtil.shareImage(view.getContext(), bean.getBigUrl());
            }
        });
        ImageLoaderUtil.displayImage(bean.getBigUrl(), vh.iv_pic, R.drawable.def_gray_big);


    }

    public static class VH extends PotatoBaseViewHolder {

        @InjectView(R.id.iv_pic)
        ImageView iv_pic;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
