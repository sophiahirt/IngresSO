package dao;

public class BancoException extends RuntimeException {

    public BancoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
