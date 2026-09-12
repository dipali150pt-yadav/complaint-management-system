# 🚀 AI-Powered Civic Complaint Management System

An intelligent complaint management system for **Smart Municipal Corporations and Civic Bodies** that uses **AI-powered computer vision** to automatically analyze, categorize, and route civic complaints. Citizens simply upload a photo of the issue, and AI handles the rest!

## ✨ Key Features

### 🤖 AI-Powered Features
- **Automatic Image Analysis** - Upload a photo and AI identifies the issue
- **Smart Categorization** - Automatically detects issue type (roads, sanitation, lighting, etc.)
- **Department Routing** - AI assigns complaints to the correct municipal department
- **Severity Detection** - Identifies urgency level (Critical, High, Medium, Low)
- **Auto-Generated Descriptions** - Creates detailed complaint descriptions from images
- **Confidence Scoring** - Shows AI confidence level for transparency

### 👥 Citizen Features
- **Photo Upload** - Simply take a picture of the civic issue
- **GPS Location** - Automatic location capture from device
- **Complaint Tracking** - Track status using complaint ID
- **Multiple Issue Types** - Supports 10+ civic issue categories
- **Anonymous Reporting** - Optional name/contact submission
- **Real-time Analysis** - Instant AI analysis and categorization

### 🛠️ Admin Features
- **Comprehensive Dashboard** - View all complaints with AI insights
- **Advanced Analytics** - Statistics by category, department, severity
- **AI Confidence Metrics** - Monitor AI accuracy and performance
- **Priority Management** - Sort by urgency and priority levels
- **Department-wise View** - Filter complaints by assigned department
- **Image Viewer** - Review uploaded complaint images
- **Status Management** - Mark complaints as resolved/pending

## 🎯 Supported Civic Issues

The system can automatically detect and categorize:

1. **Roads & Infrastructure** - Potholes, damaged roads, broken pavements
2. **Sanitation** - Garbage accumulation, waste management issues
3. **Street Lighting** - Broken streetlights, non-functional lamps
4. **Water & Drainage** - Water leakage, drainage blockage, flooding
5. **Electricity** - Power outages, electrical hazards, transformer issues
6. **Parks & Gardens** - Park maintenance, tree cutting needs
7. **Stray Animals** - Animal control issues
8. **Illegal Construction** - Unauthorized construction, encroachment
9. **Public Facilities** - Public toilet issues, washroom maintenance
10. **Traffic & Parking** - Traffic signal problems, parking issues

## 🏗️ Technology Stack

### Backend
- **Java 17** - Core programming language
- **Spring Boot 3.3.5** - Application framework
- **Spring Data JPA** - Database operations
- **Spring Security** - Authentication & authorization
- **Hibernate** - ORM framework
- **MySQL** - Database

### AI & ML
- **OpenAI GPT-4 Vision API** - Primary image analysis (Recommended)
- **Google Cloud Vision API** - Alternative image analysis
- **Local Rule-Based Analysis** - Fallback when APIs unavailable
- **WebFlux** - Reactive HTTP client for API calls

### Frontend
- **Thymeleaf** - Server-side templating
- **Bootstrap 5** - UI framework
- **Bootstrap Icons** - Icon library
- **HTML5/CSS3/JavaScript** - Core web technologies

## 📋 Prerequisites

Before you begin, ensure you have:

1. **Java Development Kit (JDK) 17+**
2. **MySQL 8.0+**
3. **Maven 3.6+** (optional - included in project)
4. **IDE** (IntelliJ IDEA, Eclipse, or VS Code)
5. **AI API Key** (optional but recommended) - OpenAI OR Google Cloud Vision

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/dipali150pt-yadav/complaint-management-system.git
cd complaint-management-system
```

### 2. Setup MySQL Database

```sql
CREATE DATABASE complaintdb;
```

### 3. Configure Application

Edit `src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/complaintdb
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

