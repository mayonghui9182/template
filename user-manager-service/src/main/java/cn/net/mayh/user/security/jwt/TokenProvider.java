package cn.net.mayh.user.security.jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * Token 生成工具类
 *
 * @author duyq06
 */
@Component
public class TokenProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private static final String AUTH_USER_KEY = "user";
  // 该JWT的签发者
  static final String CLAIM_KEY_ISS = "iss";
  // 该JWT所面向的用户
  static final String CLAIM_KEY_USERNAME = "sub";
  // 接收该JWT的一方
  static final String CLAIM_KEY_AUDIENCE = "aud";
  // 在什么时候签发的(UNIX时间)
  static final String CLAIM_KEY_CREATED = "iat";

  // 设备类型
  static final String AUDIENCE_UNKNOWN = "unknown";
  static final String AUDIENCE_WEB = "web";
  static final String AUDIENCE_MOBILE = "mobile";
  static final String AUDIENCE_TABLET = "tablet";

  @Value("${ikfa.security.authentication.jwt.header}")
  private String tokenHeader;

  // 秘钥
  @Value(value = "${ikfa.security.authentication.jwt.secret}")
  private String secretKey;

  // token 签发者
  @Value(value = "${ikfa.security.authentication.jwt.iss}")
  private String iss;

  // token 有效时间 秒
  @Value(value = "${ikfa.security.authentication.jwt.token-validity-in-seconds}")
  private long tokenValidityInMilliseconds;

  // token 记住我 时有效时间 秒
  @Value(value = "${ikfa.security.authentication.jwt.token-validity-in-seconds-for-remember-me}")
  private long tokenValidityInMillisecondsForRememberMe;

  @PostConstruct
  public void init() {
    this.tokenValidityInMilliseconds = 1000 * tokenValidityInMilliseconds;
    this.tokenValidityInMillisecondsForRememberMe = 1000 * tokenValidityInMillisecondsForRememberMe;
  }

  /**
   * token 中获取用户id主键
   *
   * @param token
   * @return
   */
  public String getUserCodeFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  /**
   * 获取token的签发日期
   *
   * @param token
   * @return
   */
  public Date getIssuedAtDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getIssuedAt);
  }

  /**
   * 获取 token 过期时间
   *
   * @param token
   * @return
   */
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  /**
   * 获取申请 token 的设备类型
   *
   * @param token
   * @return
   */
  public String getAudienceFromToken(String token) {
    return getClaimFromToken(token, Claims::getAudience);
  }

  /**
   * token 是否过期
   *
   * @param token
   * @return
   */
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  /**
   * 是否在修改密码之后生成的token
   *
   * @param created
   * @param lastPasswordReset
   * @return
   */
  private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
    return (lastPasswordReset != null && created.before(lastPasswordReset));
  }

  /**
   * 获取请求设备类型
   *
   * @param device
   * @return
   */
  private String generateAudience(Device device) {
    if (device == null) {
      return AUDIENCE_WEB;
    }
    String audience = AUDIENCE_UNKNOWN;
    if (device.isNormal()) {
      audience = AUDIENCE_WEB;
    } else if (device.isTablet()) {
      audience = AUDIENCE_TABLET;
    } else if (device.isMobile()) {
      audience = AUDIENCE_MOBILE;
    }
    return audience;
  }

  /**
   * 是否忽略token有效期
   *
   * @param token
   * @return
   */
  private Boolean ignoreTokenExpiration(String token) {
    String audience = getAudienceFromToken(token);
    return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    //转换成T 类型的数据
    return claimsResolver.apply(claims);
  }

  /**
   * 验证jwt token
   *
   * @param token 传入参数token
   * @return
   */
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }

  /**
   * 创建 token 设置 token 有效时间
   *
   * @param userDetails
   * @param rememberMe
   * @param device
   * @return
   */
  public String createToken(JwtUser userDetails, Boolean rememberMe, Device device) {
    String authorities =
        userDetails
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

    Date validity = this.calculateExpirationDate(new Date(), rememberMe);

    // 头部用于描述 JWT 的最基本的信息 如其类型以及签名所用的算法
    Map<String, Object> claims = new HashMap<String, Object>();

    claims.put(AUTH_USER_KEY, JSON.toJSONString(userDetails));
    claims.put(AUTHORITIES_KEY, authorities);

    String audience = generateAudience(device);
    claims.put(CLAIM_KEY_USERNAME, userDetails.getUcode());
    claims.put(CLAIM_KEY_AUDIENCE, audience);
    claims.put(CLAIM_KEY_ISS, iss);
    return generateToken(claims, validity, userDetails.getUcode(), audience);
  }

  /**
   * 生成 jwt token
   *
   * @param claims
   * @param validity
   * @return
   */
  String generateToken(Map<String, Object> claims, Date validity, String subject, String audience) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setAudience(audience)
        .setIssuedAt(new Date())
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

  /**
   * 判断 token 能否被刷新
   *
   * @param token
   * @param lastPasswordReset
   * @return
   */
  public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
    final Date created = getIssuedAtDateFromToken(token);
    return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
        && (!isTokenExpired(token) || ignoreTokenExpiration(token));
  }

  /**
   * 计算 token 过期时间
   *
   * @param createdDate
   * @param rememberMe
   * @return
   */
  private Date calculateExpirationDate(Date createdDate, Boolean rememberMe) {
    Date validity;
    long now = createdDate.getTime();
    if (rememberMe) {
      validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
    } else {
      validity = new Date(now + this.tokenValidityInMilliseconds);
    }
    return validity;
  }

  /**
   * 刷新 token
   *
   * @param token
   * @param rememberMe
   * @return
   */
  public String refreshToken(String token, Boolean rememberMe) {
    final Date createdDate = new Date();
    final Date expirationDate = calculateExpirationDate(createdDate, rememberMe);

    final Claims claims = getAllClaimsFromToken(token);
    claims.setIssuedAt(createdDate);
    claims.setExpiration(expirationDate);

    return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secretKey).compact();
  }

  /**
   * 获取 token 中权限信息
   *
   * @param token
   * @return
   */
  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    User principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  /**
   * 通过 token 获取登录用户信息
   *
   * @param token
   * @return
   */
  public JwtUser getSecurityUserFromToken(String token) {
    final Claims claims = getClaimsFromToken(token);
    String jsonUser = claims.get(AUTH_USER_KEY, String.class);
    JwtUser userDetails = JSON.parseObject(jsonUser, JwtUser.class);
    return userDetails;
  }

  /**
   * 通过 token 获取 claims 信息
   *
   * @param token
   * @return
   */
  private Claims getClaimsFromToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    return claims;
  }

  /**
   * 验证token 是否有效
   *
   * @param token
   * @param userDetails
   * @return
   */
  public Boolean validateToken(String token, UserDetails userDetails) {
    JwtUser user = (JwtUser) userDetails;
    final String userCode = this.getUserCodeFromToken(token);
    //final Date created = this.getIssuedAtDateFromToken(token);
    //final Date expiration = getExpirationDateFromToken(token);
    return (userCode.equals(user.getUcode()) && !isTokenExpired(token));
    //&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));

  }

  /**
   * 解析 token
   *
   * @param request
   * @return
   */
  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(tokenHeader);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }
}
