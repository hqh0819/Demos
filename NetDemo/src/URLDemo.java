import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by luca on 2016/9/15.
 */
public class URLDemo {

    public static void main(String[]args) throws IOException {
        URL url=new URL("http://imgsrc.baidu.com/forum/w%3D580/sign=5977e6dbd900baa1ba2c47b37710b9b1/8023287adab44aede83995d3bb1c8701a18bfb49.jpg");
        InputStream is=url.openStream();
        OutputStream os=new FileOutputStream(new File("e:\\a.jpg"));

        byte[]buff=new byte[1024];
        int len;
        while((len=is.read(buff))!=-1)os.write(buff,0,len);

    }
}
