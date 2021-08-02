package com.lagm.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PreTiempoTranscurridoFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(PreTiempoTranscurridoFilter.class);
	
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
		log.info(String.format("%s request enrutado a %s", request.getMethod(), request.getRequestURL().toString()));
		
		// Pasamos datos al request: El tiempo inicial
		Long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		return null;
	}

	// 1. Define el tipo de filtro
	@Override
	public String filterType() { // pre, post, route
		// pre: Antes de que se resuelva la ruta, y antes de la comunicación con el microservicio
		return "pre";
	}

	// 2. Define el orden
	@Override
	public int filterOrder() {
		return 1;
	}
	
}
