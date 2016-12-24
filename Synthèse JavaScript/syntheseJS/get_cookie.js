String token = null;
Cookie[] cookies=req.getCookies();
if (cookies != null) {
	for (Cookie c : cookies) {
		if ("user".equals(c.getName()) && c.getSecure()) {
			token = c.getValue();
		} else if ("user".equals(c.getName()) && token == null){
			token = c.getValue();
		}
	}
}