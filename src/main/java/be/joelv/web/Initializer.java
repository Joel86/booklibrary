package be.joelv.web;

import javax.servlet.Filter;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import be.joelv.datasource.DataSourceConfig;
import be.joelv.repositories.RepositoriesConfig;
import be.joelv.restclients.RestClientsConfig;
import be.joelv.security.SecurityConfig;
import be.joelv.services.ServicesConfig;

public class Initializer 
	extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {DataSourceConfig.class, RepositoriesConfig.class, 
			ServicesConfig.class, RestClientsConfig.class, SecurityConfig.class};
	}
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {ControllersConfig.class};
	}
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {new OpenEntityManagerInViewFilter()};
	}
}
