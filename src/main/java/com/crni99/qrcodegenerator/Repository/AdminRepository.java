package com.crni99.qrcodegenerator.Repository;

import com.crni99.qrcodegenerator.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}