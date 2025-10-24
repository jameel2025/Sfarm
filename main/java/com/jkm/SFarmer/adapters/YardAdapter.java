// app/src/main/java/com/yourcompany/farmmanagement/adapters/YardAdapter.java
package com.jkm.SFarmer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jkm.SFarmer.R;
import com.jkm.SFarmer.models.Yard;
import java.util.List;

public class YardAdapter extends RecyclerView.Adapter<YardAdapter.YardViewHolder> {
    
    private List<Yard> yardList;
    private OnYardClickListener listener;
    
    public interface OnYardClickListener {
        void onYardClick(Yard yard);
        void onYardEdit(Yard yard);
        void onYardDelete(Yard yard);
    }
    
    public YardAdapter(List<Yard> yardList, OnYardClickListener listener) {
        this.yardList = yardList;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public YardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_yard, parent, false);
        return new YardViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull YardViewHolder holder, int position) {
        Yard yard = yardList.get(position);
        holder.bind(yard, listener);
    }
    
    @Override
    public int getItemCount() {
        return yardList.size();
    }
    
    static class YardViewHolder extends RecyclerView.ViewHolder {
        private TextView tvYardNumber, tvCropType, tvArea, tvProduction;
        
        public YardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvYardNumber = itemView.findViewById(R.id.tvYardNumber);
            tvCropType = itemView.findViewById(R.id.tvCropType);
            tvArea = itemView.findViewById(R.id.tvArea);
            tvProduction = itemView.findViewById(R.id.tvProduction);
        }
        
        public void bind(Yard yard, OnYardClickListener listener) {
            tvYardNumber.setText(yard.getYardNumber());
            tvCropType.setText(yard.getCropType());
            tvArea.setText("المساحة: " + yard.getArea() + " فدان");
            tvProduction.setText("الإنتاج: " + yard.getProduction() + " كجم");
            
            itemView.setOnClickListener(v -> listener.onYardClick(yard));
            
            itemView.findViewById(R.id.btnEdit).setOnClickListener(v -> 
                listener.onYardEdit(yard));
                
            itemView.findViewById(R.id.btnDelete).setOnClickListener(v -> 
                listener.onYardDelete(yard));
        }
    }
}