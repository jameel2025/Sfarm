package com.jkm.SFarmer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityManager {
    
    // مفاتيح التخزين
    private static final String PREFS_NAME = "FarmSecurityPrefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ROLE = "user_role";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_PERMISSIONS = "user_permissions";
    
    // أدوار المستخدمين
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_MANAGER = "manager";
    public static final String ROLE_ACCOUNTANT = "accountant";
    public static final String ROLE_SUPERVISOR = "supervisor";
    public static final String ROLE_WORKER = "worker";
    
    // الصلاحيات
    public static final String PERMISSION_VIEW_FARMS = "view_farms";
    public static final String PERMISSION_MANAGE_FARMS = "manage_farms";
    public static final String PERMISSION_VIEW_EMPLOYEES = "view_employees";
    public static final String PERMISSION_MANAGE_EMPLOYEES = "manage_employees";
    public static final String PERMISSION_VIEW_EQUIPMENT = "view_equipment";
    public static final String PERMISSION_MANAGE_EQUIPMENT = "manage_equipment";
    public static final String PERMISSION_VIEW_ACCOUNTS = "view_accounts";
    public static final String PERMISSION_MANAGE_ACCOUNTS = "manage_accounts";
    public static final String PERMISSION_VIEW_REPORTS = "view_reports";
    public static final String PERMISSION_GENERATE_REPORTS = "generate_reports";
    public static final String PERMISSION_MANAGE_USERS = "manage_users";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    
    public SecurityManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    
    /**
     * تسجيل دخول المستخدم
     */
    public boolean loginUser(String username, String password, String role, String fullName, int userId) {
        // في التطبيق الحقيقي، هنا نتحقق من قاعدة البيانات
        boolean isAuthenticated = authenticateUser(username, password);
        
        if (isAuthenticated) {
            editor.putBoolean(KEY_IS_LOGGED_IN, true);
            editor.putString(KEY_USERNAME, username);
            editor.putString(KEY_ROLE, role);
            editor.putString(KEY_FULL_NAME, fullName);
            editor.putInt(KEY_USER_ID, userId);
            editor.putStringSet(KEY_PERMISSIONS, getPermissionsForRole(role));
            editor.apply();
            return true;
        }
        return false;
    }
    
    /**
     * تسجيل خروج المستخدم
     */
    public void logoutUser() {
        editor.clear();
        editor.apply();
    }
    
    /**
     * التحقق من حالة تسجيل الدخول
     */
    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    
    /**
     * الحصول على اسم المستخدم الحالي
     */
    public String getCurrentUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }
    
    /**
     * الحصول على دور المستخدم الحالي
     */
    public String getCurrentUserRole() {
        return sharedPreferences.getString(KEY_ROLE, "");
    }
    
    /**
     * الحصول على الاسم الكامل للمستخدم
     */
    public String getCurrentUserFullName() {
        return sharedPreferences.getString(KEY_FULL_NAME, "");
    }
    
    /**
     * الحصول على معرف المستخدم
     */
    public int getCurrentUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }
    
    /**
     * التحقق من صلاحية محددة
     */
    public boolean hasPermission(String permission) {
        if (getCurrentUserRole().equals(ROLE_ADMIN)) {
            return true; // المدير لديه جميع الصلاحيات
        }
        
        return sharedPreferences.getStringSet(KEY_PERMISSIONS, null)
                .contains(permission);
    }
    
    /**
     * التحقق من دور المستخدم
     */
    public boolean hasRole(String role) {
        return getCurrentUserRole().equals(role);
    }
    
    /**
     * تشفير كلمة المرور (SHA-256)
     */
    public static String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.encodeToString(hash, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * التحقق من كلمة المرور
     */
    public boolean verifyPassword(String inputPassword, String storedEncryptedPassword) {
        String encryptedInput = encryptPassword(inputPassword);
        return encryptedInput != null && encryptedInput.equals(storedEncryptedPassword);
    }
    
    /**
     * المصادقة الأساسية (في التطبيق الحقيقي، استبدل هذا بالتحقق من قاعدة البيانات)
     */
    private boolean authenticateUser(String username, String password) {
        // بيانات مستخدمين تجريبية للتطوير
        if (username.equals("admin") && password.equals("admin123")) {
            return true;
        } else if (username.equals("manager") && password.equals("manager123")) {
            return true;
        } else if (username.equals("accountant") && password.equals("accountant123")) {
            return true;
        }
        return false;
    }
    
    /**
     * الحصول على الصلاحيات حسب الدور
     */
    private java.util.Set<String> getPermissionsForRole(String role) {
        java.util.Set<String> permissions = new java.util.HashSet<>();
        
        switch (role) {
            case ROLE_ADMIN:
                permissions.add(PERMISSION_VIEW_FARMS);
                permissions.add(PERMISSION_MANAGE_FARMS);
                permissions.add(PERMISSION_VIEW_EMPLOYEES);
                permissions.add(PERMISSION_MANAGE_EMPLOYEES);
                permissions.add(PERMISSION_VIEW_EQUIPMENT);
                permissions.add(PERMISSION_MANAGE_EQUIPMENT);
                permissions.add(PERMISSION_VIEW_ACCOUNTS);
                permissions.add(PERMISSION_MANAGE_ACCOUNTS);
                permissions.add(PERMISSION_VIEW_REPORTS);
                permissions.add(PERMISSION_GENERATE_REPORTS);
                permissions.add(PERMISSION_MANAGE_USERS);
                break;
                
            case ROLE_MANAGER:
                permissions.add(PERMISSION_VIEW_FARMS);
                permissions.add(PERMISSION_MANAGE_FARMS);
                permissions.add(PERMISSION_VIEW_EMPLOYEES);
                permissions.add(PERMISSION_MANAGE_EMPLOYEES);
                permissions.add(PERMISSION_VIEW_EQUIPMENT);
                permissions.add(PERMISSION_MANAGE_EQUIPMENT);
                permissions.add(PERMISSION_VIEW_ACCOUNTS);
                permissions.add(PERMISSION_VIEW_REPORTS);
                permissions.add(PERMISSION_GENERATE_REPORTS);
                break;
                
            case ROLE_ACCOUNTANT:
                permissions.add(PERMISSION_VIEW_FARMS);
                permissions.add(PERMISSION_VIEW_EMPLOYEES);
                permissions.add(PERMISSION_VIEW_EQUIPMENT);
                permissions.add(PERMISSION_VIEW_ACCOUNTS);
                permissions.add(PERMISSION_MANAGE_ACCOUNTS);
                permissions.add(PERMISSION_VIEW_REPORTS);
                permissions.add(PERMISSION_GENERATE_REPORTS);
                break;
                
            case ROLE_SUPERVISOR:
                permissions.add(PERMISSION_VIEW_FARMS);
                permissions.add(PERMISSION_VIEW_EMPLOYEES);
                permissions.add(PERMISSION_VIEW_EQUIPMENT);
                permissions.add(PERMISSION_VIEW_REPORTS);
                break;
                
            case ROLE_WORKER:
                permissions.add(PERMISSION_VIEW_FARMS);
                break;
        }
        
        return permissions;
    }
    
    /**
     * تغيير كلمة المرور
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        // في التطبيق الحقيقي، تحقق من كلمة المرور القديمة في قاعدة البيانات
        String currentUsername = getCurrentUsername();
        
        if (authenticateUser(currentUsername, oldPassword)) {
            String encryptedNewPassword = encryptPassword(newPassword);
            // حفظ كلمة المرور الجديدة في قاعدة البيانات
            // dbHelper.updateUserPassword(currentUsername, encryptedNewPassword);
            return true;
        }
        return false;
    }
    
    /**
     * التحقق من قوة كلمة المرور
     */
    public static boolean isPasswordStrong(String password) {
        if (password.length() < 8) {
            return false;
        }
        
        boolean hasUpper = !password.equals(password.toLowerCase());
        boolean hasLower = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = !password.matches("[A-Za-z0-9 ]*");
        
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
    
    /**
     * تسجيل نشاط المستخدم (Logging)
     */
    public void logUserActivity(String activity) {
        String username = getCurrentUsername();
        String timestamp = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
        
        // في التطبيق الحقيقي، احفظ هذا في قاعدة البيانات
        android.util.Log.i("UserActivity", String.format("[%s] %s: %s", timestamp, username, activity));
    }
    
    /**
     * التحقق من صلاحية الوصول إلى الشاشة
     */
    public boolean canAccessScreen(String screenPermission) {
        return isUserLoggedIn() && hasPermission(screenPermission);
    }
    
    /**
     * الحصول على جميع الصلاحيات المتاحة
     */
    public static String[] getAllPermissions() {
        return new String[] {
            PERMISSION_VIEW_FARMS,
            PERMISSION_MANAGE_FARMS,
            PERMISSION_VIEW_EMPLOYEES,
            PERMISSION_MANAGE_EMPLOYEES,
            PERMISSION_VIEW_EQUIPMENT,
            PERMISSION_MANAGE_EQUIPMENT,
            PERMISSION_VIEW_ACCOUNTS,
            PERMISSION_MANAGE_ACCOUNTS,
            PERMISSION_VIEW_REPORTS,
            PERMISSION_GENERATE_REPORTS,
            PERMISSION_MANAGE_USERS
        };
    }
    
    /**
     * الحصول على جميع الأدوار المتاحة
     */
    public static String[] getAllRoles() {
        return new String[] {
            ROLE_ADMIN,
            ROLE_MANAGER,
            ROLE_ACCOUNTANT,
            ROLE_SUPERVISOR,
            ROLE_WORKER
        };
    }
}