package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ticketsRepository extends JpaRepository<tickets, Integer> {
    Optional<tickets> findFirstByTicketCode(String TicketCode);
}
