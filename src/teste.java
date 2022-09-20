public class teste {
    public static int flog2(int v) {
        int nb = -1;
        while (v > 0) {
            ++nb;
            v >>= 1;
        }
        return nb;
    }

    public static void main(String[] args) {
        int K = 32;
        int total_cache = 4 * 1024;
        int total_ram = 16 * 1024 * 1024;

        int nbits_w = flog2(K);
        int nlinhas = (int) (total_cache / K);
        int nbits_r = flog2(nlinhas);
        int nbits_t = flog2(total_ram) - nbits_w - nbits_r;

        System.out.println("nbits_w = " + nbits_w);
        System.out.println("nbits_r = " + nbits_r);
        System.out.println("nbits_t = " + nbits_t + "\n");

        int x = 10_560_325;
        int w = x % (1 << nbits_w);
        int r = (x >> nbits_w) % nlinhas;
        int t = x >> (nbits_w + nbits_r);
        int s = x >> nbits_w;
        System.out.println("w = " + w);
        System.out.println("r = " + r);
        System.out.println("t = " + t);
        System.out.println("s = " + s);
        System.out.println("memoria = " + Integer.toBinaryString(x));
        System.out.println("w = " + Integer.toBinaryString(w));
        System.out.println("r = " + Integer.toBinaryString(r));
        System.out.println("t = " + Integer.toBinaryString(t));
        System.out.println("s = " + Integer.toBinaryString(s));

    }
}
