package com.example.Controller;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.DTO.ChangePasswordRequest;
import com.example.Models.Ticket;
import com.example.Models.TicketStatus;
import com.example.Models.User;
import com.example.Repo.TicketRepo;
import com.example.Repo.UserRepo;
import com.example.Service.Changepassword;
import com.example.Service.RaiseTicket;
import com.example.Service.SendEmail;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
    @Autowired
    Changepassword changepassword;
    
    @Autowired
    RaiseTicket raiseTicketService;

    @Autowired
    SendEmail sendEmail;

    @Autowired
    UserRepo userRepo;

    @Autowired
    TicketRepo ticketRepo;

    //Login Page
    @GetMapping({"/login"})
    public String login(Model model){
        System.out.println("Login page hit");
        model.addAttribute("user", new User());   
             return "login";
    }

    //Home Page (Different for User and Admin)
    @GetMapping("/home")
    public String home(HttpServletResponse response,Principal principal,Model model){
        System.out.println("Home hit");
        User user=userRepo.findByEmail(principal.getName());
        if(user.getRole().equalsIgnoreCase("ADMIN")){
            model.addAttribute("tickets", ticketRepo.findAll());
            return "admin-page";
        }
        return "home";
    }


    //Form to change password
  @GetMapping("/changepassword")
    public String showChangePasswordForm(Model model) {
         System.out.println("GET/changepassword hit");
        model.addAttribute("request", new ChangePasswordRequest());
        return "changepassword";
    }


    //Information from change password form
   @PostMapping("/changepassword")
    public String changePassword(@ModelAttribute ChangePasswordRequest request, Model model) {
    System.out.println("POST/changepassword hit");
    boolean success = changepassword.changePassword(
            request.getUsername(),
            request.getOldPassword(),
            request.getNewPassword()
    );

    if (success) {
        model.addAttribute("message", "Password changed successfully!");
        model.addAttribute("request", new ChangePasswordRequest());
        return "changepassword";
    } else {
        model.addAttribute("error", "Invalid Email or old password!");
        model.addAttribute("request", request); 
        return "changepassword";
    }
    }

    //Form for Forgot password
   @GetMapping("/forgotpassword")
    public String showForgotPasswordForm(Model model) {
    System.out.println("Forgot password hit");
    model.addAttribute("user", new User()); 
    return "forgot-password";
    }

    //After Hitting Reset password
    @PostMapping("/forgotpassword")
    public String handleForgotPassword(@ModelAttribute("user") User user, Model model) {
    System.out.println("POST/forgot password hit");
    User userExist = userRepo.findByEmail(user.getEmail());

    if (userExist == null) {
        model.addAttribute("message", "User with this mail does not exist");
        model.addAttribute("error", true); 
        return "forgot-password";
    }

    sendEmail.sendEmail(user.getEmail(), "Reset your password link here", "Forgot Password");
    model.addAttribute("message", "Reset link sent to your email.");
    model.addAttribute("error", false); 
    return "forgot-password";
    }

    //Form to Raise Ticket
    @GetMapping("/raiseticket")
    public String raiseTicket(Model model){
    System.out.println("Raise Ticket Hit");
    model.addAttribute("ticket", new Ticket());
    return "Raise-ticket";
    }


    //After Hitting Submit ticket it saves ticket and redirects to home
    @PostMapping("/submit-ticket")
    public String submitTicket(@RequestParam("type") String type,
                           @RequestParam("description") String description,
                           @RequestParam("image") MultipartFile image,
                           Principal principal,RedirectAttributes redirectAttributes) throws IOException {
                    System.out.println("submit-ticket hit");
    User user = userRepo.findByEmail(principal.getName()); 
    raiseTicketService.raiseTicket(type, user, description, image); 
    redirectAttributes.addFlashAttribute("message", "Successfully Created Ticket");
    return "redirect:/home"; 
    }

    //To view all existing Tickets
    @GetMapping("/viewexistingtickets")
    public String viewExistingTickets(Model model, Principal principal) {
    System.out.println("Existing Tickets hit");
    String email = principal.getName();
    User user = userRepo.findByEmail(email);

  
   List<Ticket> tickets = ticketRepo.findByUser(user);


    model.addAttribute("tickets", tickets);


    return "view-existingtickets";
    }

    //When Admin Updates Status
    @PostMapping("/updateStatus")
    public String updateTicketStatus(@RequestParam Long id, @RequestParam String status) {
    System.out.println("Update Status Hit");
    Ticket ticket = ticketRepo.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
    ticket.setStatus(TicketStatus.valueOf(status)); 
    ticketRepo.save(ticket);
    return "redirect:/home"; 
    }

}





