package com.zjyk.repair.modle.download;


import com.zjyk.repair.modle.net.RPBaseObsever;

public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);

    void getObsever(RPBaseObsever baseObsever);
}