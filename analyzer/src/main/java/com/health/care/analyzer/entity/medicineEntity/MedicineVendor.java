package com.health.care.analyzer.entity.medicineEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.health.care.analyzer.entity.userEntity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medicine_vendor")
public class MedicineVendor {
    @Id
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "address", nullable = false)
    private String address;

    @JsonManagedReference
    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "medicine_vendor_join",
            joinColumns = @JoinColumn(name = "medicine_vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private List<Medicine> medicineList;

    public void addMedicine(Medicine medicine) {
        if(medicineList == null) {
            medicineList = new ArrayList<>();
        }
        medicineList.add(medicine);
    }

    public void removeMedicine(Medicine medicine) {
        if(medicineList != null) {
            medicineList.remove(medicine);
        }
    }
}
