package com.sabikrahat.notekeeper.model;

public class Note {
    String key = "";
    String courseId = "";
    String noteTopic = "";
    String dateTime = "";
    String noteDescription = "";

    public Note() {
    }

    public Note(String key, String courseId, String noteTopic, String dateTime, String noteDescription) {
        this.key = key;
        this.courseId = courseId;
        this.noteTopic = noteTopic;
        this.dateTime = dateTime;
        this.noteDescription = noteDescription;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getNoteTopic() {
        return noteTopic;
    }

    public void setNoteTopic(String noteTopic) {
        this.noteTopic = noteTopic;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}
