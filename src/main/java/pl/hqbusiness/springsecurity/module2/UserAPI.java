package pl.hqbusiness.springsecurity.module2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserAPI {

  @GetMapping(path = "/hello-admin")
  public String helloAdmin(Principal principal) {
    return String.format("Cześć admin %s", principal.getName());
  }

  @GetMapping(path = "/hello-user")
  public String helloUser(Principal principal) {
    return String.format("Cześć user %s", principal.getName());
  }

  @GetMapping(path = "/hello-stranger")
  public String helloStranger(Principal principal) {
    return principal != null ? String.format("Cześć %s", principal.getName()) : "Cześć nieznajomy";
  }

  @GetMapping(path = "/bye")
  public String bye() {
    return "Papa";
  }
}
