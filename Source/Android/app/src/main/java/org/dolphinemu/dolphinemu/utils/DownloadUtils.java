package org.dolphinemu.dolphinemu.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.net.URL;
import java.net.HttpURLConnection;

import android.os.Environment;
import android.os.Handler;

public class DownloadUtils implements Runnable
{
  private Handler mHandler = null;
  private File mDownloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
  private DownloadCallback mCallback;
  private final String mUrl;
  private File mFile = null;

  public DownloadUtils(Handler handler, String url)
  {
    mHandler = handler;
    mUrl = url;
  }

  public DownloadUtils(String url)
  {
    mUrl = url;
  }

  public void start()
  {
    Thread downloadThread = new Thread(this);
    downloadThread.start();
  }

  @Override
  public void run()
  {
    downloadFile(mUrl);
  }

  private void downloadFile(String sUrl)
  {
    try {
      URL url = new URL(sUrl);

      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();
      if (mHandler != null) { mHandler.post(() -> mCallback.onDownloadStart()); }

      String filename = "download.apk";
      String fieldContentDisp = urlConnection.getHeaderField("Content-Disposition");
      if (fieldContentDisp != null && fieldContentDisp.contains("filename=")) {
        filename = fieldContentDisp.substring(fieldContentDisp.indexOf("filename=") + 9);
      }
      mFile = new File(mDownloadPath, filename);

      FileOutputStream fileOutput = new FileOutputStream(mFile);
      InputStream inputStream = urlConnection.getInputStream();

      float totalSize = urlConnection.getContentLength();
      int downloadedSize = 0;

      byte[] buffer = new byte[1024];
      int bufferLength = 0;

      while ((bufferLength = inputStream.read(buffer)) > 0) {
        fileOutput.write(buffer, 0, bufferLength);
        downloadedSize += bufferLength;

        int progress = (int) (downloadedSize / totalSize * 100);
        if (mHandler != null) { mHandler.post(() -> mCallback.onDownloadProgress(progress)); }
      }
      fileOutput.close();
      urlConnection.disconnect();

      if (mHandler != null) { mHandler.post(() -> mCallback.onDownloadComplete()); }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      if (mHandler != null) { mHandler.post(() -> mCallback.onDownloadError()); }
      if (mFile != null)
      {
        mFile.delete();
      }
    }
  }

  public void setCallbackListener(DownloadCallback listener)
  {
    mCallback = listener;
  }

  public void setDownloadPath(String path)
  {
    mDownloadPath = new File(path);
  }
}
