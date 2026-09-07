# 🚀 Deployment Guide - AI-Powered Civic Complaint System

This guide will help you deploy your application to the cloud.

## 📋 Pre-Deployment Checklist

- [x] Application runs locally
- [x] Database connected
- [x] AI features working
- [x] All files committed to GitHub
- [ ] Ready to deploy!

---

## 🎯 Deployment Option 1: Render (Recommended - Free)

### Why Render?
- ✅ Free tier available
- ✅ Automatic deployments from GitHub
- ✅ Built-in database support
- ✅ Easy environment variable management
- ✅ SSL certificates included

### Step-by-Step Deployment:

#### 1. **Commit Your Code to GitHub**

```bash
# Make sure all files are committed
git add .
git commit -m "feat: Add production configuration for deployment"
git push origin main
```

#### 2. **Sign Up for Render**

1. Visit: https://render.com/
2. Click "Get Started"
3. Sign up with GitHub (recommended)
4. Authorize Render to access your repositories

#### 3. **Create a New Web Service**

1. Click "New +" → "Web Service"
2. Connect your GitHub repository: `complaint-management-system`
3. Configure:
   - **Name**: `civic-complaint-system`
   - **Environment**: `Java`
   - **Build Command**: `./mvnw clean install -DskipTests`
   - **Start Command**: `java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar target/complaintsystem-0.0.1-SNAPSHOT.jar`
   - **Plan**: Select "Free"

#### 4. **Add Environment Variables**

In Render dashboard, add these environment variables:

```
AI_PROVIDER=openai
OPENAI_API_KEY=your-openai-api-key-here
DATABASE_URL=jdbc:mysql://your-render-mysql-host:3306/complaintdb
DATABASE_USERNAME=root
DATABASE_PASSWORD=your-database-password
JAVA_TOOL_OPTIONS=-Xmx512m
```

#### 5. **Create MySQL Database**

1. In Render dashboard: "New +" → "MySQL"
2. Name: `complaint-database`
3. Database Name: `complaintdb`
4. Plan: Select "Free" (256 MB)
5. Click "Create Database"
6. Wait for database to be ready
7. Copy the "Internal Database URL"
8. Update `DATABASE_URL` in environment variables

#### 6. **Deploy**

1. Click "Create Web Service"
2. Wait 5-10 minutes for first deployment
3. Watch the logs for any errors
4. Once deployed, you'll get a URL like: `https://civic-complaint-system.onrender.com`

#### 7. **Test Your Deployment**

- Visit: `https://your-app-name.onrender.com/home`
- Submit a test complaint
- Check admin dashboard: `https://your-app-name.onrender.com/admin`

---

## 🎯 Deployment Option 2: Railway

### Step-by-Step:

#### 1. **Sign Up for Railway**

1. Visit: https://railway.app/
2. Sign in with GitHub
3. Authorize Railway

#### 2. **Deploy from GitHub**

1. Click "New Project"
2. Select "Deploy from GitHub repo"
3. Choose your repository
4. Railway will auto-detect Java/Spring Boot

#### 3. **Add MySQL Database**

1. Click "New" → "Database" → "Add MySQL"
2. Railway will create database automatically
3. Environment variables are auto-configured

#### 4. **Add Environment Variables**

```
AI_PROVIDER=openai
OPENAI_API_KEY=your-api-key
SPRING_PROFILES_ACTIVE=prod
```

#### 5. **Deploy**

- Railway automatically deploys
- Get your URL from the dashboard
- Test your application

---

## 🎯 Deployment Option 3: AWS Elastic Beanstalk

### Prerequisites:
- AWS Account
- AWS CLI installed

### Steps:

#### 1. **Package Application**

```bash
./mvnw clean package -DskipTests
```

#### 2. **Create Elastic Beanstalk Application**

```bash
# Install EB CLI
pip install awsebcli

# Initialize
eb init -p java-17 civic-complaint-system --region us-east-1

# Create environment
eb create civic-complaint-env
```

#### 3. **Configure Environment**

1. Go to AWS Console → Elastic Beanstalk
2. Select your environment
3. Configuration → Software → Environment Properties
4. Add:
   - `SERVER_PORT`: 5000
   - `AI_PROVIDER`: openai
   - `OPENAI_API_KEY`: your-key
   - `RDS_HOSTNAME`: your-database-host
   - `RDS_PORT`: 3306
   - `RDS_DB_NAME`: complaintdb
   - `RDS_USERNAME`: admin
   - `RDS_PASSWORD`: your-password

#### 4. **Deploy**

```bash
eb deploy
```

#### 5. **Open Application**

```bash
eb open
```

---

## 🎯 Deployment Option 4: Heroku

### Steps:

#### 1. **Install Heroku CLI**

Download from: https://devcenter.heroku.com/articles/heroku-cli

