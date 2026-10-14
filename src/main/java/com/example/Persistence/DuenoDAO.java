package com.example.Persistence;

import com.example.Logic.Dueno;
import org.hibernate.Session;

public class DuenoDAO extends GenericDAOImplementation<Dueno, Long> {
    public DuenoDAO() {
        super(Dueno.class);
    }
}