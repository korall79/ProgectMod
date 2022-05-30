import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Сryptographer {
    public static void main(String[] args) {

        try (RandomAccessFile randomAccessFile = new RandomAccessFile("C:\\Users\\User\\IdeaProjects\\ProgectModul1\\out\\production\\ProgectModul1\\secret", "rw");
             FileChannel channel = randomAccessFile.getChannel()) {

            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            StringBuilder builder = new StringBuilder();
            channel.read(byteBuffer);
            byteBuffer.flip();

            while (byteBuffer.hasRemaining()) {
                builder.append((char) byteBuffer.get());
            }
            System.out.println(builder);

            int key = 3;

            char[] simvol = new char[builder.length()];
            for (int i = 0; i < builder.length(); i++) {
                if (Character.isLetter(builder.charAt(i)) && Character.isUpperCase(builder.charAt(i))) {
                    simvol[i] = (char) (((((int) (builder.charAt(i))) - 65 + key) % 26) + 65);
                } else if (Character.isLetter(builder.charAt(i)) && Character.isLowerCase(builder.charAt(i)))
                    simvol[i] = (char) (((((int) (builder.charAt(i))) - 97 + key) % 26) + 97);
                else simvol[i]= builder.charAt(i);
            }

            System.out.println(simvol);
            String encrypted = String.copyValueOf(simvol);

            ByteBuffer byteBuffer1 = ByteBuffer.allocate(encrypted.getBytes().length);
            byteBuffer1.put(encrypted.getBytes());
            byteBuffer1.flip();
            channel.write(byteBuffer1);
            Path filepath = Paths.get("ciphr.txt");
            Files.createFile(filepath);
            Files.write(filepath, encrypted.getBytes());


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}