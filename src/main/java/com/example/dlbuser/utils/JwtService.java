package com.example.dlbuser.utils;

//@Service
//public class JwtService {
//
//    @Value("${jwt.secret-key}")
//    private String JWT_SECRET_KEY;
//
//    /*
//     * JWT 생성
//     *
//     * @param userId
//     *
//     * @return String
//     */
//    public String createJwt(Long userId) {
//        Date now = new Date();
//        return Jwts.builder()
//                .setHeaderParam("type", "jwt")
//                .claim("userIdx", userId)
//                .setIssuedAt(now)
//                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24 * 365)))
//                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
//                .compact();
//    }
//
//    /*
//     * Header에서 AUTHORIZATION 으로 JWT 추출
//     *
//     * @return String
//     */
//    public String getJwt() {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                .getRequest();
//        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        if (accessToken == null || accessToken.length() == 0) {
//            throw new BaseException(EMPTY_JWT);
//        }
//
//        return accessToken.replaceAll("^Bearer( )*", "");
//    }
//
//    /*
//     * JWT에서 userId 추출
//     *
//     * @return Long
//     *
//     * @throws BaseException
//     */
//    public Long getUserId() throws BaseException {
//        // 1. JWT 추출
//        String accessToken = getJwt();
//
//        // 2. JWT parsing
//        Jws<Claims> claims;
//        try {
//            claims = Jwts.parser()
//                    .setSigningKey(JWT_SECRET_KEY)
//                    .parseClaimsJws(accessToken);
//        } catch (Exception ignored) {
//            throw new BaseException(INVALID_JWT);
//        }
//
//        // 3. userIdx 추출
//        return claims.getBody().get("userId", Long.class);
//    }
//
//}
