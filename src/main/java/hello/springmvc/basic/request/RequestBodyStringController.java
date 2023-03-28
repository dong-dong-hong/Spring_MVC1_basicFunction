package hello.springmvc.basic.request;

import com.fasterxml.jackson.core.type.WritableTypeId;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.Wrapper;

@Slf4j
@Controller
public class RequestBodyStringController {

    @GetMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);

        response.getWriter().write("ok");
    }

    // 스프링 MVC는 다음 파라미터를 지원한다.
    // InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
    // OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력

    @GetMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}",messageBody);
        responseWriter.write("ok");
    }

    /*
      HttpEntity: HTTP header, body 정보를 편리하게 조회
      - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
      - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

      응답에서도 HttpEntity 사용 가능
      - 메시지 바디 정보 직접 반환(view 조회X)
      - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

      HttpEntity 를 상속받은 다음 객체들도 같은 기능을 제공한다.
      RequestEntity
      HttpMethod, url 정보가 추가, 요청에서 사용
      ResponseEntity
      HTTP 상태 코드 설정 가능, 응답에서 사용
      return new ResponseEntity<String>("Hello World", responseHeaders,HttpStatus.CREATED)
     */

    @GetMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {

        String messageBody = httpEntity.getBody();
        log.info("messageBody={}",messageBody);

        return new HttpEntity<>("ok");
    }

    /*
      @RequestBody
      - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
      - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

      @ResponseBody
      - 메시지 바디 정보 직접 반환(view 조회X)
      - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

      @RequestBody
        @RequestBody 를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회할 수 있다. 참고로 헤더 정보가
        필요하다면 HttpEntity 를 사용하거나 @RequestHeader 를 사용하면 된다.
        이렇게 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 @RequestParam ,
        @ModelAttribute 와는 전혀 관계가 없다.

        요청 파라미터 vs HTTP 메시지 바디
        요청 파라미터를 조회하는 기능: @RequestParam , @ModelAttribute
        HTTP 메시지 바디를 직접 조회하는 기능: @RequestBody

      @ResponseBody
        @ResponseBody 를 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다.
        물론 이 경우에도 view를 사용하지 않는다
     */

    @ResponseBody
    @GetMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}",messageBody);

        return "ok";
    }
}
