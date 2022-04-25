package kr.co.hjsoft.controller;

import kr.co.hjsoft.dto.ApiDTO;
import kr.co.hjsoft.repository.ApiRepository;
import kr.co.hjsoft.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class OpenApiController {
    private final ApiService apiService;
    private final ApiRepository apiRepository;

    @GetMapping("/board/basketball")
    public String callApiWithJson(Model model) throws Exception {
        StringBuilder sb = new StringBuilder();

        // 1. URL을 만들기 위한 StringBuilder, 발급받은 인증키.
        StringBuilder urlBuilder = new StringBuilder("http://openAPI.seoul.go.kr:8088/69436867646a686a313031457758656b/json/ListPublicReservationSport/2/50/"); /*URL*/
        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성
        urlBuilder.append(URLEncoder.encode("농구장", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 5. 통신을 위한 메소드 SET.
        conn.setRequestMethod("GET");
        // 6. 통신을 위한 Content-type SET.
        conn.setRequestProperty("Content-type", "application/json");
        // 7. 통신 응답 코드 확인.
        System.out.println("Response code: " + conn.getResponseCode());
        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.

        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        // 10. 객체 해제.
        rd.close();
        conn.disconnect();
        List<ApiDTO> list = new ArrayList<>();

        JSONObject jObj;
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(sb.toString());
        JSONObject ListPublicReservationSport = (JSONObject) jsonObj.get("ListPublicReservationSport");
        JSONArray row = (JSONArray) ListPublicReservationSport.get("row");
        apiRepository.deleteAll();
        for (int i = 0; i < row.size(); i++) {
            jObj = (JSONObject) row.get(i);
            ApiDTO apiDTO = new ApiDTO();
            apiDTO.setApino((i+1));
            apiDTO.setGubun(jObj.get("GUBUN").toString());
            apiDTO.setMaxclassnm(jObj.get("MAXCLASSNM").toString());
            apiDTO.setMinclassnm(jObj.get("MINCLASSNM").toString());
            apiDTO.setSvcstatnm(jObj.get("SVCSTATNM").toString());
            apiDTO.setSvcnm(jObj.get("SVCNM").toString());
            apiDTO.setPayatnm(jObj.get("PAYATNM").toString());
            apiDTO.setPlacenm(jObj.get("PLACENM").toString());
            apiDTO.setUsetgtinfo(jObj.get("USETGTINFO").toString());
            apiDTO.setSvcurl(jObj.get("SVCURL").toString());
            apiDTO.setAreanm(jObj.get("AREANM").toString());
            apiDTO.setTelno(jObj.get("TELNO").toString());
            apiRepository.save(apiService.dtoToEntity(apiDTO)).getApino();
            list.add(apiDTO);

        }
        //4. model에 담아준다.
        List<ApiDTO> list1 = apiService.get();
        model.addAttribute("result", list1);
        return "/board/basketball";
    }

}