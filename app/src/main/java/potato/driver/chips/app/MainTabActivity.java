package potato.driver.chips.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import potato.driver.R;
import potato.driver.chips.base.BaseTabHostActivity;
import potato.driver.ui.map.MapActivity;
import potato.driver.ui.me.MeActivity;

public class MainTabActivity extends BaseTabHostActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    public TabItem getTabItemView(int position) {

        TabItem tabItem = new TabItem();
        View tabItemView = mLayoutflater.inflate(R.layout.item_main_tab, null);
        ImageView iv_icon = (ImageView) tabItemView.findViewById(R.id.iv_icon);
        switch (position) {
            case 0:
                iv_icon.setImageResource(R.drawable.selector_nav_home);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), MapActivity.class));
                break;

            case 1:

                iv_icon.setImageResource(R.drawable.selector_nav_profile);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), MeActivity.class));
                break;
        }

        return tabItem;
    }

    @Override
    public int getTabItemCount() {
        return 2;
    }

    @Override
    public void onTabChanged(String s) {

    }
}
