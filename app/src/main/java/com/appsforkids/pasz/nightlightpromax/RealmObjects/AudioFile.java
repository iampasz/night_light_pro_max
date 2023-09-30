package com.appsforkids.pasz.nightlightpromax.RealmObjects;
import java.io.Serializable;
import io.realm.RealmObject;

public class AudioFile extends RealmObject implements Serializable {

    private int id;
    private String nameSong;
    private String authorSong;
    private Boolean status = false;
    private int resourseLink;
    private String internetLink;
    private String lockalLink;
    private String fileName;
    private Boolean isPlay;

    public void setPlay(Boolean play) {
        isPlay = play;
    }
    public Boolean getPlay() {
        return isPlay;
    }
    public void setResourseLink(int resourseLink) {
        this.resourseLink = resourseLink;
    }
    public int getResourceLink() {
        return resourseLink;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileName() {
        return fileName;
    }
    public String getInternetLink() {
        return internetLink;
    }
    public String getLockalLink() {
        return lockalLink;
    }
    public void setInternetLink(String internetLink) {
        this.internetLink = internetLink;
    }
    public void setLockalLink(String lockalLink) {
        this.lockalLink = lockalLink;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }
    public int getId() {
        return id;
    }
    public String getNameSong() {
        return nameSong;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Boolean getStatus() {
        return status;
    }
    public void setAuthorSong(String authorSong) {
        this.authorSong = authorSong;
    }
    public String getAuthorSong() {
        return authorSong;
    }
}
