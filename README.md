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

Here’s an updated version of your backend installation instructions that includes the missing step for setting up the OAuth client ID and secret in a `.env` file:

---

## 🛠 Backend Installation & Setup  

### **Step 1: Clone the Repository**  
Clone the repository to your local machine:  
```bash  
git clone https://github.com/AlexanderK88/c11_examensarbete.git  
```  

### **Step 2: Navigate to the Project Directory**  
```bash  
cd c11_examensarbete  
```  

### **Step 3: Set Up the MySQL Database with Docker**  
1. **Ensure Docker is installed and running** on your system.  
   - [Download Docker](https://www.docker.com/products/docker-desktop) if it’s not installed.  

2. **Add the dump file that we provided**: Ensure the `mydatabase_dump.sql` file is located in the root of the project directory (next to `docker-compose.yml`).  

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

### **Step 4: Set Up OAuth Credentials**  
To enable OAuth authentication, you need to provide your GitHub OAuth client credentials. Follow these steps:

1. **Create a `.env` file in the root of the project directory**:  

2. **Add the following environment variables to the `.env` file**:
   ```plaintext
   OAUTH_CLIENT_ID=your-client-id-here
   OAUTH_CLIENT_SECRET=your-client-secret-here
   ```

   Replace `your-client-id-here` and `your-client-secret-here` with the **GitHub OAuth Client ID** and **Client Secret** provided to you.

3. **Verify the `application.properties` file**:  
   Ensure that the following lines are present in the `application.properties` file, which allows the application to import the `.env` file:
   ```properties
   spring.config.import=optional:file:.env[.properties]
   spring.security.oauth2.client.registration.github.client-id=${OAUTH_CLIENT_ID}
   spring.security.oauth2.client.registration.github.client-secret=${OAUTH_CLIENT_SECRET}
   spring.security.oauth2.client.registration.github.scope=read:user,user:email
   ```

   These properties will automatically read the values from the `.env` file you created.

---

### **Step 5: Start the Backend Application**  
1. Open the project in IntelliJ IDEA.  
2. Run the Spring Boot application:  
   - Open the **Run** tab in IntelliJ.
   - Select the **MangaReaderApplication** (or equivalent) and click **Run**.  

   The application should now be running on [http://localhost:8080](http://localhost:8080).  



## 📱 Screenshots  
(Include some screenshots of your app in action here.)

## 📜 License  
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.  
