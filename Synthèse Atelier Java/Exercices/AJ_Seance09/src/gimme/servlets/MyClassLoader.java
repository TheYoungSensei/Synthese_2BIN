package gimme.servlets;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

class MyClassLoader extends ClassLoader {
  protected Class findClass(String name) throws ClassNotFoundException {
    try {
      InputStream in = new BufferedInputStream(new FileInputStream(name));
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      byte[] buf = new byte[1024];
      int n = in.read(buf, 0, buf.length);
      while (n > 0) {
        out.write(buf, 0, n);
        n = in.read(buf, 0, buf.length);
      }
      in.close();
      byte[] data = out.toByteArray();
      Class<?> c = defineClass(null, data, 0, data.length);
      resolveClass(c);
      return c;
    } catch (FileNotFoundException ioe) {
      Class<?> cls = Class.forName(name);
      return cls;
    } catch (Throwable t) {

      t.printStackTrace();
      throw new ClassNotFoundException(name);
    }
  }
}