# AI Provider (local/openai/google)
ai.provider=local
```

### 4. Optional: Add AI API Key

For better accuracy, configure an AI provider:

#### OpenAI (Recommended)
```properties
ai.provider=openai
openai.api.key=sk-proj-YOUR_KEY
```
Get key: https://platform.openai.com/api-keys

#### Google Cloud Vision
```properties
ai.provider=google
google.cloud.vision.api.key=YOUR_KEY
```
Get key: https://console.cloud.google.com/apis/credentials

### 5. Run the Application

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

### 6. Access the Application

- **Home**: http://localhost:8080/home
- **Admin**: http://localhost:8080/admin
- **Login**: http://localhost:8080/login

## 🔐 Default Credentials

| Role  | Username | Password   |
|-------|----------|------------|
| Admin | admin    | admin123   |
| User  | user     | user123    |

⚠️ **Change these in production!**

## 📖 How to Use

### For Citizens

1. Visit http://localhost:8080/home
2. Upload a photo of the civic issue
3. (Optional) Add location, landmark, contact details
4. Submit - AI analyzes automatically
5. Save your Complaint ID to track status

### For Admins

1. Login at http://localhost:8080/login
2. View dashboard with AI-powered analytics
3. Review complaints with:
   - AI confidence scores
   - Severity levels
   - Auto-assigned departments
4. Resolve or manage complaints

## 🏗️ Project Structure

```
complaint-management-system/
├── src/main/java/com/cms/complaintsystem/
│   ├── ComplaintsystemApplication.java
│   ├── controller/
│   │   ├── AdminController.java
│   │   ├── AuthController.java
│   │   └── HomeController.java
│   ├── model/
│   │   ├── Complaint.java (Enhanced with AI fields)
│   │   └── User.java
│   ├── repository/
│   │   ├── ComplaintRepository.java
│   │   └── UserRepository.java
│   ├── service/
│   │   ├── AIService.java (Civic categorization)
│   │   ├── ImageAnalysisService.java (Computer vision)
│   │   └── ComplaintGenerationService.java (Orchestration)
│   └── config/
│       ├── SecurityConfig.java
│       └── WebConfig.java
├── src/main/resources/
│   ├── application.properties
│   ├── application.properties.example
│   └── templates/ (Enhanced Thymeleaf UI)
├── uploads/ (Complaint images)
└── pom.xml
```

## 🔧 Configuration Options

### AI Settings

```properties
# Provider: local, openai, or google
ai.provider=local

# Enable AI analysis
app.ai.enabled=true

# Confidence threshold (0.0-1.0)
app.ai.confidence.threshold=0.6

# Auto-assign department
app.ai.auto.assign=true
```

### Upload Settings

```properties
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
app.upload.dir=${user.dir}/uploads
```

## 🧪 Testing the AI Features

### Test with Local AI (No API needed)
- Name images descriptively: `pothole.jpg`, `garbage.png`, `streetlight.jpg`
- System detects issues from filename

### Test with OpenAI (Best results)
- Configure API key
- Upload real civic issue photos
- Review AI analysis accuracy

### Test Different Issue Types
- **Roads**: Pothole, cracked pavement
- **Sanitation**: Garbage accumulation
- **Lighting**: Broken streetlight
- **Water**: Leakage, flooding

## 🚨 Troubleshooting

| Issue | Solution |
|-------|----------|
| Database connection error | Check MySQL credentials in application.properties |
| AI API 401 error | Verify API key is correct |
| Image upload fails | Check file size limit (max 10MB) |
| Port 8080 in use | Change `server.port` or stop conflicting service |

### Enable Debug Logging

```properties
logging.level.com.cms.complaintsystem=DEBUG
```

## 🔐 Security for Production

1. Change default admin/user passwords
2. Use environment variables for API keys
3. Enable HTTPS
4. Use strong database passwords
5. Implement rate limiting

## 🚀 Deployment

### Heroku
```bash
heroku create nagar-nigam-app
heroku addons:create cleardb:ignite
git push heroku main
```

### Docker
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Cloud Platforms
- AWS EC2/Elastic Beanstalk
- Google Cloud Run
- Azure App Service
- Render
- Railway

## 📊 Database Schema

Enhanced `complaint` table with 20+ fields:
- Basic: id, text, category, department, status, imagePath
- AI: aiConfidence, detectedIssues, autoGeneratedDescription, severity, priority
- Location: latitude, longitude, address, landmark
- Metadata: submittedAt, resolvedAt, submittedBy, contactNumber

## 🤝 Contributing

Contributions welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## 📝 Future Enhancements

- [ ] SMS/Email notifications
- [ ] Mobile app (Android/iOS)
- [ ] Multi-language support (Hindi, Regional languages)
- [ ] Real-time chat for complaints
- [ ] Analytics dashboard with charts
- [ ] Citizen feedback/ratings
- [ ] Voice-based complaints
- [ ] Integration with government portals

## 📄 License

MIT License - Open source

## 👥 Credits

**Original Project**: [vivekbehera240](https://github.com/vivekbehera240)  
**AI Enhancement**: Computer vision and civic complaint features

## 🌟 Acknowledgments

- Spring Boot Framework
- OpenAI GPT-4 Vision API
- Google Cloud Vision API
- Bootstrap 5
- Thymeleaf

---

**Made with ❤️ for Better Civic Services**

*Empowering citizens to report issues easily, and helping municipalities respond efficiently through AI!*

For questions or support, create an issue on GitHub.
