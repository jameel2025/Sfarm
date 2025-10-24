package com.jkm.SFarmer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jkm.SFarmer.R;
import com.jkm.SFarmer.models.Farm;
import java.util.List;

public class FarmAdapter extends RecyclerView.Adapter<FarmAdapter.FarmViewHolder> {
    
    private List<Farm> farmList;
    private OnFarmClickListener listener;
    
    public interface OnFarmClickListener {
        void onFarmClick(Farm farm);
        void onFarmEdit(Farm farm);
        void onFarmDelete(Farm farm);
    }
    
    public FarmAdapter(List<Farm> farmList, OnFarmClickListener listener) {
        this.farmList = farmList;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public FarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_farm, parent, false);
        return new FarmViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull FarmViewHolder holder, int position) {
        Farm farm = farmList.get(position);
        holder.bind(farm, listener);
    }
    
    @Override
    public int getItemCount() {
        return farmList.size();
    }
    
    static class FarmViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFarmName, tvFarmArea, tvFarmYards;
        private Button btnEdit, btnDelete;
        
        public FarmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFarmName = itemView.findViewById(R.id.tvFarmName);
            tvFarmArea = itemView.findViewById(R.id.tvFarmArea);
            tvFarmYards = itemView.findViewById(R.id.tvFarmYards);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
        
        public void bind(Farm farm, OnFarmClickListener listener) {
            tvFarmName.setText(farm.getName());
            tvFarmArea.setText("المساحة: " + farm.getArea() + " فدان");
            tvFarmYards.setText("عدد الحوش: " + farm.getTotalYards());
            
            itemView.setOnClickListener(v -> listener.onFarmClick(farm));
            btnEdit.setOnClickListener(v -> listener.onFarmEdit(farm));
            btnDelete.setOnClickListener(v -> listener.onFarmDelete(farm));
        }
    }
}