package com.iniongun.dashboards.ui.url_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iniongun.dashboards.R;
import com.iniongun.dashboards.ui.main.MainActivity;
import com.iniongun.dashboards.utilities.Constants;
import com.iniongun.dashboards.utilities.SecurePreferences;
import com.iniongun.dashboards.utilities.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UrlListActivity1 extends AppCompatActivity {

    @BindView(R.id.et_url)
    EditText et_url;

    @BindView(R.id.et_timer_interval)
    EditText et_timer_interval;

    @BindView(R.id.btn_add_url)
    Button btn_add_url;

    @BindView(R.id.btn_set_timer)
    Button btn_set_timer;

    @BindView(R.id.btn_done)
    Button btn_done;

    SecurePreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard_config);

        ButterKnife.bind(this);

        setTitle("Dashboards Configuration");

        preferences = Utils.getSecurePreferences(this);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UrlListActivity1.this, MainActivity.class));
            }
        });

        btn_set_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_timer_interval.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(UrlListActivity1.this, "Please enter a value for slide timeout", Toast.LENGTH_SHORT).show();
                }else{
                    preferences.put(Constants.DASHBOARD_SLIDER_TIMER_KEY, et_timer_interval.getText().toString());
                    Toast.makeText(UrlListActivity1.this, "Timer set", Toast.LENGTH_SHORT).show();
                    et_timer_interval.setText("");
                }
            }
        });

        btn_add_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_url.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(UrlListActivity1.this, "Please enter a value for url", Toast.LENGTH_SHORT).show();
                }else{
                    preferences.put(Constants.DASHBOARD_URLS_KEY, preferences.getString(Constants.DASHBOARD_URLS_KEY) + et_url.getText().toString() + "#");
                    Toast.makeText(UrlListActivity1.this, "URL added", Toast.LENGTH_SHORT).show();
                    et_url.setText("");
                }
            }
        });

    }
}
