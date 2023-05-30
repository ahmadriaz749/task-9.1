package com.example.task91p;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.AdvertViewHolder> {

    private List<InformationOfAdvertData> informationOfAdvertDataList;
    private OnItemClickListener itemClickListener;

    public Adapter(List<InformationOfAdvertData> informationOfAdvertDataList, OnItemClickListener itemClickListener) {
        this.informationOfAdvertDataList = informationOfAdvertDataList;
        this.itemClickListener = itemClickListener;
    }

    public void setAdvertList(List<InformationOfAdvertData> informationOfAdvertDataList) {
        this.informationOfAdvertDataList = informationOfAdvertDataList;
    }

    @NonNull
    @Override
    public AdvertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advert, parent, false);
        return new AdvertViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertViewHolder holder, int position) {
        InformationOfAdvertData informationOfAdvertData = informationOfAdvertDataList.get(position);
        holder.bind(informationOfAdvertData);
    }

    @Override
    public int getItemCount() {
        return informationOfAdvertDataList.size();
    }

    public class AdvertViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewDescription;
        private TextView textViewDate;
        private TextView textViewLocation;
        private TextView textViewLostOrFound;
        private TextView textViewName;
        private TextView textViewPhone;


        public AdvertViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLostOrFound = itemView.findViewById(R.id.textViewLostOrFound);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            itemView.setOnClickListener(this);
        }

        public void bind(InformationOfAdvertData informationOfAdvertData) {
            textViewLostOrFound.setText(informationOfAdvertData.getLostOrFound());
            textViewName.setText(informationOfAdvertData.getName());
            textViewPhone.setText(informationOfAdvertData.getPhone());
            textViewDescription.setText(informationOfAdvertData.getDescription());
            textViewDate.setText(informationOfAdvertData.getDate());
            textViewLocation.setText(informationOfAdvertData.getLocation());
        }

        // Get the advert ID and pass it to the item click listener
        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    long advertId = informationOfAdvertDataList.get(position).getId();
                    itemClickListener.onItemClickListener(advertId);
                }
            }
        }
    }

    // Interface for defining the item click listener
    public interface OnItemClickListener {
        void onItemClickListener(long advertId);
    }
}
