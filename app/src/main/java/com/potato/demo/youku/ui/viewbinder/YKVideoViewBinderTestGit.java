package com.potato.demo.youku.ui.viewbinder;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.demo.R;
import com.potato.demo.databinding.ItemYkVideoBinding;
import com.potato.demo.youku.data.bean.YKVideo;
import com.potato.frame.base.BaseViewBinder;
import com.potato.frame.base.BaseViewHolder;
import com.potato.frame.common.PageCtrl;
import com.potato.frame.util.ImageLoaderUtil;

/**
 * Created by ztw on 2015/9/8.
 */
public class YKVideoViewBinderTestGit extends BaseViewBinder<YKVideoViewBinderTestGit.ViewHolder> {

    public YKVideoViewBinderTestGit() {
    }

    @Override
    public ViewHolder onCreateViewHolder(int position, int type, ViewGroup parent) {

        ItemYkVideoBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_yk_video,
                parent,
                false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Object object, int position, int type) {
        final ItemYkVideoBinding binding = (ItemYkVideoBinding) holder.getBinding();
        final YKVideo bean = (YKVideo) object;
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

//                PageCtrl.start2WebViewActivity(binding.getRoot().getContext(),"https://shop108703695.taobao.com");
//                PageCtrl.start2SchemaPage(content_url);
//                https://shop108703695.taobao.com
                /**
                 * 跳转到播放界面进行播放
                 */

                PageCtrl.startYKPlayerActivity(binding.getRoot().getContext(), bean.getId(), bean.getTitle());

            }
        });

    }

    public static class ViewHolder extends BaseViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(View itemView) {
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
