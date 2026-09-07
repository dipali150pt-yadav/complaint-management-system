# 🚀 Quick Start Guide - Run Your Project

## Prerequisites Installation

### 1. Install Java JDK 17
- **Download**: https://adoptium.net/temurin/releases/
- Select: Version 17, Windows x64
- Install and check "Add to PATH"
- **Verify**: `java -version`

### 2. Install MySQL 8.0
- **Download**: https://dev.mysql.com/downloads/installer/
- Choose "Developer Default"
- Set root password during installation
- **Verify**: `mysql --version`

### 3. Create Database
```sql
mysql -u root -p
CREATE DATABASE complaintdb;
EXIT;
```

### 4. Configure Password
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

---

## 🏃 Running the Project

### Build Project (First Time Only)
```bash
mvnw.cmd clean install
```
⏱️ Takes 5-10 minutes on first run (downloads dependencies)

### Start Application
```bash
mvnw.cmd spring-boot:run
```
⏱️ Takes 30-60 seconds to start

### Access Application
Once you see "Started ComplaintsystemApplication":
- **Home**: http://localhost:8080/home
- **Admin**: http://localhost:8080/admin (admin/admin123)

---

## 🧪 Test the Application

### Test Citizen Portal
1. Go to http://localhost:8080/home
2. Upload an image (name it: `pothole.jpg` or `garbage.jpg`)
3. Add location details (optional)
4. Submit and get your Complaint ID

### Test Admin Dashboard
1. Go to http://localhost:8080/login
2. Login: admin / admin123
3. View your submitted complaint
4. Check AI analysis results

---

## 🔧 Common Issues & Solutions

### Issue: "java is not recognized"
**Solution**: Java not installed or not in PATH
- Reinstall Java and check "Add to PATH"
- Restart terminal

### Issue: "mysql is not recognized"
**Solution**: MySQL not installed
- Install MySQL from link above
- Restart terminal

### Issue: "Access denied for user 'root'"
**Solution**: Wrong MySQL password
- Update `application.properties` with correct password

### Issue: "Port 8080 is already in use"
**Solution**: Another app using port 8080
- Stop other application
- Or change port in `application.properties`:
  ```properties
  server.port=8081
  ```

### Issue: "Table 'complaintdb.complaint' doesn't exist"
**Solution**: Database not created
- Create database: `CREATE DATABASE complaintdb;`
- Restart application

---

## 🎯 Stop the Application

Press: **Ctrl + C** in terminal

---

## 🔄 Restart Application

```bash
mvnw.cmd spring-boot:run
```

---

## 📱 Optional: Enable AI Analysis (Better Results)

### Option 1: OpenAI (Recommended)
1. Sign up: https://platform.openai.com/
2. Get API key: https://platform.openai.com/api-keys
3. Update `application.properties`:
   ```properties
   ai.provider=openai
   openai.api.key=sk-proj-YOUR_KEY_HERE
   ```
4. Restart application

### Option 2: Google Cloud Vision
1. Create project: https://console.cloud.google.com/
2. Enable Vision API
3. Get API key
4. Update `application.properties`:
   ```properties
   ai.provider=google
   google.cloud.vision.api.key=YOUR_KEY_HERE
   ```
5. Restart application

---

## 📊 Project Structure

```
complaint-Management-System/
├── src/main/java/          # Java source code
├── src/main/resources/     # Configuration & templates
├── uploads/                # Uploaded complaint images
├── mvnw.cmd               # Maven wrapper (use this)
└── pom.xml                # Dependencies
```

---

## 🎓 Learning More

- **Full Setup Guide**: See SETUP_GUIDE.md
- **API Documentation**: See API_DOCUMENTATION.md
- **Feature Overview**: See README.md

---

## ✅ Success Checklist

- [ ] Java 17 installed
- [ ] MySQL 8.0 installed
- [ ] Database 'complaintdb' created
- [ ] application.properties configured
- [ ] Project built successfully
- [ ] Application running on port 8080
- [ ] Can access home page
- [ ] Can submit complaint
- [ ] Can login to admin dashboard

---

**Need Help?** Check SETUP_GUIDE.md for detailed troubleshooting.
