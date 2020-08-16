package com.bfwg.rest;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bfwg.common.DeviceProvider;
import com.bfwg.dto.UserRequest;
import com.bfwg.model.Authority;
import com.bfwg.model.User;
import com.bfwg.model.UserRoleName;
import com.bfwg.model.UserTokenState;
import com.bfwg.response.MessageResponse;
import com.bfwg.security.TokenHelper;
import com.bfwg.security.auth.JwtAuthenticationRequest;
import com.bfwg.service.AuthorityService;
import com.bfwg.service.UserService;
import com.bfwg.service.impl.CustomUserDetailsService;

/**
 * Created by fan.jin on 2017-05-10.
 */

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	TokenHelper tokenHelper;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private DeviceProvider deviceProvider;
	
	@Lazy
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityService authorityService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response, Device device) throws AuthenticationException, IOException {

		// Perform the security
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));

		// Inject into security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// token creation
		User user = (User) authentication.getPrincipal();
		String jws = tokenHelper.generateToken(user.getUsername(), device);
		int expiresIn = tokenHelper.getExpiredIn(device);
		// Return the token
		return ResponseEntity.ok(new UserTokenState(jws, expiresIn));
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response,
			Principal principal) {

		String authToken = tokenHelper.getToken(request);

		Device device = deviceProvider.getCurrentDevice(request);

		if (authToken != null && principal != null) {

			// TODO check user password last update
			String refreshedToken = tokenHelper.refreshToken(authToken, device);
			int expiresIn = tokenHelper.getExpiredIn(device);

			return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
		} else {
			UserTokenState userTokenState = new UserTokenState();
			return ResponseEntity.accepted().body(userTokenState);
		}
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	/**
	 * @author NANA 16/08/2020 Signup for new user
	 **/
	@PostMapping("/signup")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest) {

		if (userService.existsByUsername(userRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
		}
		if (userService.existsByEmail(userRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
		}

		User newUser = new User(userRequest.getFirstName(), encoder.encode(userRequest.getPassword()),
				userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(),
				userRequest.getPhoneNumber(), userRequest.isEnabled());

		Set<Authority> roles = new HashSet<>();
		Set<String> strAuthorities = userRequest.getAuthorities();

		if (strAuthorities == null) {
			Authority userRole = authorityService.findByName(UserRoleName.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Role is not found."));
			roles.add(userRole);
		} else {
			strAuthorities.forEach(role -> {
				switch (role) {
				case "admin":
					Authority adminRole = authorityService.findByName(UserRoleName.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Role is not found."));
					roles.add(adminRole);
					break;
				default:
					Authority userRole = authorityService.findByName(UserRoleName.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Role is not found."));
					roles.add(userRole);
				}
			});
		}

		newUser.setAuthorities(roles);
		userService.save(newUser);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
}