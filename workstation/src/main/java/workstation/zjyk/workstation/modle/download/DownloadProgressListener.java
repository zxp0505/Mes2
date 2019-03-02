package workstation.zjyk.workstation.modle.download;

import io.reactivex.disposables.Disposable;
import workstation.zjyk.workstation.modle.net.WSBaseObsever;

public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);

    void getObsever(WSBaseObsever baseObsever);
}