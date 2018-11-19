package com.iniongun.dashboards.ui.url_list;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.iniongun.dashboards.R;
import com.iniongun.dashboards.application.DashboardApplication;
import com.iniongun.dashboards.database.UrlEntity;
import com.iniongun.dashboards.ui.add_url.AddUrlActivity;
import com.iniongun.dashboards.ui.main.MainActivity;
import com.iniongun.dashboards.utilities.Constants;
import com.iniongun.dashboards.utilities.SecurePreferences;
import com.iniongun.dashboards.utilities.Utils;
import com.iniongun.dashboards.viewmodels.UrlListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UrlListActivity extends AppCompatActivity {

    @BindView(R.id.url_list_recycler_view) RecyclerView url_list_recycler_view;

    @BindView(R.id.btn_add_url) FloatingActionButton btn_add_url;

    @BindView(R.id.btn_start_url_activity) ImageView btn_start_url_activity;

    @BindView(R.id.btn_timer_settings) FloatingActionButton btn_timer_settings;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    
    UrlListViewModel urlListViewModel;
    
    private List<UrlEntity> urlEntities;

    UrlListAdapter adapter;

    SecurePreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_url_list);

        ButterKnife.bind(this);

        preferences = Utils.getSecurePreferences(this);

        //set default timer value
        preferences.put(Constants.DASHBOARD_SLIDER_TIMER_KEY, "5000");

        ActionBar actionBar = getSupportActionBar();
        
        //Dependency Injection
        ((DashboardApplication) getApplication())
                .getApplicationComponent()
                .inject(this);
        
        //Set up and subscribe (observe) to the ViewModel
        urlListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(UrlListViewModel.class);
        
        urlListViewModel.getUrls().observe(this, new Observer<List<UrlEntity>>() {
            @Override
            public void onChanged(@Nullable List<UrlEntity> urlEntities) {
                if (UrlListActivity.this.urlEntities == null){
                    setListData(urlEntities);
                }
            }
        });

        btn_add_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UrlListActivity.this, AddUrlActivity.class));
            }
        });

        btn_start_url_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UrlListActivity.this.urlEntities.size() > 0){
                    Intent intent = new Intent(UrlListActivity.this, MainActivity.class);
                    ArrayList<String> urls = new ArrayList<>();

                    for (UrlEntity url: UrlListActivity.this.urlEntities)
                        urls.add(url.getUrl());

                    intent.putStringArrayListExtra("URL_LIST", urls);

                    startActivity(intent);
                }else{
                    Toast.makeText(UrlListActivity.this, "Cannot start activity, please add at least one URL.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_timer_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UrlListActivity.this);
                dialog.setContentView(R.layout.timer_layout);
                dialog.setTitle("Set Timer");

                final EditText et_timer = dialog.findViewById(R.id.et_timer_interval);
                Button btn_set_timer = dialog.findViewById(R.id.btn_set_timer);

                btn_set_timer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String timer_text = et_timer.getText().toString();
                        if (!timer_text.equalsIgnoreCase("")){
                            preferences.put(Constants.DASHBOARD_SLIDER_TIMER_KEY, timer_text);
                            Toast.makeText(UrlListActivity.this, "Timer set.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else{
                            Toast.makeText(UrlListActivity.this, "Please enter timer value.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });

    }

    private void setListData(List<UrlEntity> urlEntities) {

        UrlListActivity.this.urlEntities = urlEntities;

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        url_list_recycler_view.setLayoutManager(layoutManager);
        adapter = new UrlListAdapter(this, UrlListActivity.this.urlEntities, urlListViewModel);
        url_list_recycler_view.setAdapter(adapter);


        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                url_list_recycler_view.getContext(),
                layoutManager.getOrientation()
        );

        itemDecoration.setDrawable(
                ContextCompat.getDrawable(
                        this,
                        R.drawable.divider_white
                )
        );

        url_list_recycler_view.addItemDecoration(
                itemDecoration
        );


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeLeftOrRightToDeleteItemTouchHelperCallback());
        itemTouchHelper.attachToRecyclerView(url_list_recycler_view);

    }

    //ItemTouchHelper for swipe left or right to remove item from list
    private ItemTouchHelper.Callback swipeLeftOrRightToDeleteItemTouchHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                urlListViewModel.deleteUrl(urlEntities.get(position));

                //ensure View is consistent with underlying data
                urlEntities.remove(position);
                adapter.notifyItemRemoved(position);


            }
        };
        return simpleItemTouchCallback;
    }

}

    
