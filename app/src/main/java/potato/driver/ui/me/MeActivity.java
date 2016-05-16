package potato.driver.ui.me;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import potato.driver.R;
import potato.driver.chips.base.BaseActivity;
import potato.driver.chips.common.PageCtrl;
import potato.driver.data.db.DriverBeanDao;

public class MeActivity extends BaseActivity {


    /*@InjectView(R.id.bt_login)
    Button bt_login;
    @InjectView(R.id.bt_logout)
    Button bt_logout;*/
    @InjectView(R.id.ll_logined)
    View ll_logined;
    @InjectView(R.id.ll_unlogin)
    View ll_unlogin;

    @InjectView(R.id.tv_name)
    TextView tv_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        ButterKnife.inject(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshUserInfo();

    }

    private void refreshUserInfo() {
        //如果登录了，就显示user信息
        if (new DriverBeanDao(getApplication()).isLogin()) {
            ll_logined.setVisibility(View.VISIBLE);
            ll_unlogin.setVisibility(View.GONE);

            tv_name.setText(new DriverBeanDao(getApplication()).getLoginedDiver().getUsername());

        } else {
            ll_logined.setVisibility(View.GONE);
            ll_unlogin.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.bt_login)
    public void clickLogin(View view) {
        PageCtrl.start2Login(this);
    }

    @OnClick(R.id.bt_logout)
    public void clickLogout(View view) {
        new DriverBeanDao(this).logoutDiver();
        refreshUserInfo();
    }

}
