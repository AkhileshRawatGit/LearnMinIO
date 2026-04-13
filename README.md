# 📦 MinIO File Storage Service (Spring Boot + Docker)

A backend project built using **Spring Boot** and **MinIO** to handle file storage operations like upload, access, and deletion.
Tested using **Postman**.

---

## 🚀 Features

* 📤 Upload files to MinIO
* 🔗 Generate **Presigned URLs** for secure access
* 📥 Access files via temporary URLs
* ❌ Delete files from storage
* 🐳 Docker-based MinIO setup

---

## 🛠️ Tech Stack

* Java (Spring Boot)
* MinIO (Object Storage)
* Docker
* Postman (API Testing)

---

## ⚙️ Setup Instructions

### 1️⃣ Run MinIO using Docker

```bash
docker run -p 9000:9000 -p 9001:9001 \
  -v minio_data:/data \
  -e "MINIO_ROOT_USER=admin" \
  -e "MINIO_ROOT_PASSWORD=admin123" \
  minio/minio server /data --console-address ":9001"
```

👉 MinIO Console: http://localhost:9001
👉 Username: `admin`
👉 Password: `admin123`

---

### 2️⃣ Create Bucket

* Open MinIO UI
* Create a bucket named: `hotel-images`

---

### 3️⃣ Configure Application

```properties
minio.url=http://localhost:9000
minio.username=admin
minio.password=admin123
minio.bucket-name=hotel-images
```

---

## 📡 API Endpoints (Tested with Postman)

### 📤 Upload File

```http
POST /upload
```

* Body → form-data

  * key: `file`
  * value: select file

✔ Response:

```json
{
  "fileName": "uuid.jpg",
  "url": "presigned-url"
}
```

---

### 🔗 Get File URL

```http
GET /get/{fileName}
```

✔ Returns:

* Presigned URL (temporary access link)

---

### ❌ Delete File

```http
DELETE /delete/{fileName}
```

✔ Deletes file from MinIO bucket

---

## 🔐 Security Concept

This project uses **Presigned URLs**:

* Files remain **private**
* Access is **temporary & secure**
* No credentials exposed to frontend

---

## 🐳 Docker Commands

```bash
docker ps        # Check running containers
docker ps -a     # All containers
docker stop <id>
docker start <id>
```

---

## 🧠 Key Learnings

* Working with object storage (MinIO / S3)
* File upload handling in backend
* Secure file access using presigned URLs
* Docker-based service setup
* API testing using Postman

---

## 📌 Future Improvements

* Add authentication (JWT)
* Store file metadata in database
* Restrict file types & size
* Folder-based storage structure

---

## 👨‍💻 Author

**Akhilesh Rawat**

---

## ⭐ Support

If you found this project helpful, give it a ⭐ on GitHub!
