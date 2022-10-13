package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLMediaElement extends HTMLElement {
    //public AudioTrackList audioTracks;
    public boolean autoplay;
    //public TimeRanges buffered;
    //public MediaController controller;
    public boolean controls;
    //public DOMTokenList controlsList;
    public String crossOrigin;
    public double currentTime;
    public double defaultTime;
    public boolean defaultMuted;
    public double defaultPlaybackRate;
    public boolean disableRemotePlayback;
    public double duration;
    public boolean ended;
    //public MediaError error;
    public boolean loop;
    public String mediaGroup;
    //public MediaKeys mediaKeys:
    public boolean mozAudioCaptured;
    public double mozFragmentEnd;
    public long mozFrameBufferLength;
    public double mozSampleRate;
    public boolean muted;
    public short networkState;
    public boolean paused;
    public double playbackRate;
    //public TimeRanges played;
    public String preload;
    public boolean preservesPitch;
    public short readyState;
    //public TimeRanges seekable;
    public boolean seeking;
    public String sinkId;
    public String src;
    //public MediaStream srcObject;
    //public TextTrack textTracks;
    //public VideoTrack videoTracks;
    public double volume;

    public void update() {
        super.update();
        getter.put("autoplay", autoplay);
        getter.put("controls", controls);
        getter.put("crossOrigin", crossOrigin);
        getter.put("currentTime", currentTime);
        getter.put("defaultTime", defaultTime);
        getter.put("defaultMuted", defaultMuted);
        getter.put("defaultPlaybackRate", defaultPlaybackRate);
        getter.put("disableRemotePlayback", disableRemotePlayback);
        getter.put("duration", duration);
        getter.put("ended", ended);
        getter.put("loop", loop);
        getter.put("mediaGroup", mediaGroup);
        getter.put("mozAudioCaptured", mozAudioCaptured);
        getter.put("mozFragmentEnd", mozFragmentEnd);
        getter.put("mozFrameBufferLength", mozFrameBufferLength);
        getter.put("mozSampleRate", mozSampleRate);
        getter.put("muted", muted);
        getter.put("networkState", networkState);
        getter.put("paused", paused);
        getter.put("playbackRate", playbackRate);
        getter.put("preload", preload);
        getter.put("preservesPitch", preservesPitch);
        getter.put("readyState", readyState);
        getter.put("seeking", seeking);
        getter.put("sinkId", sinkId);
        getter.put("src", src);
        getter.put("volume", volume);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmlmediaelement(ref, autoplay, controls, crossOrigin, currentTime, defaultTime, defaultMuted, defaultPlaybackRate, disabledRemotePlayback, duration, " +
                "ended, loop, mediaGroup, mozAudioCaptured, mozFragmentEnd, mozSampleRate, muted, networkState, paused, playbackRate, " +
                "preload, preservesPitch, readyState, seeking, sinkId, src, valume)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setBoolean(2, autoplay);
        ps.setBoolean(3, controls);
        ps.setString(4, crossOrigin);
        ps.setDouble(5, currentTime);
        ps.setDouble(6, defaultTime);
        ps.setBoolean(7, defaultMuted);
        ps.setDouble(8, defaultPlaybackRate);
        ps.setBoolean(9, disableRemotePlayback);
        ps.setDouble(10, duration);
        ps.setBoolean(11, ended);
        ps.setBoolean(12, loop);
        ps.setString(13, mediaGroup);
        ps.setBoolean(14, mozAudioCaptured);
        ps.setDouble(15, mozFragmentEnd);
        ps.setDouble(16, mozSampleRate);
        ps.setBoolean(17, muted);
        ps.setShort(18, networkState);
        ps.setBoolean(19, paused);
        ps.setDouble(20, playbackRate);
        ps.setString(21, preload);
        ps.setBoolean(22, preservesPitch);
        ps.setShort(23, readyState);
        ps.setBoolean(24, seeking);
        ps.setString(25, sinkId);
        ps.setString(26, src);
        ps.setDouble(27, volume);

        //ISNERTを実行する
        ps.executeUpdate();
        //次の子クラスへと紐づけるためのID取得
        int childID = -1;
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1);
        }
        return childID;
    }

    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        String sql = "insert into HTMLMediaElement(ref, autoplay, controls, crossOrigin, currentTime, defaultTime, defaultMuted, defaultPlaybackRate, disabledRemotePlayback, duration, " +
                "ended, loop, mediaGroup, mozAudioCaptured, mozFragmentEnd, mozSampleRate, muted, networkState, paused, playbackRate, " +
                "preload, preservesPitch, readyState, seeking, sinkId, src, valume)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setBoolean(2, autoplay);
            ps.setBoolean(3, controls);
            ps.setString(4, crossOrigin);
            ps.setDouble(5, currentTime);
            ps.setDouble(6, defaultTime);
            ps.setBoolean(7, defaultMuted);
            ps.setDouble(8, defaultPlaybackRate);
            ps.setBoolean(9, disableRemotePlayback);
            ps.setDouble(10, duration);
            ps.setBoolean(11, ended);
            ps.setBoolean(12, loop);
            ps.setString(13, mediaGroup);
            ps.setBoolean(14, mozAudioCaptured);
            ps.setDouble(15, mozFragmentEnd);
            ps.setDouble(16, mozSampleRate);
            ps.setBoolean(17, muted);
            ps.setShort(18, networkState);
            ps.setBoolean(19, paused);
            ps.setDouble(20, playbackRate);
            ps.setString(21, preload);
            ps.setBoolean(22, preservesPitch);
            ps.setShort(23, readyState);
            ps.setBoolean(24, seeking);
            ps.setString(25, sinkId);
            ps.setString(26, src);
            ps.setDouble(27, volume);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
