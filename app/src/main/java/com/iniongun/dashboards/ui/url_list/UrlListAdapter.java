package com.iniongun.dashboards.ui.url_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iniongun.dashboards.R;
import com.iniongun.dashboards.database.UrlEntity;
import com.iniongun.dashboards.viewmodels.UrlListViewModel;

import java.util.List;


class UrlListViewHolder extends RecyclerView.ViewHolder{

    public TextView tv_url;
    public ImageButton btn_remove_url;
    public LinearLayout single_url_layout;

    public UrlListViewHolder(View itemView) {
        super(itemView);
        tv_url = itemView.findViewById(R.id.tv_url);
        btn_remove_url = itemView.findViewById(R.id.btn_remove_url);
        single_url_layout = itemView.findViewById(R.id.single_url_layout);
    }
}

public class UrlListAdapter extends RecyclerView.Adapter<UrlListViewHolder> {

    private Context mContext;
    List<UrlEntity> urlEntities;
    UrlListViewModel mUrlListViewModel;

    public UrlListAdapter(Context mContext, List<UrlEntity> urlEntities, UrlListViewModel urlListViewModel) {
        this.mContext = mContext;
        this.urlEntities = urlEntities;
        this.mUrlListViewModel = urlListViewModel;
    }

    @NonNull
    @Override
    public UrlListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
        View mView = mLayoutInflater.inflate(R.layout.single_url_layout, parent, false);

        return new UrlListViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull UrlListViewHolder urlListViewHolder, final int position) {

        urlListViewHolder.tv_url.setText(urlEntities.get(position).getUrl());
        urlListViewHolder.btn_remove_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUrlListViewModel.deleteUrl(urlEntities.get(position));
                //ensure View is consistent with underlying data
                urlEntities.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return urlEntities.size();
    }
}
