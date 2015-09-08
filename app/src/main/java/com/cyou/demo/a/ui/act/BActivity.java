package com.cyou.demo.a.ui.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.cyou.demo.R;
import com.cyou.demo.databinding.ActivityBBinding;
import com.cyou.frame.base.BaseActivity;
import com.squareup.picasso.Picasso;

public class BActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show);
//        ImageView iv = (ImageView)findViewById(R.id.iv_icon);
         ActivityBBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_b);

        Picasso.with(getBaseContext()).load("http://tvfan.kyodo.co.jp/wp-content/uploads/2015/01/15027b37a4104edd85fb5b79a6c9e3ac-344x516.jpg").placeholder(R.drawable.cheese_1).into(binding.ivIcon);
    }

}