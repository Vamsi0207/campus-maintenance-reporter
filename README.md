<div align="center">

# 🛠️ Campus Maintenance Reporter

### Report campus issues. Track progress. Keep every repair visible.

A Spring Boot web application that gives students a simple way to report campus maintenance problems and gives administrators one dashboard for reviewing and resolving them.

[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)](https://www.oracle.com/java/technologies/downloads/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Server--Rendered-005F0F?logo=thymeleaf&logoColor=white)](https://www.thymeleaf.org/)
[![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-Wrapper-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)

[Watch the demo](https://drive.google.com/file/d/1ggo49WWY5WaXFf2QLDQyDZVTX7bwGr5q/view?usp=drive_link) · [Explore the features](#-features) · [Run locally](#-run-it-locally)

</div>

---

## 📌 Why this project?

Reporting a broken fan, a plumbing leak, or a pest issue should not require chasing down the right office in person. **Campus Maintenance Reporter** turns that process into a clear ticket workflow:

1. A student signs in and submits an issue with a category, description, and image.
2. The new ticket starts in the **Pending** state.
3. An administrator reviews all submitted issues from a centralized dashboard.
4. The administrator moves work through **Pending → In Progress → Resolved**.
5. The student can return at any time to see the latest status of their tickets.

---

## ✨ Features

| Area | What it provides |
| --- | --- |
| 🎫 **Issue reporting** | Submit a maintenance category, a detailed description, and a supporting image. |
| 🔎 **Personal ticket history** | Students can review the issues they previously submitted. |
| 📊 **Admin dashboard** | Administrators can see every ticket and update its current status. |
| 🔐 **Authentication** | Spring Security handles sign-in, sign-out, and BCrypt password verification for organization-approved accounts. |
| 🏫 **Controlled access** | Accounts are pre-registered by the organization; public user registration is intentionally disabled. |
| 🔄 **Status tracking** | Tickets move between `Pending`, `InProgress`, and `Resolved`. |
| 🖼️ **Image uploads** | Uploaded evidence is stored locally and linked from the admin dashboard. |
| 🔑 **Password support** | Users can change an existing password or request a reset email. |

---

## 🎬 Demo

Want to see the full student-to-admin workflow before installing the project?

> **[▶ Watch the Campus Maintenance Reporter demo video](https://drive.google.com/file/d/1ggo49WWY5WaXFf2QLDQyDZVTX7bwGr5q/view?usp=drive_link)**

---

## 🧰 Tech stack

| Layer | Technologies |
| --- | --- |
| **Backend** | Java 21, Spring Boot 3.5.0, Spring MVC |
| **Security** | Spring Security, BCrypt |
| **Persistence** | Spring Data JPA, Hibernate, MySQL |
| **Frontend** | Thymeleaf, HTML, CSS |
| **Email** | Spring Mail, Gmail SMTP |
| **Build** | Maven Wrapper |

---

## 🚀 Run it locally

### 1. Prerequisites

Install the following tools before starting:

- **Java 21**
- **MySQL**
- **Git**

You do **not** need to install Maven separately because the repository includes the Maven Wrapper.

### 2. Clone the repository

```bash
git clone <your-repository-url>
cd campus-maintenance-reporter
```

### 3. Create the MySQL database

Open a MySQL shell and create the database used by the application:

```sql
CREATE DATABASE project;
```

Hibernate is configured with `spring.jpa.hibernate.ddl-auto=update`, so the required tables are created or updated when the application starts.

### 4. Configure the database connection

Open `src/main/resources/application.properties` and replace the placeholder credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/project
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

### 5. Configure email support

The forgot-password form sends mail through Gmail SMTP. In `src/main/resources/application.properties`, set your sender account and a [Google App Password](https://support.google.com/accounts/answer/185833):

```properties
spring.mail.username=your_mail@gmail.com
spring.mail.password=your_google_app_password
```

Also update the sender address passed to `message.setFrom(...)` in `src/main/java/com/example/Service/SendEmail.java` so it matches the configured Gmail account.

> [!TIP]
> If you only want to explore ticket creation and status updates, you can configure email later. Email settings are needed when testing the forgot-password flow.

### 6. Start the application

On macOS or Linux:

```bash
bash ./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Then open **[http://localhost:8080/login](http://localhost:8080/login)**.

---

## 👤 Organization-managed accounts

This application is designed for use within an organization, such as a college or university. Student and administrator accounts are **pre-registered by the organization** before users sign in. Public user registration is intentionally not enabled, which helps limit access to approved members of the organization.

For local development, add representative accounts directly to the `users` table after the application has started once and Hibernate has created the schema. Passwords must be stored as **BCrypt hashes**, not plain text:

```sql
USE project;

INSERT INTO users (email, password, role)
VALUES
  ('student@example.com', '<bcrypt_hash_for_student_password>', 'USER'),
  ('admin@example.com', '<bcrypt_hash_for_admin_password>', 'ADMIN');
```

The admin dashboard is selected when a signed-in user has the `ADMIN` role. A non-admin user receives the student home page.

---

## 🔄 Example workflow

### Student experience

1. Sign in with a student account.
2. Select **Raise a Ticket**.
3. Choose an issue type, describe the maintenance problem, and attach an image.
4. Submit the ticket. It is saved with the `Pending` status.
5. Select **View My Tickets** to check progress later.

### Administrator experience

1. Sign in with an account whose role is `ADMIN`.
2. Review tickets from every user on the admin dashboard.
3. Open an attached image when additional context is needed.
4. Update the ticket to `Pending`, `InProgress`, or `Resolved`.

---

## 🗺️ Application pages

| Route | Purpose |
| --- | --- |
| `/login` | Sign in to the application. |
| `/home` | Show the student landing page or the admin dashboard based on the signed-in user's role. |
| `/raiseticket` | Open the ticket-submission form. |
| `/viewexistingtickets` | Show the signed-in student's ticket history. |
| `/changepassword` | Change an existing password after verifying the old password. |
| `/forgotpassword` | Request a password-reset email. |

---

## 🗂️ Project structure

```text
src/
├── main/
│   ├── java/com/example/
│   │   ├── Controller/      # MVC routes and page flow
│   │   ├── DTO/             # Form request objects
│   │   ├── Models/          # User, ticket, and ticket-status entities
│   │   ├── Repo/            # Spring Data JPA repositories
│   │   ├── SecurityConfig/  # Authentication and authorization configuration
│   │   └── Service/         # Ticket, password, user-detail, and email services
│   └── resources/
│       ├── static/uploads/  # Locally uploaded ticket images
│       ├── templates/       # Thymeleaf pages
│       └── application.properties
└── test/                    # Spring Boot tests
```

---

## 🧪 Run the tests

The test suite loads the Spring application context, so configure a reachable MySQL database before running it:

```bash
bash ./mvnw test
```

---

## ⚠️ Development notes

- Uploaded ticket images are written to `src/main/resources/static/uploads`. This is convenient for local development, but a production deployment should use durable object storage or an external upload directory.
- The forgot-password flow currently sends an informational email. It is a starting point for adding a secure, time-limited reset-token workflow.
- Database and email credentials belong in environment-specific configuration before deployment. Do not commit real secrets.

---

## 🌱 Ideas for future improvements

- Add an administrator-facing account provisioning page or integrate with the organization's identity provider.
- Use secure reset tokens with expiration times for forgot-password requests.
- Store uploads in cloud object storage.
- Add ticket filtering, search, priority levels, and assignment to maintenance teams.
- Add notifications when a ticket changes status.
- Add automated tests for services, repositories, and controller flows.

---

<div align="center">

Built to make campus maintenance reporting simpler, faster, and more transparent. 🚀

</div>