#### 2. **Login and Create App**

```bash
heroku login
heroku create civic-complaint-system
```

#### 3. **Add MySQL Database**

```bash
heroku addons:create jawsdb:kitefin
```

#### 4. **Set Environment Variables**

```bash
heroku config:set AI_PROVIDER=openai
heroku config:set OPENAI_API_KEY=your-key
heroku config:set SPRING_PROFILES_ACTIVE=prod
```

#### 5. **Deploy**

```bash
git push heroku main
```

#### 6. **Open Application**

```bash
heroku open
```

---

## 🎯 Deployment Option 5: Docker + Any Cloud

### Create Dockerfile:

```dockerfile
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/complaintsystem-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Build and Run:

```bash
# Build
./mvnw clean package -DskipTests
docker build -t civic-complaint-system .

# Run locally
docker run -p 8080:8080 \
  -e AI_PROVIDER=openai \
  -e OPENAI_API_KEY=your-key \
  civic-complaint-system

# Deploy to Docker Hub
docker tag civic-complaint-system yourusername/civic-complaint-system
docker push yourusername/civic-complaint-system
```

---

## 🔐 Security Best Practices

### 1. **Never Commit Sensitive Data**

Add to `.gitignore`:
```
application-prod.properties
.env
*.key
```

### 2. **Use Environment Variables**

All sensitive data should be in environment variables:
- Database passwords
- API keys
- Secret keys

### 3. **Change Default Credentials**

Update admin password in production:
```java
// In SecurityConfig or UserDetailsService
// Change from admin/admin123 to secure password
```

### 4. **Enable HTTPS**

Most cloud providers (Render, Railway) provide free SSL.

### 5. **Set Up Monitoring**

- Enable application logging
- Set up error tracking (Sentry, Rollbar)
- Monitor API usage

---

## 📊 Post-Deployment

### Test Your Deployed Application:

1. ✅ **Home Page**: `https://your-app.com/home`
2. ✅ **Submit Complaint**: Upload image and test AI
3. ✅ **Track Complaint**: `https://your-app.com/track`
4. ✅ **Admin Dashboard**: `https://your-app.com/admin`
5. ✅ **Check AI Analysis**: Verify categories and confidence scores

### Monitor Performance:

- Check application logs
- Monitor database connections
- Track API usage (OpenAI/Google)
- Monitor response times

---

## 🆘 Troubleshooting

### Issue: Application Won't Start

**Check:**
- Java version (should be 21)
- Database connection string
- Environment variables set correctly

**Solution:**
```bash
# Check logs
# Render: Dashboard → Logs
# Railway: Dashboard → Deployments → Logs
# Heroku: heroku logs --tail
```

### Issue: Database Connection Failed

**Check:**
- Database URL format: `jdbc:mysql://host:3306/dbname`
- Username and password correct
- Database created
- Network access allowed

**Solution:**
Update environment variables with correct database credentials.

### Issue: AI Not Working

**Check:**
- AI_PROVIDER environment variable set
- API key valid and not expired
- API quota not exceeded

**Solution:**
```bash
# Test API key locally first
# Check API provider dashboard for usage
```

### Issue: Images Not Uploading

**Check:**
- Upload directory writable
- File size limits
- Disk space available

**Solution:**
- Use `/tmp/uploads` for cloud deployments
- Increase `spring.servlet.multipart.max-file-size`

---

## 💰 Cost Estimation

### Free Tier (All Features):

| Service | Plan | Cost |
|---------|------|------|
| Render | Free | $0/month |
| MySQL | Free 256MB | $0/month |
| SSL Certificate | Included | $0/month |
| **Total** | | **$0/month** |

### With OpenAI:

| Service | Usage | Cost |
|---------|-------|------|
| OpenAI API | 100 images/day | ~$30/month |
| Render Web Service | Free | $0/month |
| Database | Free | $0/month |
| **Total** | | **~$30/month** |

### Production (Paid):

| Service | Plan | Cost |
|---------|------|------|
| Render | Starter | $7/month |
| MySQL | 1GB | $15/month |
| OpenAI | 500 images/day | ~$150/month |
| **Total** | | **~$172/month** |

---

## 🎓 Next Steps After Deployment

1. **Custom Domain**: Point your domain to deployment
2. **Email Notifications**: Add email service
3. **SMS Alerts**: Integrate Twilio for SMS
4. **Analytics**: Add Google Analytics
5. **Monitoring**: Set up Uptime monitoring
6. **Backup**: Configure database backups
7. **CDN**: Use CloudFlare for images

---

## 📞 Support

- **Render Docs**: https://render.com/docs
- **Railway Docs**: https://docs.railway.app/
- **Spring Boot Deployment**: https://spring.io/guides/gs/spring-boot/

---

**Your application is production-ready! Choose your deployment platform and follow the steps above.** 🚀
