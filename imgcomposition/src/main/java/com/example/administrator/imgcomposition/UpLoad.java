package com.example.administrator.imgcomposition;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * �ϳɺ��ͼƬ�ϴ���������
 * Created by Administrator on 2015/8/24.
 */
public class UpLoad {
    private static final int TIME_OUT = 10*10000000; //��ʱʱ��
    private static final String CHARSET = "utf-8";
    public static final String SUCCESS="1";
    public static final String FAILURE = "0";

    public static String fileImageUpload (File file,String RequestURL){
        String BOUNDARY = UUID.randomUUID().toString(); //�߽�ʶ�� �������
        String PREFIX = "--";
        String LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; //��������
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);  //����������
            conn.setDoOutput(true);  //���������
            conn.setUseCaches(false);  //������ʹ�û���
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", CHARSET);
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type",CONTENT_TYPE+";boundary="+BOUNDARY);
            if (file!=null){
                OutputStream outputStream = conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputStream);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);

                sb.append("Content-Disposition: form-data; name=\"img\";filename=\""+file.getName()+"\""+LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes))!=-1){
                    dos.write(bytes,0,len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();

                int res = conn.getResponseCode();
                if (res == 200){
                    return SUCCESS;
                }
            }
        } catch (Exception e){}
        return FAILURE;
    }
}
