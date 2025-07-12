# 🛠️ Campus Maintenance Reporter

A full-stack web application built with Spring Boot and Thymeleaf that allows students to report infrastructure issues on campus.  
Administrators can view, manage, and update the status of these complaints through a secure dashboard.

---

## ✨ Features

- 📝 Students can submit complaints with images and descriptions  
- 🔐 Secure login system with role-based access (student/admin)  
- 📊 Admin dashboard for tracking and resolving issues  
- 🔄 Complaint status updates (Pending, In Progress, Resolved)  
- 🖼️ Image upload and storage support  

---

## 🧰 Tech Stack

- **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA  
- **Frontend:** Thymeleaf, HTML, CSS  
- **Database:** MySQL  
- **Build Tool:** Maven  

---

## ⚙️ Prerequisites

- ☕ Java 17+  
- 🛠️ Maven  
- 🐬 MySQL  

---
## 🔄 Demo Workflow: Student → Admin → Student

1. **Student logs in and submits a complaint**  
   - Example: "Fan not working in Room 204"  
   - Complaint is saved with status: **Pending**

2. **Admin logs in and updates status**  
   - Admin views the complaint list  
   - Changes the status to: **In Progress**

3. **Student logs back in to check status**  
   - Student views their complaint  
   - Status now shows: **In Progress**

---
## Demo

👉 [Watch Demo Video](https://drive.google.com/file/d/1ggo49WWY5WaXFf2QLDQyDZVTX7bwGr5q/view?usp=drive_link)

---


## 🗄️ Database Setup

1. Open your MySQL client or terminal.  
2. Create a new database named `project`:  
   ```sql
   CREATE DATABASE project;

   

