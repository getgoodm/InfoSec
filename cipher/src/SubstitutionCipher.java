import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.*;

import static java.util.Map.Entry.comparingByValue;

public class SubstitutionCipher {

    //Функция для шифровки текста
    public static String encrypt(String key, String plaintext)
    {
        StringBuilder ciphertext = new StringBuilder();
        String[] keySpace = new String[key.length()];
        for(int i = 0; i < key.length(); i++) keySpace[i] = String.valueOf(key.charAt(i));

        for(int i = 0; i < plaintext.length(); i++)
        {
            int index = plaintext.charAt(i) - 65;
            int indexlow = plaintext.charAt(i) - 97;
            //Прописные буквы
            if (indexlow >=0 && indexlow <= keySpace.length)
                ciphertext.append( keySpace[indexlow]);
            //Заглавные буквы
            else if (index >=0 && index <= keySpace.length)
                ciphertext.append(keySpace[index].toUpperCase());
            else
                ciphertext.append(String.valueOf(plaintext.charAt(i)));
        }

        return ciphertext.toString();
    }
//Функция для дешифровки текста
    public static String decrypt(String key, String ciphertext)
    {
        StringBuilder plaintext = new StringBuilder();
        for(int i = 0; i < ciphertext.length(); i++)
        {
            char character = ciphertext.charAt(i);
            boolean fl = false;
            if (Character.isUpperCase(character)) {
                character = Character.toLowerCase(character);
                fl = true;
            }
            int index = key.indexOf(character);
            int asciiLow = index + 97;
            if (asciiLow >= 97 && asciiLow <=122)
                //Заглавные буквы
                if (fl == true)
                    plaintext.append(String.valueOf((char)asciiLow).toUpperCase());
            //Прописные буквы
                else
                    plaintext.append(String.valueOf((char)asciiLow));
            else
            {
                plaintext.append(String.valueOf(character));
            }
        }

        return plaintext.toString();
    }
    //Считает частоту букв
    public static HashMap<Integer, Integer> calcFrequency(String path) {
        HashMap<Integer, Integer> hash = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                for (int i=0; i<line.length(); i++) {
                    char c = line.charAt(i);
                    String alphabet = "abcdefghijklmnopqrstuvwxyz";
                    if (alphabet.indexOf(c)>=0) {
                        int value = hash.getOrDefault((int) c, 0);
                        hash.put((int) c, value + 1);
                    }
                }
            }
            reader.close();
            for (int key : hash.keySet()) {
                System.out.println((char) key + ": " + hash.get(key));
            }
            return hash;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return hash;
    }

    //Преобразование HashMap в LinkedHashMap и сортировка по значениям
    public static LinkedHashMap<Integer, Integer> sortMap(HashMap<Integer, Integer> hash) {
        LinkedHashMap<Integer, Integer> sorted = hash
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        for (int key : sorted.keySet()) {
            System.out.println((char) key + ": " + sorted.get(key));
        }
        return sorted;

    }
    //Запись в файл
    public static void writeFile(String path, String result) {
        try {
            FileOutputStream outputfis = new FileOutputStream(new File(path));
            outputfis.write(result.getBytes());
            outputfis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        StringBuffer keyString = null, inputString = null, outputString, cipheredOutput = null;
        try{
            /*
             * Считать ключ
             */
            int ch = -1;
            keyString = new StringBuffer("");
            FileInputStream keyfis = new FileInputStream(new File("D:/ciph/key.txt"));
            while( (ch = keyfis.read()) != -1) keyString.append((char) ch);
            keyfis.close();

            /*
             * Считать входной текст
             */
            inputString = new StringBuffer("");
            FileInputStream inputfis = new FileInputStream(new File("D:/ciph/MartinEden.txt"));
            while( (ch = inputfis.read()) != -1) inputString.append((char) ch);
            inputfis.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = "";
                //Зашифровать текст и вывести в файл
                String ciphertext = result = SubstitutionCipher.encrypt(keyString.toString(), inputString.toString());
                writeFile("D:/ciph/out.txt", ciphertext);
                //Посчитать и вывести частоту букв в исходном тексте
                System.out.println("original:");
                HashMap<Integer, Integer> originalMap = calcFrequency("D:/ciph/MartinEden.txt");
                //Посчитать и вывести частоту букв в зашифрованном тексте
                System.out.println("encrypted:");
                HashMap<Integer, Integer> encryptedMap = calcFrequency("D:/ciph/out.txt");
                //Отсортировать частоты букв по возрастанию:
                System.out.println("original sorted:");
                LinkedHashMap<Integer, Integer> sortedOriginal = sortMap(originalMap);
                System.out.println("encrypted sorted:");
                LinkedHashMap<Integer, Integer> sortedEncryptedMap = sortMap(encryptedMap);

                Iterator itr = sortedOriginal.entrySet().iterator();
                Iterator itr2 = sortedEncryptedMap.entrySet().iterator();
                //Хэш-таблица для пар букв
                HashMap<Integer, Integer> decryptMap = new HashMap<Integer, Integer>();
                while (itr.hasNext()) {

                    Map.Entry entry1 = (Map.Entry)itr.next();
                    int key1 = (int)entry1.getKey();
                    Map.Entry entry2 = (Map.Entry)itr2.next();
                    int key2 = (int)entry2.getKey();
                    decryptMap.put(key1, key2);
                }
                StringBuilder decryptCode = new StringBuilder();
                for (int key3 : decryptMap.keySet()) {
                    char ch = (char)(int)decryptMap.get(key3);
                    decryptCode.append(ch);
                    System.out.println((char)key3 + ": " + ch);
                }
                //Запись найденного ключа в файл
                writeFile("D:/ciph/foundKey.txt", decryptCode.toString() );
                //Расшифровка текста
                String decryptedtext = decrypt(decryptCode.toString(), ciphertext.toString() );
                writeFile("D:/ciph/decryptedText.txt", decryptedtext);

    }
}