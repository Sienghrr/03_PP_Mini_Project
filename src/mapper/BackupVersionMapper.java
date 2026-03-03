package mapper;

import domain.BackupVersion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BackupVersionMapper {
    public static BackupVersion mapBackUpVersion(ResultSet rs) throws SQLException {
        BackupVersion version = new BackupVersion();
        version.setVersionId(rs.getInt("version_id"));
        version.setVersionName(rs.getString("version_name"));
        Timestamp ts = rs.getTimestamp("created_at");
        version.setCreatedAt(ts != null ? ts.toLocalDateTime() : LocalDateTime.now());
        version.setDescription(rs.getString("description"));
        return version;
    }
}
