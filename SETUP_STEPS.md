# 🎯 Visual Setup Steps - From Zero to Running

## 📥 Installation Phase (30-40 minutes)

```
┌─────────────────────────────────────────────────────────────┐
│                    STEP 1: Install Java 17                  │
│                                                              │
│  1. Visit: https://adoptium.net/temurin/releases/          │
│  2. Download: Windows x64 .msi installer                    │
│  3. Run installer → Check "Add to PATH"                     │
│  4. Test: java -version                                     │
│                                                              │
│  ⏱️  Time: 5 minutes                                         │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                   STEP 2: Install MySQL 8.0                 │
│                                                              │
│  1. Visit: https://dev.mysql.com/downloads/installer/      │
│  2. Download: MySQL Installer                               │
│  3. Run → Choose "Developer Default"                        │
│  4. Set Root Password: ________________                     │
│  5. Test: mysql --version                                   │
│                                                              │
│  ⏱️  Time: 10-15 minutes                                     │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                  STEP 3: Create Database                    │
│                                                              │
│  $ mysql -u root -p                                         │
│  Enter password: [your password]                            │
│                                                              │
│  mysql> CREATE DATABASE complaintdb;                        │
│  mysql> SHOW DATABASES;                                     │
│  mysql> EXIT;                                               │
│                                                              │
│  ⏱️  Time: 2 minutes                                         │
└─────────────────────────────────────────────────────────────┘
```

---

## ⚙️ Configuration Phase (5 minutes)

```
┌─────────────────────────────────────────────────────────────┐
│            STEP 4: Configure Application Properties         │
│                                                              │
│  File: src/main/resources/application.properties           │
│                                                              │
│  Change this line:                                          │
│  spring.datasource.password=12345678                        │
│                                                              │
│  To your MySQL password:                                    │
│  spring.datasource.password=YourActualPassword              │
│                                                              │
│  Keep this (no API key needed for now):                     │
│  ai.provider=local                                          │
│                                                              │
│  ⏱️  Time: 2 minutes                                         │
└─────────────────────────────────────────────────────────────┘
```

---

## 🏗️ Build Phase (10 minutes - First Time Only)

```
┌─────────────────────────────────────────────────────────────┐
│                  STEP 5: Build the Project                  │
│                                                              │
│  Open Terminal in project folder:                           │
│  C:\Users\littl\OneDrive\Documents\                        │
│      complaint-Management-System                            │
│                                                              │
│  Run this command:                                          │
│  $ mvnw.cmd clean install                                   │
│                                                              │
│  What happens:                                              │
│  [INFO] Downloading dependencies... ████████░░ 80%          │
│  [INFO] Compiling source code...    ████████░░ 90%          │
│  [INFO] Running tests...            ██████████ 100%         │
│  [INFO] BUILD SUCCESS                                       │
│                                                              │
│  ⏱️  Time: 5-10 minutes (first time only)                   │
└─────────────────────────────────────────────────────────────┘
```

---

## 🚀 Run Phase (60 seconds)

```
┌─────────────────────────────────────────────────────────────┐
│                 STEP 6: Start the Application               │
│                                                              │
│  In same terminal, run:                                     │
│  $ mvnw.cmd spring-boot:run                                 │
│                                                              │
│  You'll see:                                                │
│  Starting ComplaintsystemApplication...                     │
│  ...                                                        │
│  Started ComplaintsystemApplication in 45.3 seconds         │
│                                                              │
│  ✅ Application is now running!                             │
│  ⚠️  Keep this terminal window open                         │
│                                                              │
│  ⏱️  Time: 30-60 seconds                                     │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎉 Test Phase (5 minutes)

```
┌─────────────────────────────────────────────────────────────┐
│                  STEP 7: Test Citizen Portal                │
│                                                              │
│  1. Open browser                                            │
│  2. Visit: http://localhost:8080/home                       │
│                                                              │
│  3. You'll see: Modern complaint submission form            │
│  4. Click "Upload" or drag an image                         │
│  5. Name your image: pothole.jpg or garbage.jpg             │
│  6. (Optional) Add location details                         │
│  7. Click "Submit Complaint with AI Analysis"               │
│                                                              │
│  8. You'll get:                                             │
│     ✅ Complaint ID: 1                                       │
│     📋 Category: ROADS & INFRASTRUCTURE                      │
│     🏢 Department: Roads & Infrastructure                    │
│     ⚠️  Severity: HIGH                                       │
│     🤖 AI Confidence: 70%                                    │
│                                                              │
│  ⏱️  Time: 2 minutes                                         │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                  STEP 8: Test Admin Dashboard               │
│                                                              │
│  1. Visit: http://localhost:8080/admin                      │
│  2. Login:                                                  │
│     Username: admin                                         │
│     Password: admin123                                      │
│                                                              │
│  3. You'll see:                                             │
│     📊 Dashboard with statistics                            │
│     📋 Your complaint in the table                          │
│     🤖 AI confidence metrics                                │
│     🎯 Priority and severity indicators                     │
│                                                              │
│  4. Try:                                                    │
│     - Click "Resolve" to mark complaint as resolved         │
│     - View the uploaded image                               │
│     - Check AI analysis details                             │
│                                                              │
│  ⏱️  Time: 3 minutes                                         │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎊 Success! Your System is Running

