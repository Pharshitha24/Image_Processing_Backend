import axios from "axios";

const API =
    "http://localhost:8080/api";

export const uploadImage =
    async (file) => {

        const formData =
            new FormData();

        formData.append(
            "file",
            file
        );

        const response =
            await axios.post(
                `${API}/upload`,
                formData,
                {
                    headers: {
                        "Content-Type":
                            "multipart/form-data"
                    }
                }
            );

        return response.data;
    };


export const adjustBrightness =
    async (imageId, brightness) =>
        (await axios.post(
            `${API}/${imageId}/brightness?brightness=${brightness}`
        )).data;

export const adjustContrast =
    async (imageId, contrast) =>
        (await axios.post(
            `${API}/${imageId}/contrast?contrast=${contrast}`
        )).data;

export const blurImage =
    async (imageId, kernelsize) =>
        (await axios.post(
            `${API}/${imageId}/blur?kernelsize=${kernelsize}`
        )).data;

export const enhanceSharpness =
    async (imageId, factor) =>
        (await axios.post(
            `${API}/${imageId}/sharpness?factor=${factor}`
        )).data;

export const rotateImage =
    async (imageId, angle) =>
        (await axios.post(
            `${API}/${imageId}/rotate?angle=${angle}`
        )).data;

export const flipImage =
    async (imageId, direction) =>
        (await axios.post(
            `${API}/${imageId}/flip?direction=${direction}`
        )).data;

export const grayscaleImage =
    async (imageId) =>
        (await axios.post(
            `${API}/${imageId}/grayscale`
        )).data;

export const removeBackground =
    async (imageId, threshold) =>
        (await axios.post(
            `${API}/${imageId}/background-remove?threshold=${threshold}`
        )).data;

export const zoomImage =
    async (imageId, factor) =>
        (await axios.post(
            `${API}/${imageId}/zoom?factor=${factor}`
        )).data;

export const undoImage = async (imageId) => {
    const response =
        await axios.post(
            `${API}/${imageId}/undo`
        );

    return response.data;
};

export const detectShape =
    async (imageId) =>
        (await axios.post(
            `${API}/${imageId}/shape-detection`
        )).data;

export async function downloadImage(imageId) {

    const response = await fetch(
        `http://localhost:8080/api/download/${imageId}`
    );

    if (!response.ok) {
        throw new Error("Failed to download image");
    }

    const blob = await response.blob();

    const url = window.URL.createObjectURL(blob);

    const link = document.createElement("a");

    link.href = url;
    link.download = `image-${imageId}.png`;

    document.body.appendChild(link);

    link.click();

    link.remove();

    window.URL.revokeObjectURL(url);
}

export const layerImages = async (
    backgroundImageId,
    foregroundFile,
    xOffset,
    yOffset
) => {

    const formData =
        new FormData();

    formData.append(
        "backgroundImageId",
        backgroundImageId
    );

    formData.append(
        "foregroundFile",
        foregroundFile
    );

    formData.append(
        "xOffset",
        xOffset
    );

    formData.append(
        "yOffset",
        yOffset
    );

    const response =
        await axios.post(
            `${API}/layer`,
            formData,
            {
                headers: {
                    "Content-Type":
                        "multipart/form-data"
                }
            }
        );

    return response.data;
};