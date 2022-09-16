public class CacheDireto extends Memoria {
    private int k;
    private int totalCache;
    private int totalRam;
    private int nbits_w;
    private int nlinhas;
    private int nbits_r;
    private int nbits_t;

    public CacheDireto(int capacidade) {
        super(capacidade);
        //TODO Auto-generated constructor stub
    }

    public CacheDireto(int k, int totalCache, int totalRam) {
        super(totalRam * 1024 * 1024);
        this.k = k;
        this.totalCache = totalCache * 1024;
        this.totalRam = totalRam * 1024 * 1024;

        nbits_w = flog2(this.k);
        nlinhas = (int)(this.totalCache / this.k);
        nbits_r = flog2(nlinhas);
        nbits_t = flog2(this.totalRam) - nbits_w - nbits_r;

        System.out.println("nbits_w = " + nbits_w);
        System.out.println("nbits_r = " + nbits_r);
        System.out.println("nbits_t = " + nbits_t);
    }

    @Override
    int Read(int endereco) throws EnderecoInvalido {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    void Write(int endereco, int valor) throws EnderecoInvalido {
        // TODO Auto-generated method stub
        
    }

    private DataTRW getTRW(int endereco) {
        int w = endereco % (1 << nbits_w);
        int r = (endereco >> nbits_w) % nlinhas;
        int t = endereco >> (nbits_w + nbits_r);

        return new DataTRW(t, r, w) ;
    }

    private int flog2(int v) {
        int nb = -1;
        while (v > 0) {
            ++nb;
            v >>= 1;
        }
        return nb;
    }
    
}
