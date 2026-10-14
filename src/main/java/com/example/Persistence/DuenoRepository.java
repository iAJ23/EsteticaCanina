package com.example.Persistence;

import com.example.Logic.Dueno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DuenoRepository extends JpaRepository<Dueno, Integer> {
}
