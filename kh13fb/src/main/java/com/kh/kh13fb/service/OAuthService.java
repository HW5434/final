package com.kh.kh13fb.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.kh.kh13fb.vo.KakaoLoginVO;

@Service
public class OAuthService {

	public String createKakaoToken(String code) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://kauth.kakao.com/oauth/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=" + "authorization_code" +
                                "&client_id=70d8db6c99799796a660966c718f3d6d"+
                                "&redirect_uri=http://localhost:3000/auth"+
                                "&code=" + code
                ))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());

        return jsonObject.getString("access_token");
    }
	
	public KakaoLoginVO getKakaoInfo(String accessToken) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://kapi.kakao.com/v2/user/me"))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("Authorization", "Bearer " + accessToken)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());
        
        JSONObject kakao_account = jsonObject.getJSONObject("kakao_account");
        JSONObject profile = kakao_account.getJSONObject("profile");
        String birthyear = kakao_account.getString("birthyear");
        String birthday = kakao_account.getString("birthday");
        String birth = String.format("%s-%s-%s", birthyear, birthday.substring(0, 2), birthday.substring(2));
        
        KakaoLoginVO kakaoUser = new KakaoLoginVO();
        kakaoUser.setId(jsonObject.getString("id"));
        kakaoUser.setName(kakao_account.getString("name"));
        kakaoUser.setPhoneNumber(cleanPhoneNumber(kakao_account.getString("phone_number")));
        kakaoUser.setEmail(kakao_account.getString("email"));
        kakaoUser.setNickname(profile.getString("nickname"));
        kakaoUser.setBirth(birth.trim());

        return kakaoUser;
    }
	
    public static String cleanPhoneNumber(String number) {
        String cleanedNumber = number.replaceAll("[^0-9]", "");
        if (cleanedNumber.startsWith("82")) {
            cleanedNumber = "0" + cleanedNumber.substring(2);
        }
        return cleanedNumber;
    }
	
//	public String getKaKaoAccessToken(String code) {
//
//	    String access_Token = "";
//	    String refresh_Token = "";
//	    String reqURL = "https://kauth.kakao.com/oauth/token";
//
//	    try {
//	        URL url = new URL(reqURL);
//	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//	        //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
//	        conn.setRequestMethod("POST");
//	        conn.setDoOutput(true);
//
//	        //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
//	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//	        StringBuilder sb = new StringBuilder();
//	        sb.append("grant_type=authorization_code");
//	        sb.append("&client_id=" + clientId); // TODO REST_API_KEY 입력
//	        sb.append("&redirect_uri=" + redirectUri); // TODO 인가코드 받은 redirect_uri 입력
//	        sb.append("&code=" + code);
//	        bw.write(sb.toString());
//	        bw.flush();
//
//	        //결과 코드가 200이라면 성공
//	        int responseCode = conn.getResponseCode();
//	        System.out.println("responseCode : " + responseCode);
//	        //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
//	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//	        String line = "";
//	        String result = "";
//
//	        while ((line = br.readLine()) != null) {
//	            result += line;
//	        }
//	        System.out.println("response body : " + result);
//
//	        //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
//	        JsonParser parser = new JsonParser();
//	        JsonElement element = parser.parse(result);
//
//	        access_Token = element.getAsJsonObject().get("access_token").getAsString();
//	        refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
//
//	        System.out.println("access_token : " + access_Token);
//	        System.out.println("refresh_token : " + refresh_Token);
//
//	        br.close();
//	        bw.close();
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//
//	    return access_Token;
//	}
	
}