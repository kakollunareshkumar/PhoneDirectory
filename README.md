# PhoneDirectory

**Step 1:**
Clone the application to local system and open with IDE like Eclipse or Intellij

**Step 2:**
In application.properties

change the username and password to send the contact details to your email id.
======================================================
spring.mail.username=nareshkumarkakollu3456@gmail.com
spring.mail.password=************

**Step 2:**
Navigate to the root folder of phoneDirectoryAPI and open terminal and run unit tests by giving following command:
>mvn test
This command will download the  necessary dependencies and  will run 19 integration test cases.

**Step 3:**
Run the application:
>mvn spring-boot:run
By default spring boot runs in port 8080. If you want to change the port open application.properties and add property server.port = your system port
This command will start the spring boot application and we can do crud operations with phone directory.

**Features of PhoneDirectory API:**
1. Add Contact Details.
2. Update Contact Details.
3. Delete Contact Details.
4. Get All Contact Details.
5. Get Contact Details By Name.
   
