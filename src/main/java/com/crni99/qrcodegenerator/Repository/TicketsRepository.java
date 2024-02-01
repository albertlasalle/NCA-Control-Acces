package com.crni99.qrcodegenerator.Repository;

import com.crni99.qrcodegenerator.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketsRepository extends JpaRepository<Tickets, String> {

}