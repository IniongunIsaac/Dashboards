package com.iniongun.dashboards;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnNext)
    FloatingActionButton btn_next;

    @BindView(R.id.btnPrevious)
    FloatingActionButton btn_prev;

    @BindView(R.id.dashboards_view_pager)
    ViewPager dashboards_view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DashboardsPagerAdapter mDashboardsPagerAdapter = new DashboardsPagerAdapter(this);
        dashboards_view_pager.setAdapter(mDashboardsPagerAdapter);

        dashboards_view_pager.setCurrentItem(0);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getViewPagerItem(+1) <= 2)
                    dashboards_view_pager.setCurrentItem(getViewPagerItem(+1), true);
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getViewPagerItem(-1) >= 0)
                    dashboards_view_pager.setCurrentItem(getViewPagerItem(-1), true);
            }
        });

    }

    private int getViewPagerItem(int index) {
        return dashboards_view_pager.getCurrentItem() + index;
    }

}
