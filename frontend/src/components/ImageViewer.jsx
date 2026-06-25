function ImageViewer({ imageId }) {

    if (!imageId) {

        return (
            <p>
                Upload an image to preview
            </p>
        );
    }

    return (
        <img
            src={`http://localhost:8080/api/image/${imageId}`}
            alt="Processed"
        />
    );
}

export default ImageViewer;