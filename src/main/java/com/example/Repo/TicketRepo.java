package com.example.Repo;

import com.example.Models.Ticket;
import com.example.Models.User;
import com.example.Models.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket, Long>{
  
    List<Ticket> findByUser(User user);

  
    List<Ticket> findByStatus(TicketStatus status);

   
    List<Ticket> findByUserAndStatus(User user, TicketStatus status);

  
    List<Ticket> findByType(String type);
}
