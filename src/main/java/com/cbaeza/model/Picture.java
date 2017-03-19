package com.cbaeza.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.sql.Timestamp;

/**
 * a picture file metadata
 */
@Document
public class Picture {

    @Id
    private String id;

    private Timestamp timestamp;
    private Date lastModified;

    private String name;
    private String size;
    private String dateTime;

    private String compresionType;
    private String dataPrecision;
    private String imageHeight;
    private String imageWidth;

    private String make;
    private String model;
    private String orientation;
    private String xResolution;
    private String yResolution;
    private String resolutionUnit;

    private String path;
    private boolean isAlreadySaved;

    public Picture() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCompresionType() {
        return compresionType;
    }

    public void setCompresionType(String compresionType) {
        this.compresionType = compresionType;
    }

    public String getDataPrecision() {
        return dataPrecision;
    }

    public void setDataPrecision(String dataPrecision) {
        this.dataPrecision = dataPrecision;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getxResolution() {
        return xResolution;
    }

    public void setxResolution(String xResolution) {
        this.xResolution = xResolution;
    }

    public String getyResolution() {
        return yResolution;
    }

    public void setyResolution(String yResolution) {
        this.yResolution = yResolution;
    }

    public String getResolutionUnit() {
        return resolutionUnit;
    }

    public void setResolutionUnit(String resolutionUnit) {
        this.resolutionUnit = resolutionUnit;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isAlreadySaved() {
        return isAlreadySaved;
    }

    public void setAlreadySaved(boolean alreadySaved) {
        isAlreadySaved = alreadySaved;
    }
}
