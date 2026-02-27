package domain;

import java.time.LocalDateTime;

public class BackupVersion {
    private int versionId;
    private String versionName;
    private LocalDateTime createdAt;
    private String description;

    public BackupVersion() {}

    public BackupVersion(int versionId, String versionName, LocalDateTime createdAt, String description) {
        this.versionId   = versionId;
        this.versionName = versionName;
        this.createdAt   = createdAt;
        this.description = description;
    }


    // getter & setter
    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
