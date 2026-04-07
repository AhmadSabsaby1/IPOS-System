package users.model;

/**
 * Roles available in IPOS-CA.
 * Admin   – manages user accounts
 * Pharmacist – core sales/stock operations
 * Manager – reports, reminders, templates
 */
public enum UserRole {
    ADMIN,
    PHARMACIST,
    MANAGER
}
