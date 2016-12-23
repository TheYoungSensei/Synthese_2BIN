@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
throws ServletException, IOException {
try {
....
} catch (Exception e) {
e.printStackTrace(); // pour logger l'erreur
resp.setStatus(500); // erreur au niveau du serveur
resp.setContentType("text/html");
byte[] msgBytes=e.getMessage().getBytes("UTF-8");
resp.setContentLength(msgBytes.length);
resp.setCharacterEncoding("utf-8");
resp.getOutputStream().write(msgBytes);
}
}