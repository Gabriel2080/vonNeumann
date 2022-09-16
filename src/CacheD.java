public class CacheD extends Memoria {
    private RAM ram;
    private int inicioDaCache = Integer.MIN_VALUE;
    private boolean modificacao = false;
    private int[] cache;

    public CacheD(int tam, RAM ram) {
        super(tam);
        this.ram = ram;
        this.cache = new int[tam];
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


}
