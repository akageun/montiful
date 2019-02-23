# Montiful

Montiful is a web application that enables simple system monitoring using the URL of a web application. You can also register various SNS to receive notifications easily.

## How to use
#### Install
###### Git clone
> git clone https://github.com/akageun/montiful.git

###### Build
> mvn spring-boot:run


#### Test
> mvn test

#### Configuration
- Email SMTP Infomation
- Slack Configuration
- Line Configuration

#### monitoring
1. Register the SNS (Line Notify, Email SMTP ...) to be notified.
1. Generate system health check URL
1. Register the URL to the system.

#### Program Viewer
