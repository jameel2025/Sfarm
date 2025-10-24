// app/src/main/java/com/yourcompany/farmmanagement/activities/FarmManagementActivity.java
package com.jkm.SFarmer.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jkm.SFarmer.R;
import com.jkm.SFarmer.adapters.FarmAdapter;
import com.jkm.SFarmer.database.FarmDBHelper;
import com.jkm.SFarmer.models.Farm;
import java.util.ArrayList;
import java.util.List;

public class FarmManagementActivity extends AppCompatActivity {
    
    private RecyclerView farmsRecyclerView;
    private FarmAdapter farmAdapter;
    private List<Farm> farmList;
    private FarmDBHelper dbHelper;
    private Button btnAddFarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_management);
        
        initializeDatabase();
        initializeViews();
        setupRecyclerView();
        loadFarms();
    }

    private void initializeDatabase() {
        dbHelper = new FarmDBHelper(this);
    }

    private void initializeViews() {
        farmsRecyclerView = findViewById(R.id.farmsRecyclerView);
        btnAddFarm = findViewById(R.id.btnAddFarm);
        
        btnAddFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFarmDialog();
            }
        });
    }

    private void setupRecyclerView() {
        farmList = new ArrayList<>();
        farmAdapter = new FarmAdapter(farmList, new FarmAdapter.OnFarmClickListener() {
            @Override
            public void onFarmClick(Farm farm) {
                // عرض تفاصيل المزرعة
                showFarmDetails(farm);
            }

            @Override
            public void onFarmEdit(Farm farm) {
                // تعديل المزرعة
                showEditFarmDialog(farm);
            }

            @Override
            public void onFarmDelete(Farm farm) {
                // حذف المزرعة
                showDeleteConfirmationDialog(farm);
            }
        });
        
        farmsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        farmsRecyclerView.setAdapter(farmAdapter);
    }

    private void loadFarms() {
        farmList.clear();
        // هنا سيتم جلب البيانات من قاعدة البيانات
        // مؤقتاً نضيف بيانات تجريبية
        farmList.add(new Farm("مزرعة النخيل", 50.0, 10, "الجيزة"));
        farmList.add(new Farm("مزرعة الفواكه", 30.0, 8, "الفيوم"));
        farmAdapter.notifyDataSetChanged();
    }

    private void showAddFarmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("إضافة مزرعة جديدة");
        
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_farm, null);
        final EditText etName = dialogView.findViewById(R.id.etFarmName);
        final EditText etArea = dialogView.findViewById(R.id.etFarmArea);
        final EditText etYards = dialogView.findViewById(R.id.etTotalYards);
        final EditText etAddress = dialogView.findViewById(R.id.etFarmAddress);
        
        builder.setView(dialogView);
        
        builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString();
                String areaStr = etArea.getText().toString();
                String yardsStr = etYards.getText().toString();
                String address = etAddress.getText().toString();
                
                if (name.isEmpty() || areaStr.isEmpty() || yardsStr.isEmpty()) {
                    Toast.makeText(FarmManagementActivity.this, "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                double area = Double.parseDouble(areaStr);
                int yards = Integer.parseInt(yardsStr);
                
                Farm newFarm = new Farm(name, area, yards, address);
                // حفظ المزرعة في قاعدة البيانات
                // dbHelper.addFarm(newFarm);
                
                farmList.add(newFarm);
                farmAdapter.notifyDataSetChanged();
                
                Toast.makeText(FarmManagementActivity.this, "تم إضافة المزرعة بنجاح", Toast.LENGTH_SHORT).show();
            }
        });
        
        builder.setNegativeButton("إلغاء", null);
        
        builder.show();
    }

    private void showFarmDetails(Farm farm) {
        // عرض تفاصيل المزرعة
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("تفاصيل المزرعة");
        
        String details = "الاسم: " + farm.getName() + "\n" +
                        "المساحة: " + farm.getArea() + " فدان\n" +
                        "عدد الحوش: " + farm.getTotalYards() + "\n" +
                        "العنوان: " + farm.getAddress();
        
        builder.setMessage(details);
        builder.setPositiveButton("موافق", null);
        builder.show();
    }

    private void showEditFarmDialog(Farm farm) {
        // تنفيذ نافذة التعديل
    }

    private void showDeleteConfirmationDialog(Farm farm) {
        // تنفيذ نافذة تأكيد الحذف
    }
}