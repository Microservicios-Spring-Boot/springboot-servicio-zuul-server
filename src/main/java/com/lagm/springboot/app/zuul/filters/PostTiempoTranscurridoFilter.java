package com.lagm.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);
	
	// 4. Método para validar si se ejecuta o no el filtro (método run)
	@Override
	public boolean shouldFilter() {
		/* En este caso podríamos validar:
		 * 	- si existe algún parámetro en la ruta: Request param
		 *  - si el usuario está autenticado
		 *  - etc. */
		return true; // Se define que se ejecute siempre en cada request
	}

	// 3. Aquí se resuelve la lógica de nuestro filtro
	@Override
	public Object run() throws ZuulException {
		// Obtenemos el objeto request
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info("Entrando a post filter");
		
		// Se obtiene el tiempoInicio del request
		Long tiempoInicio = (Long)request.getAttribute("tiempoInicio");
		Long tiempoFinal = System.currentTimeMillis();
		Long tiempoTranscurrido = tiempoFinal - tiempoInicio;
		
		log.info(String.format("Tiempo transcurrido en segundos: %s seg.", tiempoTranscurrido.doubleValue() / 1000.00));
		log.info(String.format("Tiempo transcurrido en segundos: %s ms.", tiempoTranscurrido.doubleValue()));
		
		return null;
	}

	// 1. Define el tipo de filtro
	@Override
	public String filterType() { // Palabras clave: pre, post, route, error
		return "post";
	}

	// 2. Define el orden
	@Override
	public int filterOrder() {
		return 1;
	}
	
}
