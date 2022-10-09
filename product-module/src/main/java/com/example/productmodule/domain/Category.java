package com.example.productmodule.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categories")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
}
