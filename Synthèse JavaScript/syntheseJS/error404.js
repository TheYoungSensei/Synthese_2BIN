resp.setStatus(404);
resp.setContentType("text/html");
String msg="<html><body>Fichier non
trouve</body></html>";
byte[] msgBytes=msg.getBytes("UTF-8");
resp.setContentLength(msgBytes.length);
resp.setCharacterEncoding("utf-8");
resp.getOutputStream().write(msgBytes);