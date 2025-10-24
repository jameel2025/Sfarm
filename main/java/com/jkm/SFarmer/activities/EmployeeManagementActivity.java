package com.jkm.SFarmer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jkm.SFarmer.R;
import com.jkm.SFarmer.adapters.EmployeeAdapter;
import com.jkm.SFarmer.models.Employee; // Make sure you have this Employee class

import java.util.ArrayList;
import java.util.List;

public class EmployeeManagementActivity extends AppCompatActivity {

    private RecyclerView employeesRecyclerView;
    private EmployeeAdapter employeeAdapter;
    private List<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_management);

        // 1. Initialize views
        employeesRecyclerView = findViewById(R.id.employeesRecyclerView);
        employeesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Prepare the data source
        loadEmployeeData();

        // 3. Create the adapter and the click listener
        // The listener is defined here as a new object passed into the adapter's constructor.
        employeeAdapter = new EmployeeAdapter(employeeList, new EmployeeAdapter.OnEmployeeClickListener() {
            /**
             * This @Override is CORRECT because we are implementing the onEmployeeClick method
             * from the OnEmployeeClickListener interface defined inside the EmployeeAdapter.
             * @param employee The employee object that was clicked.
             */
            @Override
            public void onEmployeeClick(Employee employee) {
                // This is where you handle what happens when an item is clicked.
                // For example, show a simple message (a "Toast").
                Toast.makeText(
                        EmployeeManagementActivity.this,
                        "You clicked on: " + employee.getName(),
                        Toast.LENGTH_SHORT
                ).show();

                // --- Optional: Example of starting a new activity ---
                // You could use this to open a details screen for the employee.
                // Intent intent = new Intent(EmployeeManagementActivity.this, EmployeeDetailActivity.class);
                // intent.putExtra("EMPLOYEE_NAME", employee.getName()); // Pass data to the next activity
                // intent.putExtra("EMPLOYEE_ROLE", employee.getRole());
                // startActivity(intent);
            }
        });

        // 4. Set the adapter on the RecyclerView to display the data
        employeesRecyclerView.setAdapter(employeeAdapter);
    }

    /**
     * A helper method to populate the employee list.
     * In a real app, you would load this data from a database, a network call, etc.
     */
    private void loadEmployeeData() {
        employeeList = new ArrayList<>();
        // Adding some sample data for demonstration purposes.
        employeeList.add(new Employee("John Doe", "Tractor Driver"));
        employeeList.add(new Employee("Jane Smith", "Field Manager"));
        employeeList.add(new Employee("Sam Wilson", "Livestock Handler"));
        employeeList.add(new Employee("Maria Garcia", "Crop Specialist"));
        employeeList.add(new Employee("Chen Wei", "Mechanic"));
    }

    // NOTE: The separate, incorrect "onEmployeeClick" method that was here before has been removed.
    // Its logic is now correctly placed inside the new EmployeeAdapter(...) call above.
}
