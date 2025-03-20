
Deployed to a Linux computer in AWS as an EC2 instance of type t3.micro:

Disclaimers:
- Does handle use case: 5.1 (create account) 5.2 (login) 5.4 (retrieve applications from database)
- Does not handle use case 5.3 (which was conditionally required by 5.1)
- The retrieval can take up to 10 seconds
- All new registered users have the applicant as default role

Test credentials: ␣␣
username: recruiter ␣␣
password: recruiter ␣␣

username: candidate ␣␣
password: candidate ␣␣

http://ec2-16-170-220-41.eu-north-1.compute.amazonaws.com

Use http, not https.
