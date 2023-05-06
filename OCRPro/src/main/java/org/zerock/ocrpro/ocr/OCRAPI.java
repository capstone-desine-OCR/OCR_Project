package org.zerock.ocrpro.ocr;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *         OCRAPI의 메인 기능부분
 *         싱글톤 getInstance를 통해서 선언해서 사용할수 있게 만들었음.
 *         해당 이미지 파일을 getArray함수에 넣으면 List<List<String>>형식으로 List<String>객체에 하나씩 들어 있을 수 있도록 사용
 *         writemultipart부분은 자세히 보진 않아서 잘 모르겠으나 response 보낼때 초기 세팅 같아 보임
 */
public class OCRAPI {


    /**
     * 본인의 APIURL 입력
     */
    private String apiURL = "";

    /**
     * 본인의 secretKey 입력
     */
    private String secretKey = "";

    static OCRAPI instance;

    public static OCRAPI getInstance(){
        if(instance == null){
            instance = new OCRAPI();
        }
        return instance;
    }

    public List<List<String>> getArray(String imageFile){
        try {

            // API request 설정
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
            String postParams = json.toString();

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            long start = System.currentTimeMillis();
            File file = new File(imageFile);
            writeMultiPart(wr, postParams, file, boundary);
            wr.close();

            //request 받아옴
            int responseCode = con.getResponseCode();
            InputStream inputStream = null;
            System.out.println(responseCode);
            if (responseCode == 200) {
                inputStream = con.getInputStream();
            } else {
                inputStream = con.getErrorStream();
            }
// json데이터 분류
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            JsonObject jsonResponse = new Gson().fromJson(reader, JsonObject.class);
            JsonArray imagesArr2 = jsonResponse.getAsJsonArray("images");
            List<List<String>> resultList = new ArrayList<>();
            List<String> currentRow = new ArrayList<>();
            for (int i = 0; i < imagesArr2.size(); i++) {
                JsonObject imageObj2 = imagesArr2.get(i).getAsJsonObject();
                JsonArray fieldsArr = imageObj2.getAsJsonArray("fields");
                for (int j = 0; j < fieldsArr.size(); j++) {
                    JsonObject fieldObj = fieldsArr.get(j).getAsJsonObject();
                    if (fieldObj.get("lineBreak").getAsBoolean()) {
                        currentRow.add(fieldObj.get("inferText").getAsString());
                        resultList.add(currentRow);
                        currentRow = new ArrayList<>();
                    } else {
                        currentRow.add(fieldObj.get("inferText").getAsString());
                    }
                }
            }
            resultList.add(currentRow);

            return resultList;

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }


    private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws
            IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString
                    .append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"" + file.getName() + "\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }

}
