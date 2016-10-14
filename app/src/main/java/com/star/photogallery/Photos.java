package com.star.photogallery;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photos {

    @SerializedName("photo")
    private List<Photo> mPhoto;

    public List<Photo> getPhoto() {
        return mPhoto;
    }

    public void setPhoto(List<Photo> photo) {
        mPhoto = photo;
    }

}
