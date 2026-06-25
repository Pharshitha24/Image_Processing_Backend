# Image Processing Frontend

## Project Overview

The Image Processing Frontend is a React application that provides an interactive user interface for performing image processing operations.

The frontend communicates with a Spring Boot backend through REST APIs and updates image previews dynamically after each operation.

---
# Frontend Architecture

The application follows a Component-Based Architecture.

User

↓

React Components

↓

Service Layer

↓

REST API Calls

↓

Spring Boot Backend

---

# Component Structure

src/

├── components/

│ ├── UploadSection.jsx

│ ├── ImageControls.jsx

│ └── ImagePreview.jsx

│

├── services/

│ └── imageService.js

│

├── App.jsx

└── main.jsx

---

## App Component

Responsibilities:

* Maintains application state
* Stores current imageId
* Coordinates child components

State Example:

* Current Image ID
* Preview Image

Acts as the central controller of the frontend.

---

## UploadSection Component

Responsibilities:

* Accept image uploads
* Send files to backend
* Receive imageId
* Notify App component

Flow:

Upload Image

↓

POST /api/upload

↓

Receive imageId

↓

Update Application State

---

## ImageControls Component

Responsibilities:

* Provide processing controls
* Collect user input
* Trigger processing requests

Operations:

* Brightness
* Contrast
* Blur
* Sharpness
* Rotate
* Flip
* Zoom
* Grayscale
* Background Removal
* Shape Detection
* Layering
* Undo
* Download

---

## ImagePreview Component

Responsibilities:

* Display current image
* Refresh automatically after processing

Image URL Format:

http://localhost:8080/api/image/{imageId}

Whenever imageId changes, preview updates automatically.

---

# Service Layer

File:

services/imageService.js

Responsibilities:

* Encapsulate API calls
* Handle HTTP requests
* Return backend responses

Benefits:

* Separation of concerns
* Reusability
* Cleaner components

Example:

adjustBrightness()

↓

Axios Request

↓

Backend API

↓

Response

↓

UI Update

---

# Frontend-Backend Integration

## Upload Flow

User Uploads Image

↓

Frontend sends file

↓

POST /api/upload

↓

Backend stores image

↓

Backend returns imageId

↓

Frontend stores imageId

↓

Preview displayed

---

## Processing Flow

User Changes Brightness

↓

Brightness API Called

↓

Backend Processes Image

↓

New Image Generated

↓

New imageId Returned

↓

Frontend Updates State

↓

Preview Refreshed

---

## Layering Flow

Current Preview Image

↓

User Uploads Overlay Image

↓

Layer API Called

↓

Backend Combines Images

↓

50% Transparency Applied

↓

New imageId Returned

↓

Preview Updated

---

# State Management

The application uses React Hooks.

Examples:

useState()

Used for:

* imageId
* brightness
* contrast
* blur
* sharpness
* rotation
* zoom
* shape detection results
* layering inputs

---

# Technologies Used

* React
* JavaScript
* Axios
* CSS3
* Vite

---

# Running the Application

Install Dependencies

npm install

Run Development Server

npm run dev

Application URL:

http://localhost:5173

---

# Future Enhancements

* Drag and Drop Upload
* Real-Time Filters
* Layer Management Panel
* Crop Tool
* Image History Viewer
* AI Background Removal

---
