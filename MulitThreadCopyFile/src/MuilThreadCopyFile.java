import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by luca on 2016/9/15.
 */
public class MuilThreadCopyFile {
    File src = null, dsc = null;
    int threadcount;
    int buffsize = 1024 * 1024;

    public MuilThreadCopyFile(String src, String dsc, int threadcount) {
        File file = new File(src);
        if (file.isFile() && file.exists()) this.src = file;
        file = new File(dsc);
        this.dsc = file;

        if (threadcount > 0) this.threadcount = threadcount;

    }

    public static void main(String[] args) {
        new MuilThreadCopyFile("E:\\Videos\\天空之城-MP4\\天空之城-MP4.mp4", "E:\\FFOutput\\temp.mp3", 5).exceCopy();
    }

    public void exceCopy() {
        long size = src.length() / threadcount + 1;
        long mod = src.length() % threadcount;
        long startposi = 0, endposi = 0;
        //System.out.println(mod);
        for (int i = 1; i <= threadcount; i++) {
            startposi = endposi ;
            endposi = (startposi + size) > src.length() ? src.length() : (startposi + size);
            new CopyThread(startposi, endposi, i + "").start();
            //System.out.println("总文件大小：" + src.length() + "线程" + i + "开始位置：" + startposi + ",结束位置：" + endposi);
        }
    }

    private class CopyThread extends Thread {
        long startposi, endposi;

        public CopyThread(long startposi, long endposi, String name) {
            super();
            this.startposi = startposi;
            this.endposi = endposi;
            super.setName(name);
        }

        @Override
        public void run() {
            RandomAccessFile read = null, write = null;
            try {
                read = new RandomAccessFile(src, "r");
                read.seek(startposi);
                write = new RandomAccessFile(dsc, "rw");
                write.seek(startposi);
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] b = new byte[buffsize];
            int len;
            while (startposi < endposi) {
                try {
                    //len = read.read(b, 0, buffsize < (endposi - startposi + 1) ? buffsize : (int) (endposi - startposi + 1));
                    if(startposi+buffsize<endposi){
                        len=read.read(b);
                    }else{
                        len=read.read(b,0, (int) (endposi-startposi));
                    }
                    startposi += len;
                    write.write(b, 0, len);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            System.out.println("线程" + this.getName() + "执行完毕");
        }
    }

}
