package com.health.care.analyzer.entity.medicineEntity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

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
