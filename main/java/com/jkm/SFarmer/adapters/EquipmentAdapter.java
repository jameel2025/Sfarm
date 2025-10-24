package com.jkm.SFarmer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jkm.SFarmer.R;
import com.jkm.SFarmer.models.Equipment;
import java.util.List;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder> {
    
    private List<Equipment> equipmentList;
    private OnEquipmentClickListener listener;
    
    public interface OnEquipmentClickListener {
        void onEquipmentClick(Equipment equipment);
        void onEquipmentEdit(Equipment equipment);
        void onEquipmentDelete(Equipment equipment);
        void onMaintenance(Equipment equipment);
    }
    
    public EquipmentAdapter(List<Equipment> equipmentList, OnEquipmentClickListener listener) {
        this.equipmentList = equipmentList;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public EquipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_equipment, parent, false);
        return new EquipmentViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull EquipmentViewHolder holder, int position) {
        Equipment equipment = equipmentList.get(position);
        holder.bind(equipment, listener);
    }
    
    @Override
    public int getItemCount() {
        return equipmentList.size();
    }
    
    static class EquipmentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEquipmentName, tvEquipmentType, tvEquipmentCost, tvEquipmentStatus;
        private Button btnEdit, btnMaintenance, btnDelete;
        
        public EquipmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEquipmentName = itemView.findViewById(R.id.tvEquipmentName);
            tvEquipmentType = itemView.findViewById(R.id.tvEquipmentType);
            tvEquipmentCost = itemView.findViewById(R.id.tvEquipmentCost);
            tvEquipmentStatus = itemView.findViewById(R.id.tvEquipmentStatus);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnMaintenance = itemView.findViewById(R.id.btnMaintenance);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
        
        public void bind(Equipment equipment, OnEquipmentClickListener listener) {
            tvEquipmentName.setText(equipment.getName());
            tvEquipmentType.setText(equipment.getType());
            tvEquipmentCost.setText("التكلفة: " + equipment.getCost() + " جنيه");
            tvEquipmentStatus.setText("الحالة: " + equipment.getStatus());
            
            itemView.setOnClickListener(v -> listener.onEquipmentClick(equipment));
            btnEdit.setOnClickListener(v -> listener.onEquipmentEdit(equipment));
            btnMaintenance.setOnClickListener(v -> listener.onMaintenance(equipment));
            btnDelete.setOnClickListener(v -> listener.onEquipmentDelete(equipment));
        }
    }
}