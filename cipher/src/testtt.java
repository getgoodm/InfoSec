import java.io.*;

public class testtt {
    public static void main(String[] args) {
        try {


            FileInputStream inputfis = new FileInputStream(new File("D:/ciph/inp.txt"));
            StringBuffer keyString = null;
            keyString = new StringBuffer("");
            int ch = -1;
            while ((ch = inputfis.read()) != -1) keyString.append((char) ch);
            inputfis.close();
            System.out.println(keyString);
            StringBuilder ciphertext = new StringBuilder();
            ciphertext.append(String.valueOf(keyString.charAt(0)));
            ciphertext.append(String.valueOf(keyString.charAt(5)));
            ciphertext.append(String.valueOf(keyString.charAt(6)));
            ciphertext.append(String.valueOf(keyString.charAt(4)));
            ciphertext.append(String.valueOf(keyString.charAt(18)));
            System.out.println(ciphertext.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream outputfis = new FileOutputStream(new File("D:/ciph/ott.txt"));
            //outputfis.write(keyString.getBytes());
            outputfis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
