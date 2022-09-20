public class CacheDireto extends Memoria {
    private int k;
    private int totalCache;
    private int totalRam;
    private int nbits_w;
    private int nlinhas;
    private int nbits_r;
    private int nbits_t;
    private CacheD[] cacheLine;
    private RAM ram;

    /*
    public CacheDireto(int capacidade) {
        super(capacidade);
        //TODO Auto-generated constructor stub
    }
     */

    public CacheDireto(int k, int totalCache, int totalRam, RAM ram) {
        // colocando o tamanho total da cache
        super(totalRam * 1024 * 1024);

        // calculando o tamanho de cada coisa
        this.k = k;
        this.totalCache = totalCache * 1024;
        this.totalRam = totalRam * 1024 * 1024;

        // amazenado a referencia da memoria ram
        this.ram = ram;

        // calculando o numero de bits para cada coisa
        nbits_w = flog2(this.k);
        nlinhas = (int)(this.totalCache / this.k);
        nbits_r = flog2(nlinhas);
        nbits_t = flog2(this.totalRam) - nbits_w - nbits_r;

        // criar/aloca a quantidade de linhas da cache
        cacheLine = new CacheD[nlinhas];

        // inicializando as linhas da cache
        for (int i = 0; i < nlinhas; i++) {
            cacheLine[i] = new CacheD(this.k, this.ram);
        }

        // um print para olhar o que esta acontecendo
        System.out.println("nbits_w = " + nbits_w);
        System.out.println("nbits_r = " + nbits_r);
        System.out.println("nbits_t = " + nbits_t);
    }

    @Override
    int Read(int endereco) throws EnderecoInvalido {
        // verifica se o endereco nao estoura a memoria
        super.VerificaEndereco(endereco);
        // pegar o T,R,W,S do endereco
        DataTRWS trws = getTRWS(endereco);
        // verificar/fazer se deu hit ou miss
        HitOrMiss(trws);
        // retornar o valor da cache
        return cacheLine[trws.getR()].Read(trws.getW());
    }

    @Override
    void Write(int endereco, int valor) throws EnderecoInvalido {
        // verifica se o endereco nao estoura a memoria
        super.VerificaEndereco(endereco);
        // pegar o T,R,W,S do endereco
        DataTRWS trws = getTRWS(endereco);
        // verificar/fazer se deu hit ou miss
        HitOrMiss(trws);
        // escrever o valor na cache
        cacheLine[trws.getR()].Write(trws.getW(), valor);
    }

    private DataTRWS getTRWS(int endereco) {
        // calcula o T,R,W,S para o endereco
        int w = endereco % (1 << nbits_w); // possicao na linha
        int r = (endereco >> nbits_w) % nlinhas; // linha que esta na cache
        int t = endereco >> (nbits_w + nbits_r); // valida o endereco correto da linha
        int s = endereco >> nbits_w; // frame que esta na memoria ram

        // retorna o resultado em uma classe/struct -- nao usei record do java por causa da compatibilidade com versoes anteriores
        return new DataTRWS(t, r, w, s) ;
    }

    private int flog2(int v) {
        int nb = -1;
        while (v > 0) {
            ++nb;
            v >>= 1;
        }
        return nb;
    }
    
    private void HitOrMiss(DataTRWS trws) throws EnderecoInvalido {

        /*
        // verifica se o endereco nao estoura a memoria
        super.VerificaEndereco(endereco);
        // verifica se o chace esta vazio
        if (inicioDaCache() == Integer.MIN_VALUE) {
            atualizaCache(endereco);
        } else if (!(endereco > inicioDaCache() && endereco < (inicioDaCache() + capacidade))) { // verifica se o endereco esta fora do cache
            if (modificacao) {
                gravaCache();
                modificacao = false;
            }
            atualizaCache(endereco);
        }
         */

        // outra versao melhor
        // verifica se o endereco esta na cache
        // ou se a cache esta vazia
        // para atualizar a cache e a memoria ram caso seja necessario
        if (trws.getT() != cacheLine[trws.getR()].getT() || cacheLine[trws.getR()].getT() == Integer.MIN_VALUE) {
            // atualiza a linha da cache
            // ele vai verificar se teve modificacao na memoria cache e vai atualizar a memoria ram
            // -- dentro do metodo atualizaCache
            cacheLine[trws.getR()].atualizaCache(trws.getT(), trws.getS());
        }

        // nesse ponto ele ja viu se teve hit ou miss e atualizou a cache e atualizou a memoria ram

    }
}
