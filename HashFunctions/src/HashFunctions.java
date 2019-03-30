import java.util.Scanner;
public class HashFunctions {

    private static int[] prime_numbers = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179,181,191,193,197,199};

    public static long hash1(int n) {
        String str;
        str = Integer.toString(n);
        long hash = 157;
        for (int i=0; i<str.length(); i++)
            hash = hash + (((hash << 2) * (int)str.charAt(i))  + (int)str.charAt(i)) * prime_numbers[i+5] ;
        return hash;
    }

    public static long hash2(int n) {
        String str;
        str = Integer.toString(n);
        long hash = 1315423911;
        for (int i=0; i<str.length(); i++)
            hash ^= ((hash<<4) + str.charAt(i)*prime_numbers[i+6] + (hash>>2));
        return hash;
    }

    public static long hash3(int n) {
        String str;
        str = Integer.toString(n);
        long hash=0;
        for (int i=0; i<str.length(); i++)
            hash = (long) Math.pow(prime_numbers[i+5], i+5) % prime_numbers[i+15];
        return hash;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите число");
        int n = in.nextInt();
        System.out.println(hash1(n));
        System.out.println(hash2(n));
    }
}