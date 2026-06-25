# Image Processing Backend

## Project Overview

The Image Processing Backend is a Spring Boot application that provides REST APIs for performing image manipulation and enhancement operations.

The system follows a layered architecture that separates presentation, business logic, and data management responsibilities, making the application maintainable, scalable, and easy to extend.

---

# Architecture

The backend follows a 3-Layer Architecture.

Client
↓
Controller Layer
↓
Service Layer
↓
Image Storage Layer

---

## Controller Layer

Package:

com.imageprocessing.controller

Responsibilities:

* Exposes REST APIs
* Handles HTTP requests
* Accepts input parameters
* Returns DTO responses
* Delegates business logic to services

Example APIs:

* Upload Image
* Brightness Adjustment
* Contrast Adjustment
* Blur
* Sharpness
* Rotate
* Flip
* Grayscale
* Background Removal
* Shape Detection
* Layering
* Undo
* Download

The controller never performs image processing directly.

---

## Service Layer

Package:

com.imageprocessing.service

Responsibilities:

* Contains business logic
* Processes images
* Performs transformations
* Creates processed images
* Stores image versions
* Generates responses

Examples:

* Brightness processing
* Contrast processing
* Blur implementation
* Shape detection
* Layering implementation

The service layer acts as the core processing engine of the application.

---

## Image Storage Layer

Package:

com.imageprocessing.service

Class:

ImageStore

Responsibilities:

* Store uploaded images
* Store processed images
* Retrieve images using imageId
* Maintain image history
* Support undo functionality

This layer acts as an in-memory image repository.

---

# Request Processing Flow

Example: Brightness Adjustment

1. User uploads image
2. Frontend receives imageId
3. User selects brightness value
4. Frontend sends:

POST /api/{imageId}/brightness

5. Controller receives request
6. Controller calls ImageService
7. Service processes image
8. Processed image stored in ImageStore
9. New imageId generated
10. Response returned
11. Frontend updates preview

---

# Image Processing Pipeline

Original Image

↓

Retrieve Image

↓

Apply Operation

↓

Generate Processed Image

↓

Store Processed Image

↓

Generate New Image ID

↓

Return Response DTO

---

# Data Transfer Objects (DTOs)

The backend uses DTOs to transfer structured data between the backend and frontend.

Examples:

* UploadResponse
* BrightnessResponse
* ContrastResponse
* BlurResponse
* RotateResponse
* LayerResponse
* UndoResponse

Benefits:

* Decouples API contract from implementation
* Improves maintainability
* Prevents exposing internal objects

---

# Implemented Features

## Upload

* Upload image files
* Generate image IDs

## Enhancement

* Brightness
* Contrast
* Sharpness

## Transformations

* Rotate
* Flip
* Zoom

## Effects

* Blur
* Grayscale
* Background Removal

## Advanced Features

* Shape Detection
* Image Layering with Transparency
* Undo Processing
* Download Image

---
# API Endpoints
## Upload

POST /api/upload

## Brightness

POST /api/{imageId}/brightness

## Contrast

POST /api/{imageId}/contrast

## Blur

POST /api/{imageId}/blur

## Sharpness

POST /api/{imageId}/sharpness

## Rotate

POST /api/{imageId}/rotate

## Flip

POST /api/{imageId}/flip

## Zoom

POST /api/{imageId}/zoom

## Grayscale

POST /api/{imageId}/grayscale

## Background Removal

POST /api/{imageId}/background-remove

## Shape Detection

POST /api/{imageId}/shape-detection

## Image Layering

POST /api/layer

## Undo

POST /api/{imageId}/undo

## Download

GET /api/download/{imageId}

# Technologies Used

* Java 17
* Spring Boot
* Maven
* REST APIs
* BufferedImage
* Graphics2D
* AlphaComposite

---

# Running the Application

Clone Repository

mvn clean install

mvn spring-boot:run

Server:

http://localhost:8080

---

# Future Enhancements

* Persistent Database Storage
* User Authentication
* Cloud Storage
* Batch Image Processing
* AI-based Object Detection

---

