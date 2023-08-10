import { api } from "@shared/common-data";
import axios from "axios";

export async function useImageUpload(images: File[], setFunction: Function) {
  const formData = new FormData();
  for (let image of images) {
    formData.append("images", image);
  }

  try {
    const response = await axios.post(`${api}/upload/images`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    setFunction(response.data);
    return response.data;
  } catch (error) {
    throw error;
  }
}
