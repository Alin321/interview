package com.papel.interview.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isCapital;

    @CreatedDate
    private Instant createdDate;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
