import { useState } from "react";

function UploadImage({ onUpload }) {

    const [file, setFile] =
        useState(null);

    const handleUpload = () => {

        if (file && onUpload) {

            onUpload(file);
        }
    };

    return (
        <div>

            <input
                type="file"
                accept="image/*"
                onChange={(event) =>
                    setFile(
                        event.target.files[0]
                    )
                }
            />

            <button
                onClick={handleUpload}
            >
                Upload
            </button>

        </div>
    );
}

export default UploadImage;