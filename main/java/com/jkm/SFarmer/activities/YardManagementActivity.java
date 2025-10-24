// app/src/main/java/com/yourcompany/farmmanagement/activities/YardManagementActivity.java
package com.jkm.SFarmer.activities;

import android.app.AlertDialog;
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
import com.jkm.SFarmer.adapters.YardAdapter;
import com.jkm.SFarmer.database.FarmDBHelper;
import com.jkm.SFarmer.models.Farm;
import com.jkm.SFarmer.models.Yard;
import java.util.ArrayList;
import java.util.List;

public class YardManagementActivity extends AppCompatActivity {
    
    private RecyclerView yardsRecyclerView;
    private YardAdapter yardAdapter;
    private List<Yard> yardList;
    private List<Farm> farmList;
    private FarmDBHelper dbHelper;
    private Button btnAddYard;
    private Spinner farmSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yard_management);
        
        initializeDatabase();
        initializeViews();
        setupRecyclerView();
        loadYards();
        loadFarmsForSpinner();
    }

    private void initializeDatabase() {
        dbHelper = new FarmDBHelper(this);
    }

    private void initializeViews() {
        yardsRecyclerView = findViewById(R.id.yardsRecyclerView);
        btnAddYard = findViewById(R.id.btnAddYard);
        farmSpinner = findViewById(R.id.farmSpinner);
        
        btnAddYard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddYardDialog();
            }
        });
    }

    private void setupRecyclerView() {
        yardList = new ArrayList<>();
        yardAdapter = new YardAdapter(yardList, new YardAdapter.OnYardClickListener() {
            @Override
            public void onYardClick(Yard yard) {
                showYardDetails(yard);
            }

            @Override
            public void onYardEdit(Yard yard) {
                showEditYardDialog(yard);
            }

            @Override
            public void onYardDelete(Yard yard) {
                showDeleteYardConfirmation(yard);
            }
        });
        
        yardsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        yardsRecyclerView.setAdapter(yardAdapter);
    }

    private void loadYards() {
        yardList.clear();
        // بيانات تجريبية
        Yard yard1 = new Yard();
        yard1.setYardNumber("ح1");
        yard1.setArea(5.0);
        yard1.setWorkersCount(3);
        yard1.setCropType("مانجو");
        yard1.setTreesCount(150);
        yard1.setProduction(2000.5);
        
        Yard yard2 = new Yard();
        yard2.setYardNumber("ح2");
        yard2.setArea(3.0);
        yard2.setWorkersCount(2);
        yard2.setCropType("برتقال");
        yard2.setTreesCount(100);
        yard2.setProduction(1500.0);
        
        yardList.add(yard1);
        yardList.add(yard2);
        yardAdapter.notifyDataSetChanged();
    }

    private void loadFarmsForSpinner() {
        farmList = new ArrayList<>();
        farmList.add(new Farm("مزرعة النخيل", 50.0, 10, "الجيزة"));
        farmList.add(new Farm("مزرعة الفواكه", 30.0, 8, "الفيوم"));
        
        ArrayAdapter<Farm> adapter = new ArrayAdapter<Farm>(this, 
            android.R.layout.simple_spinner_item, farmList) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((android.widget.TextView) view).setText(farmList.get(position).getName());
                return view;
            }
            
            @Override
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                ((android.widget.TextView) view).setText(farmList.get(position).getName());
                return view;
            }
        };
        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        farmSpinner.setAdapter(adapter);
    }

    private void showAddYardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("إضافة حوش جديد");
        
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_yard, null);
        
        final Spinner spinnerFarm = dialogView.findViewById(R.id.spinnerFarm);
        final EditText etYardNumber = dialogView.findViewById(R.id.etYardNumber);
        final EditText etArea = dialogView.findViewById(R.id.etYardArea);
        final EditText etWorkers = dialogView.findViewById(R.id.etWorkersCount);
        final EditText etCropType = dialogView.findViewById(R.id.etCropType);
        final EditText etTreesCount = dialogView.findViewById(R.id.etTreesCount);
        final EditText etProduction = dialogView.findViewById(R.id.etProduction);
        
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
        
        builder.setView(dialogView);
        
        builder.setPositiveButton("حفظ", (dialog, which) -> {
            Farm selectedFarm = (Farm) spinnerFarm.getSelectedItem();
            String yardNumber = etYardNumber.getText().toString();
            String areaStr = etArea.getText().toString();
            String workersStr = etWorkers.getText().toString();
            String cropType = etCropType.getText().toString();
            String treesStr = etTreesCount.getText().toString();
            String productionStr = etProduction.getText().toString();
            
            if (yardNumber.isEmpty() || areaStr.isEmpty()) {
                Toast.makeText(this, "يرجى ملء الحقول المطلوبة", Toast.LENGTH_SHORT).show();
                return;
            }
            
            Yard newYard = new Yard();
            newYard.setFarmId(selectedFarm.getId());
            newYard.setYardNumber(yardNumber);
            newYard.setArea(Double.parseDouble(areaStr));
            newYard.setWorkersCount(Integer.parseInt(workersStr));
            newYard.setCropType(cropType);
            newYard.setTreesCount(Integer.parseInt(treesStr));
            newYard.setProduction(Double.parseDouble(productionStr));
            
            yardList.add(newYard);
            yardAdapter.notifyDataSetChanged();
            
            Toast.makeText(this, "تم إضافة الحوش بنجاح", Toast.LENGTH_SHORT).show();
        });
        
        builder.setNegativeButton("إلغاء", null);
        builder.show();
    }

    private void showYardDetails(Yard yard) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("تفاصيل الحوش");
        
        String details = "رقم الحوش: " + yard.getYardNumber() + "\n" +
                        "المساحة: " + yard.getArea() + " فدان\n" +
                        "عدد العمال: " + yard.getWorkersCount() + "\n" +
                        "نوع المحصول: " + yard.getCropType() + "\n" +
                        "عدد الأشجار: " + yard.getTreesCount() + "\n" +
                        "الإنتاجية: " + yard.getProduction() + " كجم";
        
        builder.setMessage(details);
        builder.setPositiveButton("موافق", null);
        builder.show();
    }

    private void showEditYardDialog(Yard yard) {
        // تنفيذ نافذة التعديل
    }

    private void showDeleteYardConfirmation(Yard yard) {
        new AlertDialog.Builder(this)
            .setTitle("حذف الحوش")
            .setMessage("هل أنت متأكد من حذف الحوش " + yard.getYardNumber() + "؟")
            .setPositiveButton("نعم", (dialog, which) -> {
                yardList.remove(yard);
                yardAdapter.notifyDataSetChanged();
                Toast.makeText(this, "تم حذف الحوش", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("لا", null)
            .show();
    }
}