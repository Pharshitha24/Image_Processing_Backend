import { useState } from "react";
import {
    adjustBrightness,
    adjustContrast,
    blurImage,
    enhanceSharpness,
    rotateImage,
    flipImage,
    grayscaleImage,
    removeBackground,
    zoomImage,
    undoImage,
    detectShape,
    downloadImage,
    layerImages
} from "../services/imageService";

function ControlRow({ label, value, children, onApply }) {
    return (
        <div className="control-section">
            <div className="control-label">
                {label} <span className="control-value">{value}</span>
            </div>

            <div className="control-input">
                {children}
            </div>

            <button onClick={onApply}>
                Apply
            </button>
        </div>
    );
}

function ImageControls({ imageId, onProcessed }) {
    const [brightness, setBrightness] = useState(-57);
    const [contrast, setContrast] = useState(2.3);
    const [blur, setBlur] = useState(14);
    const [sharpness, setSharpness] = useState(2);
    const [rotation, setRotation] = useState(90);
    const [zoom, setZoom] = useState(1.5);
    const [threshold, setThreshold] = useState(127);
    const [shapeResult, setShapeResult] = useState(null);
    const [layerFile, setLayerFile] =useState(null);
    const [xOffset, setXOffset] =useState(0);
    const [yOffset, setYOffset] =useState(0);

    const processImage = async (apiCall) => {
        if (!imageId) return alert("Upload image first");

        try {
            const res = await apiCall();

            if (res?.shape) alert("Detected Shape: " + res.shape);
            if (res?.imageId) onProcessed(res.imageId);
        } catch (err) {
            console.error(err);
        }
    };

    return (
        <div className="controls-container">

            <h2>Image Processing Tools</h2>

            {/* Brightness */}
            <ControlRow
                label="Brightness"
                value={brightness}
                onApply={() =>
                    processImage(() =>
                        adjustBrightness(imageId, brightness)
                    )
                }
            >
                <input
                    className="slider"
                    type="range"
                    min="-100"
                    max="100"
                    value={brightness}
                    onChange={(e) => setBrightness(Number(e.target.value))}
                />
            </ControlRow>

            {/* Contrast */}
            <ControlRow
                label="Contrast"
                value={contrast}
                onApply={() =>
                    processImage(() =>
                        adjustContrast(imageId, contrast)
                    )
                }
            >
                <input
                    className="slider"
                    type="range"
                    min="0.5"
                    max="3"
                    step="0.1"
                    value={contrast}
                    onChange={(e) => setContrast(Number(e.target.value))}
                />
            </ControlRow>

            {/* Blur */}
            <ControlRow
                label="Blur"
                value={blur}
                onApply={() =>
                    processImage(() =>
                        blurImage(imageId, blur)
                    )
                }
            >
                <input
                    className="slider"
                    type="range"
                    min="1"
                    max="15"
                    value={blur}
                    onChange={(e) => setBlur(Number(e.target.value))}
                />
            </ControlRow>

            {/* Sharpness */}
            <ControlRow
                label="Sharpness"
                value={sharpness}
                onApply={() =>
                    processImage(() =>
                        enhanceSharpness(imageId, sharpness)
                    )
                }
            >
                <input
                    className="slider"
                    type="range"
                    min="0"
                    max="5"
                    step="0.1"
                    value={sharpness}
                    onChange={(e) => setSharpness(Number(e.target.value))}
                />
            </ControlRow>

            {/* Rotation */}
            <ControlRow
                label="Rotation"
                value={`${rotation}°`}
                onApply={() =>
                    processImage(() =>
                        rotateImage(imageId, rotation)
                    )
                }
            >
                <select
                    value={rotation}
                    onChange={(e) => setRotation(Number(e.target.value))}
                >
                    <option value={90}>90°</option>
                    <option value={180}>180°</option>
                    <option value={270}>270°</option>
                </select>
            </ControlRow>

            {/* Zoom */}
            <ControlRow
                label="Zoom"
                value={zoom}
                onApply={() =>
                    processImage(() =>
                        zoomImage(imageId, zoom)
                    )
                }
            >
                <input
                    className="slider"
                    type="range"
                    min="0.5"
                    max="3"
                    step="0.1"
                    value={zoom}
                    onChange={(e) => setZoom(Number(e.target.value))}
                />
            </ControlRow>

            {/* Background Threshold */}
            <ControlRow
                label="Background Removal Threshold"
                value={threshold}
                onApply={() =>
                    processImage(() =>
                        removeBackground(imageId, threshold)
                    )
                }
            >
                <input
                    className="slider"
                    type="range"
                    min="0"
                    max="255"
                    value={threshold}
                    onChange={(e) => setThreshold(Number(e.target.value))}
                />
            </ControlRow>

            {/* Action Buttons */}
            <div className="action-buttons">

                <button onClick={() => processImage(() => grayscaleImage(imageId))}>
                    Grayscale
                </button>

                <button onClick={() => processImage(() => flipImage(imageId, "horizontal"))}>
                    Flip Horizontal
                </button>

                <button onClick={() => processImage(() => flipImage(imageId, "vertical"))}>
                    Flip Vertical
                </button>

                <button
    onClick={async () => {

        if (!imageId) {
            alert("Upload image first");
            return;
        }

        try {

            const result =
                await detectShape(imageId);

            setShapeResult(result);

        } catch (error) {

            console.error(error);
        }
    }}
>
    Detect Shape
</button>
                <button onClick={() => processImage(() => undoImage(imageId))}>
                    Undo
                </button>

                <button onClick={() => downloadImage(imageId)}>
                    Download
                </button>

            </div>

            {shapeResult && (
    <div className="shape-result">
        Detected Shape:
        <strong>{shapeResult.detectedShape}</strong>
    </div>
)}

<div className="control-section">

    <div className="control-header">
        <span className="control-label">
            Image Layering
        </span>
    </div>

    <input
        type="file"
        accept="image/*"
        onChange={(e) =>
            setLayerFile(
                e.target.files[0]
            )
        }
    />

    <br />
    <br />

    <input
        type="number"
        placeholder="X Offset"
        value={xOffset}
        onChange={(e) =>
            setXOffset(
                Number(e.target.value)
            )
        }
    />

    <br />
    <br />

    <input
        type="number"
        placeholder="Y Offset"
        value={yOffset}
        onChange={(e) =>
            setYOffset(
                Number(e.target.value)
            )
        }
    />

    <br />
    <br />

    <button
        onClick={() =>
            processImage(() =>
                layerImages(
                    imageId,
                    layerFile,
                    xOffset,
                    yOffset
                )
            )
        }
    >
        Layer Images
    </button>

</div>

</div>
);
}

export default ImageControls;