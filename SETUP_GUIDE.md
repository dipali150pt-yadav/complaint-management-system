# 📚 Detailed Setup Guide - AI-Powered Civic Complaint System

This guide provides step-by-step instructions for setting up the AI-powered civic complaint management system.

## Table of Contents
1. [System Requirements](#system-requirements)
2. [Development Environment Setup](#development-environment-setup)
3. [Database Setup](#database-setup)
4. [AI Services Configuration](#ai-services-configuration)
5. [Application Configuration](#application-configuration)
6. [Running the Application](#running-the-application)
7. [Testing the System](#testing-the-system)
8. [Production Deployment](#production-deployment)
9. [Troubleshooting](#troubleshooting)

## System Requirements

### Minimum Requirements
- **CPU**: Dual-core processor
- **RAM**: 4GB minimum, 8GB recommended
- **Storage**: 2GB free space
- **OS**: Windows 10/11, macOS 10.14+, Linux (Ubuntu 20.04+)

### Software Requirements
- Java Development Kit (JDK) 17 or higher
- MySQL 8.0 or higher
- Maven 3.6+ (optional, wrapper included)
- Git
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Development Environment Setup

### 1. Install Java JDK 17

#### Windows
1. Download JDK from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [Adoptium](https://adoptium.net/)
2. Run the installer
3. Set JAVA_HOME environment variable:
   ```
   JAVA_HOME=C:\Program Files\Java\jdk-17
   ```
4. Add to PATH: `%JAVA_HOME%\bin`
5. Verify installation:
   ```bash
   java -version
   javac -version
   ```

#### macOS
```bash
# Using Homebrew
brew install openjdk@17

# Verify
java -version
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install openjdk-17-jdk
java -version
```

### 2. Install MySQL

#### Windows
1. Download MySQL Installer from [mysql.com](https://dev.mysql.com/downloads/installer/)
2. Run installer and choose "Developer Default"
3. Set root password during installation
4. Start MySQL service

#### macOS
```bash
# Using Homebrew
brew install mysql
brew services start mysql
mysql_secure_installation
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install mysql-server
sudo systemctl start mysql
sudo mysql_secure_installation
```

### 3. Install Git

#### Windows
Download from [git-scm.com](https://git-scm.com/) and run installer

#### macOS
```bash
brew install git
```

#### Linux
```bash
sudo apt install git
```

### 4. Install IDE (Optional but Recommended)

**IntelliJ IDEA Community Edition** (Recommended)
- Download from [jetbrains.com](https://www.jetbrains.com/idea/download/)
- Free and feature-rich

**VS Code** with Java extensions
- Download from [code.visualstudio.com](https://code.visualstudio.com/)
- Install "Extension Pack for Java"

## Database Setup

### Step 1: Start MySQL Server

```bash
# Windows
net start MySQL80

# macOS/Linux
sudo systemctl start mysql
# or
brew services start mysql
```

### Step 2: Login to MySQL

```bash
mysql -u root -p
# Enter your root password
```

### Step 3: Create Database

```sql
-- Create database
CREATE DATABASE complaintdb;

-- Verify
SHOW DATABASES;

-- Use database
USE complaintdb;
```

### Step 4: Create Application User (Optional but Recommended)

```sql
-- Create user
CREATE USER 'complaint_admin'@'localhost' IDENTIFIED BY 'SecurePassword123!';

-- Grant privileges
GRANT ALL PRIVILEGES ON complaintdb.* TO 'complaint_admin'@'localhost';

-- Refresh privileges
FLUSH PRIVILEGES;

-- Exit
EXIT;
```

### Step 5: Test Connection

```bash
mysql -u complaint_admin -p complaintdb
# Enter password: SecurePassword123!
```

## AI Services Configuration

### Option 1: OpenAI GPT-4 Vision (Recommended)

#### Step 1: Create OpenAI Account
1. Visit [platform.openai.com](https://platform.openai.com/)
2. Sign up for an account
3. Add payment method (pay-as-you-go)

#### Step 2: Generate API Key
1. Go to [API Keys](https://platform.openai.com/api-keys)
2. Click "Create new secret key"
3. Name it: "Civic Complaint System"
4. Copy the key (starts with `sk-proj-...`)
5. **Save it securely** - you won't see it again!

#### Step 3: Configure in Application
Edit `src/main/resources/application.properties`:
```properties
ai.provider=openai
openai.api.key=sk-proj-YOUR_API_KEY_HERE
```

#### Pricing
- Model: GPT-4o-mini
- Cost: ~$0.01 per image analysis
- Free tier: $5 credit for new accounts

### Option 2: Google Cloud Vision

#### Step 1: Create Google Cloud Account
1. Visit [cloud.google.com](https://cloud.google.com/)
2. Sign up (free $300 credit for 90 days)

#### Step 2: Create Project
1. Go to [Console](https://console.cloud.google.com/)
2. Create new project: "Civic-Complaints"
3. Select the project

#### Step 3: Enable Vision API
1. Navigate to [APIs & Services](https://console.cloud.google.com/apis/library)
2. Search for "Cloud Vision API"
3. Click "Enable"

#### Step 4: Create API Key
1. Go to [Credentials](https://console.cloud.google.com/apis/credentials)
2. Click "Create Credentials" → "API Key"
3. Copy the API key
4. (Optional) Restrict key to Vision API only

#### Step 5: Configure in Application
```properties
ai.provider=google
google.cloud.vision.api.key=AIzaSy...YOUR_KEY
```

#### Pricing
- Free: First 1,000 requests/month
- After: $1.50 per 1,000 requests

### Option 3: Local Analysis (No API Needed)

#### Configuration
```properties
ai.provider=local
```

#### How it Works
- Uses rule-based pattern matching
- Analyzes filename and text keywords
- No external API calls
- Good for development/testing
- Lower accuracy than AI APIs

## Application Configuration

### Step 1: Clone Repository

```bash
git clone https://github.com/dipali150pt-yadav/complaint-management-system.git
cd complaint-management-system
```

### Step 2: Configure Database

Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/complaintdb
spring.datasource.username=complaint_admin
spring.datasource.password=SecurePassword123!

# JPA Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

### Step 3: Configure AI Provider

Choose one option from above and add to `application.properties`

### Step 4: Configure Upload Directory

```properties
# Default: project_root/uploads
app.upload.dir=${user.dir}/uploads

# Or specify custom path
# app.upload.dir=/var/www/complaints/uploads
```

### Step 5: Configure Application Settings

```properties
# File upload limits
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# AI Settings
app.ai.enabled=true
app.ai.confidence.threshold=0.6
app.ai.auto.assign=true

# Server
server.port=8080
```

## Running the Application

### Method 1: Using Maven Wrapper (Recommended)

#### Windows
```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

#### Linux/macOS
```bash
./mvnw clean install
./mvnw spring-boot:run
```

### Method 2: Using Installed Maven

```bash
mvn clean install
mvn spring-boot:run
```

### Method 3: Using IDE

#### IntelliJ IDEA
1. Open project folder
2. Wait for Maven import
3. Right-click `ComplaintsystemApplication.java`
4. Select "Run 'ComplaintsystemApplication'"

#### VS Code
1. Open project folder
2. Install Java extensions
3. Press F5 or use "Run" menu
4. Select "Java" configuration

### Method 4: Using JAR File

```bash
# Build
mvn clean package

# Run
java -jar target/complaintsystem-0.0.1-SNAPSHOT.jar
```

### Verify Application Started

Look for this in console:
```
Started ComplaintsystemApplication in X.XXX seconds
```

### Access URLs
- Home: http://localhost:8080/home
- Admin: http://localhost:8080/admin  
- Login: http://localhost:8080/login

## Testing the System

### 1. Test Database Connection

Check console for:
```
HikariPool-1 - Start completed
```

### 2. Test Citizen Portal

1. Open http://localhost:8080/home
2. Upload test image
3. Fill optional fields
4. Submit complaint
5. Verify complaint ID received

### 3. Test AI Analysis

#### With Local Provider
- Name file: `pothole.jpg` or `garbage.png`
- System detects from filename

#### With OpenAI/Google
- Upload real civic issue photos
- Check AI-generated description
- Verify category and department
- Check confidence score

### 4. Test Admin Dashboard

1. Visit http://localhost:8080/login
2. Login: `admin` / `admin123`
3. View dashboard statistics
4. Check complaint table
5. Verify AI confidence metrics
6. Test resolve/delete actions

### 5. Test API Endpoints

```bash
# Health check (if configured)
curl http://localhost:8080/actuator/health

# Submit complaint (via curl)
curl -X POST http://localhost:8080/submit \
  -F "image=@test-pothole.jpg" \
  -F "address=Main Street" \
  -F "name=Test User"
```

## Production Deployment

### Pre-Deployment Checklist

- [ ] Change default admin/user passwords
- [ ] Use environment variables for sensitive data
- [ ] Configure production database
- [ ] Enable HTTPS/SSL
- [ ] Set up proper logging
- [ ] Configure backup strategy
- [ ] Set up monitoring
- [ ] Test all features
- [ ] Prepare rollback plan

### Environment Variables

Instead of hardcoding in `application.properties`:

```bash
# Linux/macOS
export MYSQL_PASSWORD=your_password
export OPENAI_API_KEY=your_key

# Windows
set MYSQL_PASSWORD=your_password
set OPENAI_API_KEY=your_key
```

In `application.properties`:
```properties
spring.datasource.password=${MYSQL_PASSWORD}
openai.api.key=${OPENAI_API_KEY}
```

### SSL Configuration

```properties
server.port=8443
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=your_password
server.ssl.key-store-type=PKCS12
```

### Production Properties

Create `application-prod.properties`:
```properties
spring.jpa.show-sql=false
logging.level.root=WARN
logging.level.com.cms.complaintsystem=INFO
```

Run with:
```bash
java -jar app.jar --spring.profiles.active=prod
```

## Troubleshooting

### Problem: Database Connection Failed

**Error**: `Access denied for user 'root'@'localhost'`

**Solutions**:
1. Verify MySQL is running
2. Check username/password in application.properties
3. Reset MySQL root password if needed
4. Verify database exists: `SHOW DATABASES;`

### Problem: Port 8080 Already in Use

**Error**: `Port 8080 is already in use`

**Solutions**:
1. Stop other application using port 8080
2. Change port in application.properties:
   ```properties
   server.port=8081
   ```

### Problem: AI API Returns 401 Unauthorized

**Error**: `401 Unauthorized from OpenAI/Google`

**Solutions**:
1. Verify API key is correct (no spaces)
2. Check API key has not expired
3. Verify billing is set up (for OpenAI)
4. Check API is enabled (for Google Cloud)

### Problem: Image Upload Fails

**Error**: `Maximum upload size exceeded`

**Solutions**:
1. Check file size (max 10MB default)
2. Increase limit in application.properties:
   ```properties
   spring.servlet.multipart.max-file-size=50MB
   spring.servlet.multipart.max-request-size=50MB
   ```

### Problem: Maven Build Fails

**Error**: `Package does not exist`

**Solutions**:
1. Clean Maven cache:
   ```bash
   mvn clean
   rm -rf ~/.m2/repository
   ```
2. Reload Maven dependencies in IDE
3. Check internet connection
4. Verify Java version: `java -version`

### Problem: Tables Not Created

**Error**: `Table 'complaintdb.complaint' doesn't exist`

**Solutions**:
1. Verify `spring.jpa.hibernate.ddl-auto=update`
2. Check database connection logs
3. Manually create table (see schema in model)
4. Drop and recreate database

### Enable Debug Mode

Add to `application.properties`:
```properties
logging.level.root=DEBUG
logging.level.com.cms.complaintsystem=DEBUG
logging.level.org.springframework=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

## Getting Help

### Resources
- **GitHub Issues**: Report bugs or ask questions
- **Stack Overflow**: Tag with `spring-boot` and `computer-vision`
- **Spring Boot Docs**: [spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
- **OpenAI Docs**: [platform.openai.com/docs](https://platform.openai.com/docs)

### Common Commands Reference

```bash
# Check Java version
java -version

# Check MySQL status
systemctl status mysql  # Linux
brew services list       # macOS

# Maven clean and rebuild
mvn clean install

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# View MySQL logs
tail -f /var/log/mysql/error.log

# Check application logs
tail -f logs/spring-boot-application.log
```

---

**Setup complete!** You now have a fully functional AI-powered civic complaint management system.

For additional help, refer to the main README.md or create an issue on GitHub.
