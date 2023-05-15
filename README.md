Intra Bank Payment Transfer System 
Modern Bank AS would like to create a new intra-bank payment transfer system to allow real time 
payments between internal accounts. 

High Level Requirements: 
System should be: 
1. accessible by Restful Webservices 
2. able to tell account balance in real time 
3. able to get mini statement for last 20 transactions 
4. able to transfer money in real time 
5. ability to fetch accounts details from accounts service (new / deleted) 
Acceptance Criteria: 
1. Given valid account details and positive funds available 
When account-id 111 sends €10 to account-id 222 
Then account-111’s account should be debited with €10 
And account-222’s account should be credited with €10 
2. Given invalid receiver account details and positive funds available 
When account-id 111 sends €10 to account-id 999 
Then system should reject the transfer and report invalid account details 
3. Given valid account details and no funds available (€0) 
When account-id 111 sends €10 to account-id 222 
Then system should reject the transfer with error Insufficient funds available 
4. Given valid account details 
When I call a service to check my account balance 
Then system should be able to report my current balance 
5. Given valid account details 
When I call mini statement service 
Then system should be able to show me last 20 transactions 
6. Given invalid account details 
When I call a service to check my account balance 
Then system should return error saying invalid account number 
7. Given invalid account details 
Confidential / Internal Use Only MPS Payment International 
Nets Internal 
When I call mini statement service 
Then system should return error saying invalid account number 

Technology Guidelines: 
1. Application should be written in Java-8 (or later) 
2. All the webservice API should be following Restful API architecture 
3. JSON should be used as the data serialisation format 
4. Where possible code it against interfaces and provide mock impl such as account service 
5. Use in-memory data-structure to store all the data 
6. All the classes, methods should be clearly documented 
7. Code coverage should be above 80% 
8. Use of open-source libraries is permitted 
9. Try to use Maven or Gradle based project structure 


API example for payment transfer: 
URI: http://localhost:8080/accounts/111/balance 
Output 
{ 
“account-id”: 111, 
“balance”: 100.10, 
“currency”: EUR 
} 
URI: http://localhost:8080/accounts/111/statements/mini 
Output: 
{[ 
{ 
“account-id”: 222, 
“amount”: 10, 
“currency”: EUR, 
“type”: “DEBIT”, 
“transaction-date”: 202-10-3-11:10 
}, 
{ 
“account-id”: 333, 
“amount”: 40, 
“currency”: EUR, 
“type”: “CREDIT” , 
“transaction-date”: 202-10-2-11:10 
} 
] 
}
