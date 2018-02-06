package com.example.nero.estateagent.ImageDownloadService;

import com.example.nero.estateagent.com.nero.service.AbstractService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Nero on 03/04/2017.
 */

public class ImageDownloadService extends AbstractService {

    private String imageURL;
    private String filePath;

    public ImageDownloadService(String URL, String filePath){
        this.imageURL = URL;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;

        try{
            HttpURLConnection httpURLConnection = createConection(imageURL);
            File file = new File(filePath);
            file.getParentFile().mkdir();

            httpURLConnection.setReadTimeout(0);
            httpURLConnection.setConnectTimeout(120000);

            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream, 1024 * 5);
            fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[5 * 1024];

            int len;
            while((len = bufferedInputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer, 0, len);
            }

            fileOutputStream.flush();

            super.serviceCallComplete(false);
        }catch (Exception ex){
            ex.printStackTrace();
            super.serviceCallComplete(true);
        }finally{
            if(fileOutputStream != null && bufferedInputStream != null){
                try {
                    fileOutputStream.close();
                    bufferedInputStream.close();
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
    }


    private HttpURLConnection createConection(String urlString)
            throws MalformedURLException, IOException{

        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = null;
        boolean defaultRedirect = HttpURLConnection.getFollowRedirects();

        HttpURLConnection.setFollowRedirects(false);

        if(url.getProtocol().equals("https")){
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } else{
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }

        httpURLConnection.connect();

        String redirectTo = httpURLConnection.getHeaderField("Location");

        if(redirectTo != null && redirectTo.length() > 0){
            httpURLConnection.disconnect();
            httpURLConnection = createConection(redirectTo);
        }

        HttpURLConnection.setFollowRedirects(defaultRedirect);
        return httpURLConnection;
    }

    public ImageDownloadService(String imageURL){
        this.imageURL = imageURL;
    }

    public String getFilePath(){
        return this.filePath;
    }
}
