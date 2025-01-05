//package ec.edu.uce.marketplace.controllers;
//
//import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.http.ResponseEntity;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    // Endpoint para obtener el usuario autenticado
//    @GetMapping("/me")
//    public ResponseEntity<Map<String, Object>> getCurrentUser(OAuth2AuthenticationToken authenticationToken) {
//        Map<String, Object> userDetails = new HashMap<>();
//        userDetails.put("username", authenticationToken.getName());
//        userDetails.put("authorities", authenticationToken.getAuthorities());
//        userDetails.put("attributes", authenticationToken.getPrincipal().getAttributes());
//        return ResponseEntity.ok(userDetails);
//    }
//
//    // Endpoint para probar el token OAuth2 (solo para flujos client_credentials)
//    @GetMapping("/token")
//    public ResponseEntity<Map<String, String>> getToken(
//            @RegisteredOAuth2AuthorizedClient("marketplace-client") OAuth2AccessToken accessToken) {
//        Map<String, String> tokenDetails = new HashMap<>();
//        tokenDetails.put("token", accessToken.getTokenValue());
//        tokenDetails.put("expiresAt", accessToken.getExpiresAt().toString());
//        return ResponseEntity.ok(tokenDetails);
//    }
//}
