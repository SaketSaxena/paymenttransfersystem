# Payment Transfer System
This application uses Java and Spring boot framework to publish the rest endpoint used in intra bank payment transfer system.

### Few Assumptions Made
While creating the rest endpoint few assumption were made. Below are the list of all the assumption made

- Currency for every account default to `GBP`.   
  `If require we can implement the Utility class or an api which can do the currency conversion and also add the coversion charges based on that, amount can be deducted while transfering the fund`
- When closing the account, user is only able to close the account if the account is not holding any balance.
- While creating a new account, account-id need to passed by the user.
  `When doing the actaul implement this can be generated based the account number generator`
- When the account is deleted, user is only able to see his/her account details. He is not able to check the balance, mini-statements or transfer and receive the funds.

## How to run the application

- Build the application using 
    <pre>mvn clean install</pre>
- Run the application using
    <pre>mvn spring-boot:run
           or
  Run PaymentTransferSystemApplication Java file</pre>

## How to access the application
When the application starts it can be accessible on port no `8080`.

### Integration with Swagger
To test the above endpoints you can either use any tool like `Postman` or you can access `Swagger` on http://localhost:8080/swagger-ui/index.html

## Rest Endpoints
Application publish following endpoint:

- To get the account balance  
  `GET http://localhost:8080/accounts/{accountId}/balance`
- To get the mini statement (only provides last 20 transaction)  
  `GET http://localhost:800/accounts/{accountId}/statement/mini`
- To Transfer funds from one account to another account  
  `POST http://localhost:8080/transfer-fund`  
- To Create the new user account  
  `POST http://localhost:8080/account/new`
- To Close the user account  
  `POST http://localhost:8080/account/{accountId}/close`
- To get the user account details  
  `GET http://localhost:8080/account/{accountId}`