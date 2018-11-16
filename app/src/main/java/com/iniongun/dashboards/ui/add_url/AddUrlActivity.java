package com.iniongun.dashboards.ui.add_url;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iniongun.dashboards.R;
import com.iniongun.dashboards.application.DashboardApplication;
import com.iniongun.dashboards.database.UrlEntity;
import com.iniongun.dashboards.ui.url_list.UrlListActivity;
import com.iniongun.dashboards.viewmodels.AddUrlViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddUrlActivity extends AppCompatActivity {

    @BindView(R.id.et_url)
    EditText et_url;

    @BindView(R.id.btn_add_url)
    Button btn_add_url;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    AddUrlViewModel addUrlViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_url);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Dependency Injection
        ((DashboardApplication) getApplication())
                .getApplicationComponent()
                .inject(this);

        //Set up and subscribe (observe) to the ViewModel
        addUrlViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(AddUrlViewModel.class);

        btn_add_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = et_url.getText().toString();
                if (url.equalsIgnoreCase("") && !Patterns.WEB_URL.matcher(url).matches())
                    Toast.makeText(AddUrlActivity.this, "Enter a valid URL", Toast.LENGTH_SHORT).show();
                else{
                    addUrlViewModel.addUrl(new UrlEntity(url));
                    Toast.makeText(AddUrlActivity.this, "URL added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent( AddUrlActivity.this, UrlListActivity.class));
                }
            }
        });

    }
}
