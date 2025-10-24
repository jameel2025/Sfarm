package com.jkm.SFarmer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// THIS IS THE MOST IMPORTANT CORRECTION:
// It ensures we are using the R class from our own project, not the Android system R class.
import com.jkm.SFarmer.R;

import com.jkm.SFarmer.models.Employee; // Assuming you have an Employee model class

import java.util.List;

/**
 * An adapter for the RecyclerView that displays a list of employees.
 */
public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    // A list to hold the employee data that the adapter will display.
    private final List<Employee> employeeList;
    // An interface to handle click events on items in the list.
    private final OnEmployeeClickListener clickListener;

    /**
     * Constructor for the EmployeeAdapter.
     *
     * @param employeeList The list of employees to be displayed.
     * @param clickListener The listener that will handle item clicks.
     */
    public EmployeeAdapter(List<Employee> employeeList, OnEmployeeClickListener clickListener) {
        this.employeeList = employeeList;
        this.clickListener = clickListener;
    }

    /**
     * This method is called by the RecyclerView when it needs to create a new ViewHolder.
     * It inflates the layout for a single item and returns a new ViewHolder instance.
     */
    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a single employee item from the XML file.
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employee, parent, false); // Assuming your item layout is named 'item_employee.xml'
        return new EmployeeViewHolder(itemView);
    }

    /**
     * This method is called by the RecyclerView to display the data at a specific position.
     * It gets the employee data for the position and binds it to the ViewHolder's views.
     */
    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        // Get the employee object for the current position.
        Employee currentEmployee = employeeList.get(position);
        // Bind the employee data to the views in the ViewHolder.
        holder.bind(currentEmployee, clickListener);
    }

    /**
     * This method returns the total number of items in the data set held by the adapter.
     */
    @Override
    public int getItemCount() {
        return employeeList == null ? 0 : employeeList.size();
    }

    /**
     * The ViewHolder class holds the views for a single item in the RecyclerView.
     * This avoids repeated calls to findViewById(), making scrolling smoother.
     */
    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        // Declare the views that will be in each item.
        TextView tvEmployeeName;
        TextView tvEmployeeRole; // Example of another view you might have

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views by finding them in the inflated layout.
            // This is where your original error occurred. With the correct R class, this works.
            tvEmployeeName = itemView.findViewById(R.id.tvEmployeeName);
            tvEmployeeRole = itemView.findViewById(R.id.tvEmployeeRole); // Example
        }

        /**
         * A helper method to bind the data of a single employee to the views.
         *
         * @param employee The employee object containing the data.
         * @param listener The click listener to attach to the item view.
         */
        public void bind(final Employee employee, final OnEmployeeClickListener listener) {
            tvEmployeeName.setText(employee.getName()); // Assuming your Employee class has a getName() method
            tvEmployeeRole.setText(employee.getRole()); // Assuming your Employee class has a getRole() method

            // Set an OnClickListener on the entire item view.
            itemView.setOnClickListener(v -> listener.onEmployeeClick(employee));
        }
    }

    /**
     * An interface to define a click listener for items in the RecyclerView.
     * The Activity or Fragment will implement this to respond to clicks.
     */
    public interface OnEmployeeClickListener {
        void onEmployeeClick(Employee employee);
    }
}
