package org.fiap.entities;

import java.time.LocalDateTime;

public class Company extends _BaseEntity{

    private Integer adminId; // Reference to User
    private String name;
    private String phone;
    private String website;
    private String verificationStatus; // 'verified', 'pending'
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
