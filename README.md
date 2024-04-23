# first-decision

First Decicion Tickets App with SSO Login Facebook OAuth2

## Running the project in Localhost mode

Clone the projet:
> git clone https://gitlab.com/marceloboah/first-decision.git


### Running Postgrees Database

> cd first-decision

> docker-compose -f docker-compose.yml up

### Running Backend Java

> cd first-decision-backend-fb-app

> mvn clean install

> mvn spring-boot:run

Access the endpoint:
http://localhost:8081/


### Swagger

Access API documentation:
http://localhost:8081/swagger-ui/index.html


### Running Frontend

> cd first-decision-app

> npm install

> ng serve

Access endpoint in browser:

http://localhost:4200

### Running Email Server

Its only need it if you will update the tickets. 

> cd node-email-form

> node index.js

The endpoint call in Frontend is disabled by default. You can change the parameter in interface/environment.ts for true to send email. Default:

export const IS_EMAIL_SERVER_ENABLE = false;

You need to configure the server with your data in index.js file:

> user: 'your-email-address@gmail.com', // Replace with your email address
> pass: 'your-email-password' // Replace with your email password


External Documentation:

https://dev.to/chabbasaad/sending-email-using-nodejs-and-nodemailer-with-angular-app-contact-form-5c58

### Postman

You can import the file:
First Decision.postman_collection.json

>License
>@Copyright

>author:
Developed by Marcelo Boá
>email:
marcelorosa1978@gmail.com


