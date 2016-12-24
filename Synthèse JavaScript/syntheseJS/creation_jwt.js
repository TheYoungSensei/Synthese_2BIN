Map<String, Object> claims = new HashMap<String, Object>();
claims.put("username", email);
claims.put("id", id);
claims.put("ip", req.getRemoteAddr());
String ltoken = new JWTSigner(Config.JWTSecret).sign(claims);