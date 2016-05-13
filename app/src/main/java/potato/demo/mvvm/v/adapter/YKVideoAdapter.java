package potato.demo.mvvm.v.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import potato.demo.chips.common.PageCtrl;
import potato.demo.chips.util.ImageLoaderUtil;
import potato.demo.R;
import potato.demo.databinding.ItemYkVideoBinding;
import potato.demo.mvvm.m.bean.YKVideo;
import com.potato.library.adapter.PotatoBaseRecyclerViewAdapter;
import com.potato.library.adapter.PotatoBaseViewHolder;

/**
 * Created by ztw on 2015/9/21.
 */
public class YKVideoAdapter extends PotatoBaseRecyclerViewAdapter {

    public YKVideoAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemYkVideoBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_yk_video,
                parent,
                false);
        VH holder = new VH(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemYkVideoBinding binding = (ItemYkVideoBinding) ((VH) holder).getBinding();
        final YKVideo bean = (YKVideo) mData.get(position);
        binding.setBean(bean);
        ImageLoaderUtil.displayImage(bean.getThumbnail(), binding.ivPic, R.drawable.def_gray_big);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse(bean.getLink());
                Uri content_url = Uri.parse("https://shop108703695.taobao.com");
                intent.setData(content_url);
//                binding.getRoot().getContext().startActivity(intent);

                PageCtrl.start2WebViewActivity(binding.getRoot().getContext(), "https://shop108703695.taobao.com");
//                PageCtrl.start2SchemaPage(content_url);
//                https://shop108703695.taobao.com
            }
        });
    }


    public static class VH extends PotatoBaseViewHolder {

        private ViewDataBinding binding;

        public VH(View itemView) {
            super(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}