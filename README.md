# C11_Examensarbete
Examensarbete med Alexander, Mangatracker webapp.


Sure! Here's a description text you can use for your manga app's README on GitHub:

---

# 📚 Manga Reader App  

**Manga Reader App** is a sleek and user-friendly application designed for manga enthusiasts to explore, read, and manage their favorite manga collections. Whether you're a casual reader or a passionate fan, this app provides a seamless experience with customizable features and an intuitive interface.  

## ✨ Features  
- **Discover New Manga**: Browse an extensive library of manga by genre, popularity, or latest releases.  
- **Reading Experience**: Enjoy a distraction-free reading experience with zoom, dark mode, and various layout options.  
- **Bookmark & Favorites**: Save your favorite manga and bookmark where you left off.  
- **Offline Reading**: Download chapters and read offline anytime, anywhere.  
- **Personalized Recommendations**: Get suggestions based on your reading preferences.  

## 🚀 Technologies Used  
- **Frontend**: React 
- **Backend**: JAVA Spring Boot  
- **Database**: MySQL 
- **API Integration**: External manga APIs for fetching content.  

Here’s the updated **Installation & Setup** section with the steps for setting up the backend, including the MySQL dump and Docker container. This is tailored for the backend setup, and we can create a separate section for the frontend afterward.

---

## 🛠 Backend Installation & Setup  

### **Step 1: Clone the Repository**  
Clone the repository to your local machine:  
```bash  
git clone https://github.com/username/manga-reader-app.git(https://github.com/AlexanderK88/c11_examensarbete.git)  
```  

### **Step 2: Navigate to the Project Directory**  
```bash  
cd manga-reader-app  
```  

### **Step 3: Set Up the MySQL Database with Docker**  
1. **Ensure Docker is installed and running** on your system.  
   - [Download Docker](https://www.docker.com/products/docker-desktop) if it’s not installed.  

2. **Add the dump file**: Ensure the `mydatabase_dump.sql` file is located in the root of the project directory (next to `docker-compose.yml`).  

3. **Start the Docker container**:  
   Run the following command to set up the MySQL database:  
   ```bash  
   docker-compose up -d  
   ```  

   This will:
   - Start a MySQL container.
   - Create the database `mydatabase`.
   - Import the tables and data from the `mydatabase_dump.sql` file.  

4. **Verify the database setup**:  
   - Open IntelliJ IDEA and go to the **Database** tab.  
   - Add a new data source by choosing **MySQL**.  
   - Use the following credentials to connect:  
     - **Host:** `localhost`  
     - **Port:** `3306`  
     - **Username:** `root`  
     - **Password:** `verysecret`  
     - **Database:** `mydatabase`  

   Once connected, you should see the tables and data in the database.

---


## 📱 Screenshots  
(Include some screenshots of your app in action here.)

## 📜 License  
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.  