```
┌───────────────────────────────────────────────────────────────┐
│                                                               │
│                  ✅ CONGRATULATIONS! ✅                        │
│                                                               │
│     Your AI-Powered Civic Complaint System is LIVE!          │
│                                                               │
│  📱 Citizen Portal:  http://localhost:8080/home              │
│  👨‍💼 Admin Dashboard: http://localhost:8080/admin             │
│  🔍 Track Complaint: http://localhost:8080/track             │
│                                                               │
│  To STOP:  Press Ctrl + C in terminal                        │
│  To START: Run "mvnw.cmd spring-boot:run" again              │
│                                                               │
└───────────────────────────────────────────────────────────────┘
```

---

## 🔄 Daily Usage (After Initial Setup)

Once everything is installed, you only need to do this:

```
1. Start MySQL Service (if not auto-started)
   
2. Open terminal in project folder
   
3. Run: mvnw.cmd spring-boot:run
   
4. Wait 30-60 seconds
   
5. Open browser → http://localhost:8080/home
```

---

## 🆘 Troubleshooting Guide

### ❌ Problem: "java is not recognized"
```
Cause:  Java not installed or not in PATH
Fix:    1. Install Java from Step 1
        2. Restart terminal/computer
        3. Try: java -version
```

### ❌ Problem: "mysql is not recognized"
```
Cause:  MySQL not installed
Fix:    1. Install MySQL from Step 2
        2. Restart terminal
        3. Try: mysql --version
```

### ❌ Problem: "Access denied for user 'root'"
```
Cause:  Wrong password in application.properties
Fix:    1. Open src/main/resources/application.properties
        2. Update: spring.datasource.password=YOUR_ACTUAL_PASSWORD
        3. Restart application
```

### ❌ Problem: "Port 8080 is already in use"
```
Cause:  Another application using port 8080
Fix:    Option 1: Stop the other application
        Option 2: Change port
                  - Edit application.properties
                  - Add: server.port=8081
                  - Visit: http://localhost:8081/home
```

### ❌ Problem: "Table 'complaintdb.complaint' doesn't exist"
```
Cause:  Database not created properly
Fix:    1. Open MySQL: mysql -u root -p
        2. Run: DROP DATABASE IF EXISTS complaintdb;
        3. Run: CREATE DATABASE complaintdb;
        4. Exit and restart application
```

### ❌ Problem: "BUILD FAILURE" during mvnw.cmd clean install
```
Cause:  Maven dependencies download issue
Fix:    1. Check internet connection
        2. Delete: C:\Users\littl\.m2\repository
        3. Run again: mvnw.cmd clean install
```

---

## ⚡ Performance Tips

### First Run
- Takes longer (5-10 minutes) - downloading dependencies
- Subsequent runs: 30-60 seconds

### If Slow
1. Close other applications
2. Check if antivirus is scanning project folder
3. Ensure good internet connection for first run

---

## 📊 What You Should See

### Terminal Output (Success)
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::               (v3.3.5)

2024-01-15 10:30:45 INFO  Starting ComplaintsystemApplication
2024-01-15 10:31:20 INFO  Started ComplaintsystemApplication in 45.3 seconds
```

### Browser Output (Citizen Portal)
```
🏢 नागर निगम - Nagar Nigam
   AI-Powered Civic Issue Reporter
   
   [Upload Image of Issue *]
   [Click to upload or drag and drop]
   
   📸 Upload Image
   🤖 AI Analysis
   ✅ Auto Filing
```

### Browser Output (Admin Dashboard)
```
📊 Admin Dashboard
   Nagar Nigam - AI-Powered Complaint Management
   
   Total: 5  |  Pending: 3  |  Resolved: 2  |  High Priority: 1
   
   Average AI Confidence: 85%
```

---

## 🎓 Next Steps After Setup

Once your basic system is running:

### Optional Enhancement 1: Enable OpenAI (Better AI)
```
1. Sign up: https://platform.openai.com/
2. Get API key
3. Edit application.properties:
   ai.provider=openai
   openai.api.key=sk-proj-YOUR_KEY
4. Restart application
```

### Optional Enhancement 2: Test with Real Images
```
1. Take photos of real civic issues
2. Upload to your system
3. See how AI categorizes them
4. Compare accuracy
```

### Optional Enhancement 3: Customize Categories
```
1. Edit: src/main/java/.../service/AIService.java
2. Add your city-specific categories
3. Rebuild: mvnw.cmd clean install
4. Restart application
```

---

## 📞 Need Help?

- **Quick Reference**: See QUICK_START_CHECKLIST.txt
- **Detailed Guide**: See SETUP_GUIDE.md
- **Commands**: See RUN_PROJECT.md
- **Full Docs**: See README.md

---

**Total Time Required:**
- First Time Setup: 45-60 minutes
- Daily Startup: 1-2 minutes
- Testing: 5 minutes

**You've got this! 💪**
