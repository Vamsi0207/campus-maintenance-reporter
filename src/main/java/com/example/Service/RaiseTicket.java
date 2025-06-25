package com.example.Service;

import com.example.Models.Ticket;
import com.example.Models.TicketStatus;
import com.example.Models.User;
import com.example.Repo.TicketRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Service
public class RaiseTicket {

    @Autowired
    private TicketRepo ticketRepository;

    private static final String UPLOAD_DIR = "uploads";

    public Ticket raiseTicket(String type, User user, String description, MultipartFile image) throws IOException {
        String fileName = image.getOriginalFilename();

        //  Save to src/main/resources/static/uploads
        String staticPath = new File("src/main/resources/static/" + UPLOAD_DIR).getAbsolutePath();
        File uploadFolder = new File(staticPath);
        uploadFolder.mkdirs(); // create folder if not exists

        File saveFile = new File(uploadFolder, fileName);
        image.transferTo(saveFile);

        // Save path relative to static/
        String imagePath = "/" + UPLOAD_DIR + "/" + fileName;

        Ticket ticket = new Ticket();
        ticket.setType(type);
        ticket.setUser(user);
        ticket.setDescription(description);
        ticket.setImagePath(imagePath);
        ticket.setStatus(TicketStatus.Pending);

        return ticketRepository.save(ticket);
    }
}
