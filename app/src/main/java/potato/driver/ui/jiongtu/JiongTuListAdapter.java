package potato.driver.ui.jiongtu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.potato.library.adapter.PotatoBaseRecyclerViewAdapter;
import com.potato.library.adapter.PotatoBaseViewHolder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import potato.driver.R;
import potato.driver.chips.common.PageCtrl;
import potato.driver.chips.util.ImageLoaderUtil;
import potato.driver.data.bean.JiongtuAlbum;

/**
 * Created by ztw on 2015/9/21.
 */
public class JiongTuListAdapter extends PotatoBaseRecyclerViewAdapter {

    public JiongTuListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(
                R.layout.item_jiongtu_list,
                parent,
                false);
        VH holder = new VH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VH vh = ((VH) holder);
        final JiongtuAlbum bean = (JiongtuAlbum) mData.get(position);

        vh.tv_title.setText(bean.getTitle());
        vh.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                PageCtrl.startJiongTuDetailActivity(context, bean);
            }
        });
        ImageLoaderUtil.displayImage(bean.getBigCover(), vh.iv_pic, R.drawable.def_gray_big);

    }


    public static class VH extends PotatoBaseViewHolder {

        @InjectView(R.id.iv_pic)
        ImageView iv_pic;
        @InjectView(R.id.tv_title)
        TextView tv_title;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
