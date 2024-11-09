# Alert Hub

Alert Hub is a notification system designed to alert project managers about changes recorded in various Task Management Tools, including Jira, ClickUp, and GitHub. Notifications can be sent via SMS or Email, providing timely updates on project progress and task status.

## System Architecture

The Alert Hub system comprises ten microservices, each with specific roles and responsibilities:

1. **Loader**: Manages data ingestion from Task Management Tools.
2. **Security**: Handles user authentication and authorization.
3. **Action**: Manages actions (notifications) based on conditions.
4. **Metric**: Defines metrics used to track project tasks.
5. **Processor**: Evaluates metrics and triggers actions when thresholds are met.
6. **Sender Email**: Manages email notifications.
7. **Sender SMS**: Manages SMS notifications.
8. **User**: Manages user profiles and roles.
9. **Evaluation**: Provides insights into developer performance.
10. **Logger**: Handles logging across the application.

## Data Sources

Alert Hub integrates with three major platforms:
- **GitHub**
- **Jira**
- **ClickUp**

Each platform has a folder in the `data` directory containing data files named in the format `{provider_name}_yyyy_mm_ddThh_mm_ss`.

## Core Functionality

### 1. Loader Service
The Loader Service scans, extracts, and stores data in the `platformInformation` table. It runs hourly to detect new files and also allows manual triggering of file scans. Data fields from different platforms are mapped to a unified database schema.

### 2. Security Service
Manages access control for users and permissions for actions, metrics, and triggers, with different levels of access, including full administrative permissions for managing users and roles.

### 3. Metric Service
Metrics define specific conditions for task tracking, including:
- **Threshold**: Minimum count required for a metric.
- **Time Frame**: Time window for monitoring metric activity.
- **Labels**: Task labels to be tracked (e.g., bug, enhancement, help_wanted).

### 4. Action Service
Defines actions that are triggered when metric conditions are met, with details such as:
- **Type**: SMS or Email
- **Time and Day**: Schedule for when actions should run
- **Message**: Content of the notification
- **Conditions**: Array of metric-based conditions (AND/OR logic) that need to be satisfied for action triggers.

### 5. Processor Service
Evaluates conditions and queues actions for notification services. Based on conditions, it triggers either the email or SMS service by placing a message in the Kafka queue.

### 6. Notification Services (Email and SMS)
Processes queued messages and sends notifications based on action types, using either SMS or email for delivery.

### 7. Evaluation Service
Allows managers to gain insights into developer performance with endpoints for:
- Identifying developers with the most occurrences of specific labels within a time frame.
- Aggregating task counts for each label per developer.
- Retrieving the total number of tasks for a developer within a specified time frame.

## Database Schema

- **users**: Stores user details.
- **platformInformation**: Records platform-specific data after transformation.
- **metric**: Stores metric definitions and configurations.
- **action**: Tracks defined actions and their execution history.

## Job Scheduler

The scheduler runs every 30 minutes, checking actions based on their scheduled times, `is_enabled`, and `is_deleted` flags. When conditions are met, actions are queued for processing.

## Requirements

- **Testing**: Service layer testing for each microservice (except Logger).
- **Exception Handling**: Robust error management for all services.
- **API Documentation**: Swagger documentation implemented for each service.

## Additional Notes

- **Empty/Null Values**: Numeric fields treat Null as 0.
- **Manual Triggers**: Available for initiating scans and processing actions outside the automated schedule.

  
