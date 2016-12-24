Cookie cookie = new Cookie("user", ltoken);
cookie.setPath("/");
cookie.setMaxAge(60 * 60 * 24 * 365); 
resp.addCookie(cookie);