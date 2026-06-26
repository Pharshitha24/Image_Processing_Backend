import { useState } from "react";

import UploadImage from "./components/UploadImage";
import ImageViewer from "./components/ImageViewer";
import ImageControls from "./components/ImageControls";

import { uploadImage } from "./services/imageService";

import "./styles/App.css";

function App() {

    const [originalImageId, setOriginalImageId] = useState("");
    const [processedImageId, setProcessedImageId] = useState("");

    const handleUpload = async (file) => {

        try {

            const response = await uploadImage(file);

            console.log(response);

            // Save uploaded image as both original and processed
            setOriginalImageId(response.imageId);
            setProcessedImageId(response.imageId);

        } catch (error) {

            console.error(error);

        }
    };

    return (

        <div className="app">

            <h1>Image Processing System</h1>

            <UploadImage onUpload={handleUpload} />

            <div className="main-container">

                {/* Image Comparison */}
                <div className="left-panel">

                    <div className="image-comparison">

                        <div className="image-box">

                            <h3>Original Image</h3>

                            <ImageViewer imageId={originalImageId} />

                        </div>

                        <div className="image-box">

                            <h3>Processed Image</h3>

                            <ImageViewer imageId={processedImageId} />

                        </div>

                    </div>

                </div>

                {/* Controls */}
                <div className="right-panel">

                    <ImageControls
                        imageId={processedImageId}
                        onProcessed={setProcessedImageId}
                    />

                </div>

            </div>

        </div>

    );
}

export default App;