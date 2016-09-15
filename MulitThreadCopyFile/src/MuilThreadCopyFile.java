import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by luca on 2016/9/15.
 */
public class MuilThreadCopyFile {
    private static final int buffsize = 1024 * 1024;
    private File src = null, dsc = null;
    private int threadcount;

    public MuilThreadCopyFile(String src, String dsc, int threadcount) {
        File file = new File(src);
        if (file.isFile() && file.exists()) this.src = file;
        file = new File(dsc);
        this.dsc = file;
        if (threadcount > 0) this.threadcount = threadcount;

    }

    public static void main(String[] args) {
        new MuilThreadCopyFile("E:\\FFOutput\\诛仙青云志11.mp3", "E:\\FFOutput\\temp.mp3", 5).execCopy();
    }

    public void execCopy() {

        if (src == null || dsc == null || threadcount == 0) {
            System.out.println("部分参数设置错误，无法复制！");
            return;
        }
        ;

        long size = src.length() / threadcount + 1;
        long mod = src.length() % threadcount;
        long startposi = 0, endposi = 0;
        //System.out.println(mod);
        Thread[] rs = new Thread[threadcount];
        for (int i = 1; i <= threadcount; i++) {
            startposi = endposi;
            endposi = (startposi + size) > src.length() ? src.length() : (startposi + size);
            rs[i - 1] = new CopyThread(startposi, endposi, i + "");
            rs[i - 1].start();
            //System.out.println("总文件大小：" + src.length() + "线程" + i + "开始位置：" + startposi + ",结束位置：" + endposi);
        }

        for (int i = 1; i <= threadcount; i++) {
            try {
                rs[i - 1].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//System.out.println("修改时间 ");
        dsc.setLastModified(src.lastModified());
    }

    private class CopyThread extends Thread {
        private long startposi, endposi;

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
                    if (startposi + buffsize < endposi) {
                        len = read.read(b);
                    } else {
                        len = read.read(b, 0, (int) (endposi - startposi));
                    }
                    startposi += len;
                    write.write(b, 0, len);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            //System.out.println("线程" + this.getName() + "执行完毕");
        }
    }

}
