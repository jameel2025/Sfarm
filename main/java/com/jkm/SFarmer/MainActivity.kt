package com.jkm.farmmanagementsystem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jkm.farmmanagementsystem.activities.EmployeeManagementActivity
import com.jkm.farmmanagementsystem.activities.EquipmentManagementActivity
import com.jkm.farmmanagementsystem.activities.FarmManagementActivity
import com.jkm.farmmanagementsystem.activities.YardManagementActivity

/**
 * This is the main screen of the application. It now serves as a dashboard
 * to navigate to other features.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup the listener for the Employees button
        val manageEmployeesButton: Button = findViewById(R.id.btnManageEmployees)
        manageEmployeesButton.setOnClickListener {
            // Create an "Intent" to start the EmployeeManagementActivity
            val intent = Intent(this, EmployeeManagementActivity::class.java)
            startActivity(intent)
        }

        // Setup the listener for the Equipment button
        val manageEquipmentButton: Button = findViewById(R.id.btnManageEquipment)
        manageEquipmentButton.setOnClickListener {
            val intent = Intent(this, EquipmentManagementActivity::class.java)
            startActivity(intent)
        }

        // Setup the listener for the Farms button
        val manageFarmsButton: Button = findViewById(R.id.btnManageFarms)
        manageFarmsButton.setOnClickListener {
            val intent = Intent(this, FarmManagementActivity::class.java)
            startActivity(intent)
        }

        // Setup the listener for the Yards button
        val manageYardsButton: Button = findViewById(R.id.btnManageYards)
        manageYardsButton.setOnClickListener {
            val intent = Intent(this, YardManagementActivity::class.java)
            startActivity(intent)
        }
    }
}
