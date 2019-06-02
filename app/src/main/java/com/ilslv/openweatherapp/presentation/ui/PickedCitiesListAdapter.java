package com.ilslv.openweatherapp.presentation.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ilslv.openweatherapp.R;

import java.util.List;

public class PickedCitiesListAdapter extends RecyclerView.Adapter<PickedCitiesListAdapter.ViewHolder> {

    private List<String> cities;
    private OnCityClickListener listener;

    public PickedCitiesListAdapter(OnCityClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_city, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCityPicked(cities.get(holder.getAdapterPosition()));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String city = cities.get(position);
        viewHolder.cityName.setText(city);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    interface OnCityClickListener {
        void onCityPicked(String city);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView cityName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.city_name);
        }
    }
}
