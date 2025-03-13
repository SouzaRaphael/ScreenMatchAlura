package br.com.exaulra.ScreenMatch.services;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}