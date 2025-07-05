package com.test.entity;

import com.test.enums.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "pincode")
@SQLDelete(sql = "UPDATE pincode SET deleted_at = CURRENT_TIMESTAMP, status= 'INACTIVE'  WHERE pincode_id = ?")
public class PinCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pincode_id")
    private Long pincodeId;

    @Column(name = "pincode", nullable = false)
    private Long pinCode;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column (name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column (name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.ACTIVE;

    public Long getPincodeId() {
        return pincodeId;
    }

    public void setPincodeId(Long pincodeId) {
        this.pincodeId = pincodeId;
    }

    public Long getPinCode() {
        return pinCode;
    }

    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PinCode(Long pincodeId, Long pinCode, City city, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, Status status) {
        this.pincodeId = pincodeId;
        this.pinCode = pinCode;
        this.city = city;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.status = status;
    }

    public PinCode() {
    }
}
