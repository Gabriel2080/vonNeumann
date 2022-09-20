public class CacheD{
    private RAM ram;
    private int inicioDaCacheFrame = Integer.MIN_VALUE;
    private boolean modificacao = false;
    private int[] cache;
    private int t = Integer.MIN_VALUE;
    private int capacidade;

    public CacheD(int tam, RAM ram) {
        this.capacidade = tam;
        this.ram = ram;
        this.cache = new int[tam];
    }

    
    int Read(int w) {
        // ler na cache o endereco
        return cache[w];
    }

    void Write(int w, int valor) {
        // marca que foi modificado
        modificacao = true;
        // escreve na cache o endereco
        cache[w] = valor;  
    }

    public int getT() {
        return t;
    }

    public void setInicioDaCacheFrame(int inicioDaCacheFrame) {
        this.inicioDaCacheFrame = inicioDaCacheFrame;
    }
    
    /*
    private void HitOrMiss(int endereco) throws EnderecoInvalido {
        // verifica se o endereco nao estoura a memoria
        ram.VerificaEndereco(endereco);
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
    }
    */

    private int inicioDaCache() {
        return (inicioDaCacheFrame * capacidade);
    }

    private void gravaCache() throws EnderecoInvalido {
        // grava o cache na memoria
        System.out.println("gravou na memoria");
        for (int i = 0; i < capacidade; i++) {
            ram.Write(inicioDaCache() + i, cache[i]);
        }
    }

    /*
    private void atualizaCache(int endereco) throws EnderecoInvalido {
        // calculo que ajuste, para preenxer o cache todo
        // atualiza o cache
        System.out.println("atulizou cache");
        inicioDaCache = (endereco+capacidade > ram.capacidade) ? ram.capacidade-capacidade : endereco;
        for (int i = 0; i < capacidade; i++) {
            cache[i] = ram.Read(inicioDaCache + i);
        }
    }
    */

    public void atualizaCache(int t, int s) throws EnderecoInvalido {
        // atuliza a tag da cache
        this.t = t;
        // verifica se foi modificado
        // se foi atualiza a memoria RAM
        if (modificacao == true) {
            gravaCache();
            modificacao = false;
        }

        // atualiza o cache
        for (int i = 0; i < capacidade; i++) {
            // endereco da memoria e o (s*capacidade) + i
            // exemplo: s = 1, capacidade = 32, i = 0
            // endereco = 32 + 0 = 32 -- S/frame começa no 0
            cache[i] = ram.Read((s*capacidade) + i);
        }
    }
}
