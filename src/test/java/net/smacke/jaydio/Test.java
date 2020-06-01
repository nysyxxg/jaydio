package net.smacke.jaydio;

import java.io.File;
import java.io.IOException;

/**
 * jaydio, 在Linux中，绕过文件页面缓存，一个执行直接 I/O的Java库
 * https://www.kutu66.com//GitHub/article_133204
 */
public class Test {
    
    public static void main(String[] args) throws IOException {
        int bufferSize = 1 << 23; // Use 8 MiB buffers
        byte[] buf = new byte[bufferSize];
        DirectRandomAccessFile fin = new DirectRandomAccessFile(new File("hello.txt"), "r", bufferSize);
        DirectRandomAccessFile fout = new DirectRandomAccessFile(new File("world.txt"), "rw", bufferSize);
        while (fin.getFilePointer() < fin.length()) {
            int remaining = (int) Math.min(bufferSize, fin.length() - fin.getFilePointer());
            fin.read(buf, 0, remaining);
            fout.write(buf, 0, remaining);
        }
        fin.close();
        fout.close();
    }
}
