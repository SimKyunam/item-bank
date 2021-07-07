# Side Project - Demo Item Bank

### 1. Language & Skill
	- Spring Boot
	- Spring-data
	- Spring-security
	- jUint5
	- JPA
	- JWT
	- Swagger

### 2. Database
	- h2 (local, dev)
	- mysql	(release)

### 3. Was
	- apache Tomcat
	

## 사용법
### * Swagger 사용시 주의사항
1. JWT token이 필요하다.
2. JwtUtilTest 파일로 이동하여 createToken 테스트 코드를 실행하여 토큰 발행
3. 발생한 토큰을 http://localhost:[포트주소]/swagger-ui.html (ex: http://localhost:8080/swagger-ui.html)에 접근
4. 상단에 Authorize 버튼을 클릭하여, 해당 토큰값을 등록

```java
	@Test
	public void createToken(){
        User user = User.builder()
                .id(1L)
                .name("John")
                .password("1111")
                .account("John")
                .build();

        String token = jwtUtil.createToken(user);
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("token value : " + token);
        System.out.println("------------------------------------------------------------------------------");

        assertTrue(token.contains("."));
    }
```
