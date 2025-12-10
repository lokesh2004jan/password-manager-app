# 🔐 Password Manager App  
A secure and modern **Password Manager** built using **Jetpack Compose**, **Room Database**, and **AES-256 Encryption**.  
This project was created as part of the **Mobile Developer Internship Practical**.

---

## 🚀 Features

### 📝 Core Functionality
- Add new password entries  
- View password details  
- Delete saved passwords  
- Bottom-sheet UI for Add & View  
- Masked password display (`********`)  
- Clean and minimal home screen list  

### 🔐 Security
All passwords are **securely encrypted** using:

- **AES/CBC/PKCS5Padding**
- **256-bit key (32 bytes)**
- **Unique IV per encryption**

No plain-text passwords are ever stored in the database.

---

## 🛠️ Tech Stack

### Frontend:
- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Navigation Compose**
- **StateFlow + ViewModel (MVVM)**

### Storage:
- **Room Database**
- **Coroutines + Flow**

### Security:
- **AES-256 Encryption**
- **IV-based cryptography**
- **Base64 secured storage**


