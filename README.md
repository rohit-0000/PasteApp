# 📝 Paste App

A full-stack **Notes App** where users can **create, edit, delete, and search notes**—built using **React.js** for the frontend, **Spring Boot** for the backend, and **MongoDB Atlas** for the database. This app offers secure authentication, responsive design, and a developer-friendly note editor with dynamic line numbering like VS Code.

---

## 🚀 Live Demo

- **Frontend (Vercel)**: [Paste App Frontend](https://paste-app-git-main-rohit-0000s-projects.vercel.app/home)  
- **Backend (Render)**: [Paste App API](https://pasteapp-deployment.onrender.com)

---

## 🛠️ Tech Stack

### 🌐 Frontend
- React.js
- React Router
- Redux Toolkit
- React Toastify (notifications)
- Custom TextArea with Line Numbers
- Responsive Design

### ⚙️ Backend
- Spring Boot
- Spring Security
- JWT Authentication
- BCrypt Password Hashing
- JavaMailSender (Email OTP)
- MongoDB Atlas

---

## 🔐 Features

- ✅ User Authentication (JWT + Spring Security)
- ✅ Password Encryption (BCrypt)
- ✅ Forgot Password (Email OTP via JavaMailSender)
- ✅ Create, Edit, Delete Notes
- ✅ Search in Notes
- ✅ Dynamic Line Numbers in Editor
- ✅ Responsive UI for All Devices
- ✅ Persistent Storage on MongoDB Atlas
- ✅ Toast Notifications

---

## ⚙️ Setup Instructions

### 🖥 Backend (Spring Boot)

1. Clone the backend repository:
   ```bash
   git clone https://github.com/yourusername/paste-app-backend.git
   cd paste-app-backend
   ```

2. Configure MongoDB Atlas and Email credentials in `src/main/resources/application.properties`:
   ```properties
   spring.data.mongodb.uri=your_mongodb_uri
   spring.mail.username=your_email
   spring.mail.password=your_email_password
   ```

3. Run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```

---

### 💻 Frontend (React.js)

1. Clone the frontend repository:
   ```bash
   git clone https://github.com/yourusername/paste-app-frontend.git
   cd paste-app-frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Create a `.env` file in the root with your backend API base URL:
   ```env
   REACT_APP_API_BASE_URL=https://your-backend-url.onrender.com/api
   ```

4. Start the frontend server:
   ```bash
   npm start
   ```

---

## 📂 Folder Structure

### Frontend
```
src/
├── components/
├── pages/
├── redux/
├── utils/
└── App.js
```

### Backend
```
src/
├── controller/
├── model/
├── repository/
├── service/
├── config/
└── PasteAppApplication.java
```

---

## 🤝 Contributing

Contributions are welcome!  
Feel free to fork this repo, submit a pull request, or open an issue for any bugs or feature requests.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---
