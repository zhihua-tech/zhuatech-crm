/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.crm.model;

import jakarta.persistence.*;

@Entity @Table(name = "crm_user")
public class UserAccount extends BaseEntity {
    public enum Role { ADMIN, SALES_MANAGER, SALES }
    @Column(nullable = false, unique = true, length = 32) private String username;
    @Column(nullable = false) private String password;
    @Column(nullable = false, length = 50) private String fullName;
    @Column(length = 100) private String email;
    @Column(length = 20) private String phone;
    @Column(length = 50) private String position;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Role role;
    @Column(nullable = false) private boolean enabled = true;
    protected UserAccount() {}
    public UserAccount(String username, String password, String fullName, Role role) {
        this.username = username; this.password = password; this.fullName = fullName; this.role = role;
    }
    public void updateProfile(String email, String phone, String position) { this.email = email; this.phone = phone; this.position = position; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPosition() { return position; }
    public Role getRole() { return role; }
    public boolean isEnabled() { return enabled; }
}
