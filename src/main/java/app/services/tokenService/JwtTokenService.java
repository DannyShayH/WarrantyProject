package app.services.tokenService;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class JwtTokenService implements TokenService {
    private final byte[] secret;
    private final String issuer;
    private final long tokenExpireTimeSeconds;

    public JwtTokenService(String secret, String issuer, long tokenExpireTimeSeconds){
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.issuer = issuer;
        this.tokenExpireTimeSeconds = tokenExpireTimeSeconds;
    }

    @Override
    public String createToken(AuthUserDTO user) {
        try{
            Instant now = Instant.now();

            var claims = new JWTClaimsSet.Builder()
                    .subject(user.email())
                    .issuer(issuer)
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(now.plusSeconds(tokenExpireTimeSeconds)))
                    .claim("roles", user.roles())
                    .build();
            var jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
            jwt.sign(new MACSigner(secret));
            return jwt.serialize();
        } catch(JOSEException e){
            throw new RuntimeException("Token signing failed", e);
        }
    }

    @Override
    public AuthUserDTO parseAndValidate(String token) {
        try{
            var jwt = SignedJWT.parse(token);

            if(!jwt.verify(new MACVerifier(secret))) throw new IllegalArgumentException("Bad signature");

            var c = jwt.getJWTClaimsSet();
            if(!issuer.equals(c.getIssuer())) throw new IllegalArgumentException("Bad issuer");
            if(c.getExpirationTime() == null || c.getExpirationTime().before(new Date()))
                throw new IllegalArgumentException("Token expired");

            String email = c.getSubject();
            @SuppressWarnings("unchecked")
            Set<String> roles = Set.copyOf((List<String>) c.getClaim("roles"));

            return new AuthUserDTO(email, roles);
        } catch(ParseException | JOSEException e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }
}