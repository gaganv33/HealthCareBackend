package com.health.care.analyzer.entity.medicineEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.health.care.analyzer.dto.medicineVendor.AddMedicineRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "medicine", uniqueConstraints = {
        @UniqueConstraint(name = "medicine_id", columnNames = {"name", "expiryDate", "serialNo"})
})
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "serial_no", nullable = false, unique = true)
    private String serialNo;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @JsonBackReference
    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "medicine_vendor_join",
            joinColumns = @JoinColumn(name = "medicine_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_vendor_id")
    )
    private List<MedicineVendor> vendorList;

    public void addMedicineVendor(MedicineVendor vendor) {
        if(vendorList == null) {
            vendorList = new ArrayList<>();
        }
        vendorList.add(vendor);
    }

    public void removeMedicineVendor(MedicineVendor vendor) {
        if(vendorList != null) {
            vendorList.remove(vendor);
        }
    }

    public boolean isContainsMedicineVendor(MedicineVendor medicineVendor) {
        if(vendorList != null) {
            return vendorList.contains(medicineVendor);
        }
        return false;
    }

    public Medicine(AddMedicineRequestDTO addMedicineRequestDTO) {
        this.name = addMedicineRequestDTO.getName();
        this.quantity = addMedicineRequestDTO.getQuantity();
        this.serialNo = addMedicineRequestDTO.getSerialNo();
        this.expiryDate = addMedicineRequestDTO.getExpiryDate();
    }
}
