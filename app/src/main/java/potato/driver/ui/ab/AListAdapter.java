package potato.driver.ui.ab;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.potato.library.adapter.PotatoBaseListAdapter;
import com.potato.library.adapter.PotatoBaseViewHolder;
import com.potato.library.util.L;

import butterknife.ButterKnife;
import butterknife.InjectView;
import potato.driver.R;
import potato.driver.chips.util.ImageLoaderUtil;
import potato.driver.data.bean.ABean;

/**
 * Created by ztw on 2015/9/21.
 */
public class AListAdapter extends PotatoBaseListAdapter {


    public AListAdapter(Context context) {
        super(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int type) {

        View view = mInflater.inflate(
                R.layout.item_a,
                parent,
                false);
        VH holder = new VH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VH vh = ((VH) holder);
        final ABean bean = (ABean) mData.get(position);
        vh.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ADetailActivity.class);
                intent.putExtra(ADetailActivity.EXTRA_NAME, bean.getTitle());
                context.startActivity(intent);
            }
        });
        L.i("with", bean.getIcon());

        ImageLoaderUtil.displayImage(bean.getIcon(), vh.avatar, R.drawable.def_gray_small);
    }

    public static class VH extends PotatoBaseViewHolder {
        @InjectView(R.id.tv_title)
        TextView tv_title;
        @InjectView(R.id.tv_content)
        TextView tv_content;
        @InjectView(R.id.avatar)
        ImageView avatar;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }

    }
}
