package ru.shipa.rssreader.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import ru.shipa.rssreader.R;

/**
 * Created by Vlad on 15.03.2016.
 */
public class FavouriteNewsListAdapter extends RecyclerView.Adapter<FavouriteNewsListAdapter.ViewHolder> {

        private List<String[]> mDataset;

        public static class ViewHolder extends RecyclerView.ViewHolder {
//            @Bind(R.id.tvLine)
//            TextView tvLine;
//            @Bind(R.id.tvDateLine)
//            TextView tvDateLine;
//            @Bind(R.id.tvRefresh)
//            TextView tvRefresh;


            public ViewHolder(View v) {
                super(v);
                ButterKnife.bind(this, v);
            }
        }

        public FavouriteNewsListAdapter(List<String[]> dataset) {
            mDataset = dataset;
        }

        @Override
        public FavouriteNewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.line_item_favourite_news, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.tvLine.setText(mDataset.get(position)[0]);
//            holder.tvDateLine.setText(mDataset.get(position)[1]);
//            if (holder.tvDateLine.getText() != App.getContext().getString(R.string.never)) {
//                holder.tvLine.setTextColor(App.getContext().getResources().getColor(R.color.secondary_text));
//                holder.tvRefresh.setTextColor(App.getContext().getResources().getColor(R.color.secondary_text));
//                holder.tvDateLine.setTextColor(App.getContext().getResources().getColor(R.color.secondary_text));
//            }
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
}
