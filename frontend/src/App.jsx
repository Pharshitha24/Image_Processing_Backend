import { useState } from "react";

import UploadImage
    from "./components/UploadImage";

import ImageViewer
    from "./components/ImageViewer";

import { uploadImage }
    from "./services/imageService";

import "./styles/App.css";

import ImageControls
    from "./components/ImageControls";

function App() {

    const [imageId,
        setImageId] =
        useState("");

    const handleUpload =
        async (file) => {

            try {

                const response =
                    await uploadImage(
                        file
                    );

                console.log(
                    response
                );

                setImageId(
                    response.imageId
                );

            } catch (error) {

                console.error(
                    error
                );
            }
        };

    return (
    <div className="app">

        <h1>
            Image Processing System
        </h1>

        <UploadImage
            onUpload={handleUpload}
        />

        <div className="main-container">

            <div className="left-panel">

                <h2>
                    Image Preview
                </h2>

                <ImageViewer
                    imageId={imageId}
                />

            </div>

            <div className="right-panel">

                <ImageControls
                    imageId={imageId}
                    onProcessed={setImageId}
                />

            </div>

        </div>

    </div>
);
}

export default App;