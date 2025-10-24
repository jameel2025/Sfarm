// app/src/main/java/com/yourcompany/farmmanagement/activities/MainActivity.java
package com.jkm.SFarmer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import com.jkm.SFarmer.R;
import com.jkm.SFarmer.adapters.DashboardAdapter;

public class MainActivity extends AppCompatActivity {

    private GridView dashboardGrid;
    
    // أسماء وأيقونات الأقسام
    private String[] sections = {
        "المزارع", "الحوش", "الموظفين", "المعدات",
        "الحسابات", "المخازن", "الصيانة", "التسميد"
    };
    
    private int[] sectionIcons = {
        R.drawable.ic_farm, R.drawable.ic_yard, R.drawable.ic_employee, R.drawable.ic_equipment,
        R.drawable.ic_accounts, R.drawable.ic_store, R.drawable.ic_maintenance, R.drawable.ic_fertilizer
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        setupDashboard();
    }

    private void initializeViews() {
        dashboardGrid = findViewById(R.id.dashboardGrid);
    }

    private void setupDashboard() {
        DashboardAdapter adapter = new DashboardAdapter(this, sections, sectionIcons);
        dashboardGrid.setAdapter(adapter);
        
        dashboardGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleSectionClick(position);
            }
        });
    }

    private void handleSectionClick(int position) {
        Intent intent;
        switch (position) {
            case 0: // المزارع
                intent = new Intent(this, FarmManagementActivity.class);
                startActivity(intent);
                break;
            case 1: // الحوش
                intent = new Intent(this, YardManagementActivity.class);
                startActivity(intent);
                break;
            case 2: // الموظفين
                intent = new Intent(this, EmployeeManagementActivity.class);
                startActivity(intent);
                break;
            case 3: // المعدات
                intent = new Intent(this, EquipmentActivity.class);
                startActivity(intent);
                break;
            // باقي الحالات...
        }
    }
}