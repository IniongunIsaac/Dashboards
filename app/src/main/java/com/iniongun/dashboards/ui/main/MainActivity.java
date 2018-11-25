package com.iniongun.dashboards.ui.main;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iniongun.dashboards.R;
import com.iniongun.dashboards.utilities.Constants;
import com.iniongun.dashboards.utilities.SecurePreferences;
import com.iniongun.dashboards.utilities.Utils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.btnNext)
//    FloatingActionButton btn_next;
//
//    @BindView(R.id.btnPrevious)
//    FloatingActionButton btn_prev;

    @BindView(R.id.dashboards_view_pager)
    ViewPager dashboards_view_pager;

    SecurePreferences preferences;

    ArrayList<String> urls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        preferences = Utils.getSecurePreferences(this);


        urls = getIntent().getStringArrayListExtra("URL_LIST");

        DashboardsPagerAdapter mDashboardsPagerAdapter = new DashboardsPagerAdapter(this, urls);
        dashboards_view_pager.setAdapter(mDashboardsPagerAdapter);

        dashboards_view_pager.setCurrentItem(0);

        autoSlideViewpager();

    }

    private void autoSlideViewpager(){

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            int currentPagerItem = 0, counter = 0;
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dashboards_view_pager.setCurrentItem(currentPagerItem);
                    }
                });

                currentPagerItem = (currentPagerItem >= urls.size()) ? 0 : ++currentPagerItem;

//                currentPagerItem = (++counter % 2) == 0 ? 1 : 0;
            }
        }, 0, Integer.parseInt(preferences.getString(Constants.DASHBOARD_SLIDER_TIMER_KEY)));
    }

    private int getViewPagerItem(int index) {
        return dashboards_view_pager.getCurrentItem() + index;
    }

}
