package xxl.com.json.common.okhttp;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/**
 * Created by xxl on 2017/12/28.
 */

public class ExMultipartBody extends RequestBody {
    private MultipartBody mMultipartBody;
    private int mCurrentLength;//上传的当前进度
    private UpLoadListener mUpLoadListener;
    public ExMultipartBody(MultipartBody multipartBody) {
        //静态代理 代理MultipartBody
        this.mMultipartBody = multipartBody;
    }

    public ExMultipartBody(MultipartBody multipartBody,UpLoadListener upLoadListener){
        this.mMultipartBody = multipartBody;
        this.mUpLoadListener = upLoadListener;
    }
    @Nullable
    @Override
    public MediaType contentType() {
        //实际调用还是由被代理对象调用
        return mMultipartBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mMultipartBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        //往服务器写数据会调用此方法
        //获取文件总长度
        final long totalLength = contentLength();
        Log.e("aaa", "writeTo: "+totalLength);
        //获取当前写入进度

        //又是静态代理
        ForwardingSink forwardingSink = new ForwardingSink(sink) {
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                mCurrentLength += byteCount;
                //边写出边监听进度回调出去
                if (mUpLoadListener != null){
                    mUpLoadListener.progress(totalLength,mCurrentLength);
                }
                super.write(source, byteCount);
            }
        };
        //转换为BufferSink
        BufferedSink bufferedSink = Okio.buffer(forwardingSink);
        mMultipartBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }
}
