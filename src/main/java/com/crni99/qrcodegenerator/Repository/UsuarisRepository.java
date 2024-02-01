package com.crni99.qrcodegenerator.Repository;

import com.crni99.qrcodegenerator.model.Usuaris;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarisRepository extends JpaRepository<Usuaris, Integer> {
}