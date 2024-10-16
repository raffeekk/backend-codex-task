# This is my small project for codex

## Overview

The Codex Document Management System is a RESTful API built with Java and Spring Boot. It allows users to create, update, search, and manage documents in a PostgreSQL database, while also indexing and searching through them using Elasticsearch.

## Features

- **Save Documents**: Create and store documents with titles and content.
- **Update Documents**: Patch existing documents to modify their titles and content.
- **Search Documents**: Perform full-text search across documents, prioritizing titles over content.
- **Integration with Elasticsearch**: Search functionality leverages Elasticsearch for efficient querying.

## Technologies Used

- Java 17
- Spring Boot
- PostgreSQL
- Elasticsearch
- Gradle

## Getting Started

### Prerequisites

- Java 17
- PostgreSQL
- Elasticsearch

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/raffeekk/backend-codex-task.git
   cd your-repo
   ```

2. Configure your PostgreSQL database:
   - Create a database (e.g., `codex_db`).
   - Update the database connection settings in `src/main/resources/application.properties`.

3. Run Elasticsearch locally.

4. Build the project:
   ```bash
   ./gradlew build
   ```

5. Start the application:
   ```bash
   ./gradlew bootRun
   ```

### API Endpoints

#### Save Document

- **Endpoint**: `POST /document`
- **Request Body**:
  ```json
  {
    "title": "Document Title",
    "content": "Document content goes here."
  }
  ```
- **Response**:
  ```json
  {
    "id": 1,
    "title": "Document Title",
    "content": "Document content goes here."
  }
  ```

#### Update Document

- **Endpoint**: `PATCH /document`
- **Request Body**:
  ```json
  {
    "documentId": 1,
    "document": {
      "title": "Updated Title",
      "content": "Updated content goes here."
    }
  }
  ```
- **Response**:
  ```json
  {
    "id": 1,
    "title": "Updated Title",
    "content": "Updated content goes here."
  }
  ```

#### Search Documents

- **Endpoint**: `POST /document/search`
- **Request Body**:
  ```json
  {
    "querystring": "search term"
  }
  ```
- **Response**:
  ```json
  [
    {
      "id": 1,
      "title": "Document Title",
      "content": "Document content goes here."
    }
  ]
  ```

## Running Tests

To run tests, use:
```bash
./gradlew test
```

## Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot)
- [PostgreSQL](https://www.postgresql.org/)
- [Elasticsearch](https://www.elastic.co/elasticsearch/)
```
