package br.com.yan.automato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* - instalar no pc: sdkman, java 21 oracle, instalar maven, sdk list java, sdk instal java "identfier", sdk list maven, sdk install maven "version"
 * instalar intellij
* linha de comando dps de tudo instalado: mvn spring-boot:run (para rodar  back terminal)
*
 * FRONT
 * instalar http server npm
 * instalar npm (node)
 * http-server -p 9090 -- para rodar
 * no navegador, porta 9090 para rodar o front
*
*
* */
@SpringBootApplication
public class GeradorDeAutomatoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeradorDeAutomatoApplication.class, args);
	}

}
