# Task Manager App
   Full-Stack Application (Angular + Spring Boot + MySQL)

   A task management system with JWT authentication, Docker support, and CRUD operations.

## 🛠️ Technologies Used
- Frontend: Angular 15, Angular Material
- Backend: Spring Boot 3 (Java 21), JWT Authentication
- Database: MySQL 8.0
- DevOps: Docker, Docker Compose

⚙️ Prerequisites
- Tool	            	| Installation Guide
- Java 21.0.1+	       :  [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
- Angular CLI 15.x	 :  npm install -g @angular/cli@15
- MySQL 8.0+	       : [MySQL Downloads](https://dev.mysql.com/downloads/)
- Docker Latest	    :  [Docker Desktop](https://www.docker.com/products/docker-desktop/)

## 🚀 Installation
1. Clone the Repository
- git clone https://github.com/MohamadSiyam66/Task-Manager-App.git  
- cd Task-Manager-App  

2. Backend Setup
- cd backend  
- Configure application.properties: 
- Build and run: mvn spring-boot:run  
- API will start at: http://localhost:8080

3. Frontend Setup
- cd frontend  
- npm install  
- ng serve  
- Access UI at: http://localhost:4200

4. Database Setup
- Execute backend/task-manager-db.sql in MySQL Workbench or via CLI: mysql -u root -p < task-manager-db.sql  

## 🐳 Docker Deployment 
- docker-compose up 
- Frontend: http://localhost:4200
- Backend: http://localhost:8080

## 🔐 Authentication (JWT)
- Auto-generated JWT secret

## 🚨 Troubleshooting
- Docker Build Error:	Run docker-compose build --no-cache
- If docker-compose Doesn't work properly
  1. Change the **mysql:3306** to **localhost:3306** in the application.properties
  2. Then run the backend locally.

## Screanshots
1.Login Page
   ![image](https://github.com/user-attachments/assets/02f80425-b460-492e-b91d-aded6f1fae0d)

2. Register Page
![image](https://github.com/user-attachments/assets/15a4f648-4c51-4eac-9ded-b2d778e17dfe)

3. User Dashboard
![image](https://github.com/user-attachments/assets/1645ae09-8cff-4466-b07f-5a82340fb52f)

4. New Task
![image](https://github.com/user-attachments/assets/bdb4dcc3-4e2f-4f34-a5c6-8de43f379081)

5. Update Task
![image](https://github.com/user-attachments/assets/bf2b3c59-c90b-452f-a37a-56fed30b206a)

6. Filter
![image](https://github.com/user-attachments/assets/b8f24991-fac0-4765-a211-a5a94e117a25)


7. View Task Details
![image](https://github.com/user-attachments/assets/4f55d8e4-7bf0-44ff-a595-9febb33e3b11)


