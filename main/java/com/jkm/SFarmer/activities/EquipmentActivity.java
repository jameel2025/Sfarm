// app/src/main/java/com/yourcompany/farmmanagement/activities/EquipmentActivity.java
package com.jkm.SFarmer.activities;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jkm.SFarmer.R;
import com.jkm.SFarmer.adapters.EquipmentAdapter;
import com.jkm.SFarmer.database.FarmDBHelper;
import com.jkm.SFarmer.models.Equipment;
import com.jkm.SFarmer.models.Farm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EquipmentActivity extends AppCompatActivity {
    
    private RecyclerView equipmentRecyclerView;
    private EquipmentAdapter equipmentAdapter;
    private List<Equipment> equipmentList;
    private List<Farm> farmList;
    private FarmDBHelper dbHelper;
    private Button btnAddEquipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        
        initializeDatabase();
        initializeViews();
        setupRecyclerView();
        loadEquipment();
        loadFarmsForSpinner();
    }

    private void initializeDatabase() {
        dbHelper = new FarmDBHelper(this);
    }

    private void initializeViews() {
        equipmentRecyclerView = findViewById(R.id.equipmentRecyclerView);
        btnAddEquipment = findViewById(R.id.btnAddEquipment);
        
        btnAddEquipment.setOnClickListener(v -> showAddEquipmentDialog());
    }

    private void setupRecyclerView() {
        equipmentList = new ArrayList<>();
        equipmentAdapter = new EquipmentAdapter(equipmentList, new EquipmentAdapter.OnEquipmentClickListener() {
            @Override
            public void onEquipmentClick(Equipment equipment) {
                showEquipmentDetails(equipment);
            }

            @Override
            public void onEquipmentEdit(Equipment equipment) {
                showEditEquipmentDialog(equipment);
            }

            @Override
            public void onEquipmentDelete(Equipment equipment) {
                showDeleteEquipmentConfirmation(equipment);
            }

            @Override
            public void onMaintenance(Equipment equipment) {
                scheduleMaintenance(equipment);
            }
        });
        
        equipmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        equipmentRecyclerView.setAdapter(equipmentAdapter);
    }

    private void loadEquipment() {
        equipmentList.clear();
        // بيانات تجريبية
        equipmentList.add(new Equipment("جرار زراعي", "آلات", 150000.0, "نشط"));
        equipmentList.add(new Equipment("رشاش", "ري", 50000.0, "تحت الصيانة"));
        equipmentList.add(new Equipment("حصادة", "حصاد", 200000.0, "نشط"));
        equipmentAdapter.notifyDataSetChanged();
    }

    private void loadFarmsForSpinner() {
        farmList = new ArrayList<>();
        farmList.add(new Farm("مزرعة النخيل", 50.0, 10, "الجيزة"));
        farmList.add(new Farm("مزرعة الفواكه", 30.0, 8, "الفيوم"));
    }

    private void showAddEquipmentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("إضافة معدات جديدة");
        
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_equipment, null);
        
        final Spinner spinnerFarm = dialogView.findViewById(R.id.spinnerFarm);
        final EditText etName = dialogView.findViewById(R.id.etEquipmentName);
        final EditText etType = dialogView.findViewById(R.id.etEquipmentType);
        final EditText etCost = dialogView.findViewById(R.id.etEquipmentCost);
        final Spinner spinnerStatus = dialogView.findViewById(R.id.spinnerStatus);
        
        // تعبئة spinner بالمزارع
        ArrayAdapter<Farm> farmAdapter = new ArrayAdapter<Farm>(this, 
            android.R.layout.simple_spinner_item, farmList) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((android.widget.TextView) view).setText(farmList.get(position).getName());
                return view;
            }
        };
        farmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFarm.setAdapter(farmAdapter);
        
        // تعبئة spinner بالحالات
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
            R.array.equipment_status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);
        
        builder.setView(dialogView);
        
        builder.setPositiveButton("حفظ", (dialog, which) -> {
            Farm selectedFarm = (Farm) spinnerFarm.getSelectedItem();
            String name = etName.getText().toString();
            String type = etType.getText().toString();
            String costStr = etCost.getText().toString();
            String status = spinnerStatus.getSelectedItem().toString();
            
            if (name.isEmpty() || type.isEmpty() || costStr.isEmpty()) {
                Toast.makeText(this, "يرجى ملء الحقول المطلوبة", Toast.LENGTH_SHORT).show();
                return;
            }
            
            Equipment newEquipment = new Equipment();
            newEquipment.setFarmId(selectedFarm.getId());
            newEquipment.setName(name);
            newEquipment.setType(type);
            newEquipment.setCost(Double.parseDouble(costStr));
            newEquipment.setStatus(status);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                newEquipment.setPurchaseDate(LocalDate.now().toString());
            }

            equipmentList.add(newEquipment);
            equipmentAdapter.notifyDataSetChanged();
            
            Toast.makeText(this, "تم إضافة المعدات بنجاح", Toast.LENGTH_SHORT).show();
        });
        
        builder.setNegativeButton("إلغاء", null);
        builder.show();
    }

    private void showEquipmentDetails(Equipment equipment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("تفاصيل المعدات");
        
        String details = "الاسم: " + equipment.getName() + "\n" +
                        "النوع: " + equipment.getType() + "\n" +
                        "التكلفة: " + equipment.getCost() + " جنيه\n" +
                        "الحالة: " + equipment.getStatus() + "\n" +
                        "تاريخ الشراء: " + equipment.getPurchaseDate() + "\n" +
                        "آخر صيانة: " + equipment.getMaintenanceDate();
        
        builder.setMessage(details);
        builder.setPositiveButton("موافق", null);
        builder.show();
    }

    private void scheduleMaintenance(Equipment equipment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("جدولة صيانة");
        builder.setMessage("هل تريد جدولة صيانة للمعدات " + equipment.getName() + "؟");
        
        builder.setPositiveButton("نعم", (dialog, which) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                equipment.setMaintenanceDate(LocalDate.now().toString());
            }
            equipment.setStatus("تحت الصيانة");
            equipmentAdapter.notifyDataSetChanged();
            Toast.makeText(this, "تم جدولة الصيانة", Toast.LENGTH_SHORT).show();
        });
        
        builder.setNegativeButton("لا", null);
        builder.show();
    }

    private void showEditEquipmentDialog(Equipment equipment) {
        // تنفيذ نافذة التعديل
    }

    private void showDeleteEquipmentConfirmation(Equipment equipment) {
        new AlertDialog.Builder(this)
            .setTitle("حذف المعدات")
            .setMessage("هل أنت متأكد من حذف المعدات " + equipment.getName() + "؟")
            .setPositiveButton("نعم", (dialog, which) -> {
                equipmentList.remove(equipment);
                equipmentAdapter.notifyDataSetChanged();
                Toast.makeText(this, "تم حذف المعدات", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("لا", null)
            .show();
    }
}