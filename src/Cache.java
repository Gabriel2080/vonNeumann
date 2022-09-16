public class Cache extends Memoria {
    private RAM ram;
    private int inicioDaCache = Integer.MIN_VALUE;
    private boolean modificacao = false;
    private int[] cache;

    public Cache(int tam, RAM ram) {
        super(tam);
        this.ram = ram;
        this.cache = new int[tam];
    }

    @Override
    int Read(int endereco) throws EnderecoInvalido {
        // ver se da Hit ou Miss
        HitOrMiss(endereco);
        // retorna o valor do endereco
        return cache[endereco - inicioDaCache];
    }

    @Override
    void Write(int endereco, int valor) throws EnderecoInvalido {
        // ver se da Hit ou Miss
        HitOrMiss(endereco);
        // coloca a frag que modificacao true
        modificacao = true;
        // atualiza o cache
        cache[endereco - inicioDaCache] = valor;
    }

    private void HitOrMiss(int endereco) throws EnderecoInvalido {
        // verifica se o endereco nao estoura a memoria
        ram.VerificaEndereco(endereco);
        // verifica se o chace esta vazio
        if (inicioDaCache == Integer.MIN_VALUE) {
            atualizaCache(endereco);
        } else if (!(endereco > inicioDaCache && endereco < (inicioDaCache + capacidade))) { // verifica se o endereco esta fora do cache
            if (modificacao) {
                gravaCache();
                modificacao = false;
            }
            atualizaCache(endereco);
        }
    }

    private void gravaCache() throws EnderecoInvalido {
        // grava o cache na memoria
        System.err.println("gravou na memoria");
        for (int i = 0; i < capacidade; i++) {
            ram.Write(inicioDaCache + i, cache[i]);
        }
    }

    private void atualizaCache(int endereco) throws EnderecoInvalido {
        // calculo que ajuste, para preenxer o cache todo
        // atualiza o cache
        System.out.println("atulizou cache");
        inicioDaCache = (endereco+capacidade > ram.capacidade) ? ram.capacidade-capacidade : endereco;
        for (int i = 0; i < capacidade; i++) {
            cache[i] = ram.Read(inicioDaCache + i);
        }
    }
}
