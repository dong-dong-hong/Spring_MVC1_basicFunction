package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
// @Controller는 반환 값이 String이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다.
// @RestController는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력 한다. 따라서 실행 결과로 ok 메시지를 받을 수 있다.
// @RequestBody와 관련이 있다.
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    /*
      기본 요청
      둘다 허용 /hello-basic, /hello-basic/
      HTTP 메서드 모두 허용 GET, HEAD, POST, PUT, PATHH, DELETE
     */
    @RequestMapping(value = "/hello-basic")
    // /hello-basic URL 호출이 오면 이 메서드가 실행되도록 매핑한다.
    // 대부분의 속성을 배열[]로 제공하므로 다중 설정이 가능하다. {"/hello-basic", "/hello-go"}
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /*
      편리한 축약 애노테이션 (코드보기)
      @GetMapping
      @PostMapping
      @PutMapping
      @DeleteMapping
      @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }
    /*
     PathVariable 사용
     변수명이 같으면 생략 가능
     @PathVariable("userId") String userId -> @PathVariable userId
     /mapping/userA
    */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath userId={}", data);
        return "ok";
    }

    /*
      PathVariable 사용 다중
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /*
     파라미터로 추가 매핑
     params="mode",
     params="!mode"
     params="mode=debug"
     params="mode!=debug" (! = )
     params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /*
     특정 헤더로 추가 매핑
     headers="mode",
     headers="!mode"
     headers="mode=debug"
     headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /*
     Content-Type 헤더 기반 추가 매핑 Media Type
     consumes="application/json"
     consumes="!application/json"
     consumes="application/*"
     consumes="*\/*"
     MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /*
     Accept 헤더 기반 Media Type
     produces = "text/html"
     produces = "!text/html"
     produces = "text/*"
     produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
