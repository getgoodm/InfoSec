import java.util.BitSet;
public class testbit {
    public static void main(String[] args) {
         BitSet seq = new BitSet(3);
         seq.set(0, true);
         seq.set(1, true);
         seq.set(2, true);
         seq.set(3, true);
         seq.set(4, true);
         System.out.println(seq.toLongArray()[0]);
         long a = seq.toLongArray()[0];
         System.out.println(a);

    }

}
