import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.io.FileReader;
import java.util.Scanner;
import java.math.*;


public class Party {
    private long a;
    private long p;

    public long getP() {
        return p;
    }

    public long getG() {
        return g;
    }

    private long g;
    private long open_key;

    public void setForeign_key(long foreign_key) {
        this.foreign_key = foreign_key;
    }

    private long foreign_key;
    private long secret_key;
    private String name;

    public void setP(long p) {
        this.p = p;
    }

    public void setG(long g) {
        this.g = g;
    }

    public Party(String name) {
        this.name = name;
        Random random = new Random();
        this.a = 3+random.nextInt(10);
        System.out.println(this.name + " a=" + this.a);

    }
    //Запись в файл
    public void writeToFile(String s) {

        try (FileWriter writer = new FileWriter("channel.txt", true)){
            writer.write(s);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //Генерирует g и p
    public void generateParams() {
        boolean fl = false;
        long g = 0;
        //Генерирует g
        while (fl == false) {
            //Генерируется n-битовая последовательность
            long num = Diffie.genNum(4);
            //Проверка по таблице простых чисел
            if (Diffie.tableCheck(num)) {
                //Проверка с помощью теста Рабина-Миллера
                if (Diffie.Rabin(num, 5)) {
                    g = num;
                    fl = true;
                }
            }
        }
        fl = false;
        long p = 0;
        Random random = new Random();
        //Находит p
        while (fl== false) {
            int rng = 2 + random.nextInt(100);
            long num = g*rng + 1;
            //Проверка по таблице простых чисел
            if (Diffie.tableCheck(num)) {
                //Проверка с помощью теста Рабина-Миллера
                if (Diffie.Rabin(num, 5)) {
                    p = num;
                    fl = true;
                }
            }


        }
        this.p = p;
        this.g = g;
        System.out.println(this.name + " сгенерировал(а) параметры: p = " + this.p + " g = " + this.g);
        //Запись параметров в файл
        writeToFile(p + " " + g);
    }

    //Функция для чтения параметров p и g из файла
    public void readParams(){
        FileReader fr= null;
        try {
            fr = new FileReader("channel.txt");
            Scanner scan = new Scanner(fr);
            this.p = scan.nextInt();
            this.g = scan.nextInt();
            fr.close();
            System.out.println(this.name + " получил(а) параметры: p= " + this.p + " g = " + this.g );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Вычисление открытого ключа и его запись в файл
    public void calcOpenKey(){
        this.open_key = Diffie.modPow(this.g, this.a, this.p);
        System.out.println(this.name + " : открытый ключ " + this.open_key);
        writeToFile("\n" + String.valueOf(this.open_key));
    }

    //Получение открытого ключа
    public long receiveKey(){
        FileReader fr= null;
        try {
            fr = new FileReader("channel.txt");
            Scanner scan = new Scanner(fr);
            String tmp = "";
            while (scan.hasNextLine())
            {
                tmp = scan.nextLine();
            }
            this.foreign_key = (long) Integer.parseInt(tmp);
            fr.close();
            System.out.println(this.name + " получил(а) открытый ключ: " + this.foreign_key);
            return foreign_key;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
    //Вычисление секретного ключа
    public void calcSecret(){
        this.secret_key = Diffie.modPow(this.foreign_key, this.a, this.p);
        System.out.println(this.name + " вычислил(а) секретный ключ: " + this.secret_key);
    }
}
