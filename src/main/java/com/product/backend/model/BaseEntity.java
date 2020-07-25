package com.product.backend.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @Column(name = "created_time")
    @CreationTimestamp
    private LocalDateTime createdTime;
}
