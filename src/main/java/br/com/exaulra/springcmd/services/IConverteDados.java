package br.com.exaulra.springcmd.services;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}