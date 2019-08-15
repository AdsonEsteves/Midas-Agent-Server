/**
 * Created on 28/12/2004
 * @author Aluizio Haendchen Filho
 */

package org.midas.as.catalog;

import java.util.Map;

import org.midas.metainfo.ContainerInfo;
import org.midas.metainfo.EntityInfo;
import org.midas.metainfo.MetaInfoException;
import org.midas.metainfo.OrganizationInfo;
import org.midas.metainfo.ServiceInfo;



public class Catalog 
{
	// TODO: Verificar Poss�vel uso de Exce��es Personalizadas nos Gets
		
	// Vari�vel que guarda os Meta Dados de um Agent Server
	private static ContainerInfo containerInfo;
		
	// HashMap que serve de ind�ce para os data sources
	// Key   = Nome do data source
	// Value = Array de strings com o driver (1) e a conex�o (2) 
	private static Map<String,String[]> dataSources;
		
	// HashMap que serve de �ndice para as organiza��es que o AS hospeda
	// Key   = Nome do servi�o
	// Value = Objeto do servi�o
	private static Map<String,ServiceInfo> services;
	
	/**
	 * Obt�m a string do driver pelo nome do Data Source
	 * @param dataSourceName - nome do Data Source
	 * @return string do driver
	 */
	public static String getDatabaseDriver(String source) throws CatalogException
	{
		return ( (Catalog.getDataSourceInfo(source))[0] );	
	}
	
	
	/**
	 * Obt�m a string de conex�o pelo nome do Data Source
	 * @param dataSourceName - nome do Data Source
	 * @return string de conex�o
	 */	
	public static String getDatabaseConnectionString(String source) throws CatalogException
	{
		return ( (Catalog.getDataSourceInfo(source))[1] );
	}
	
	/**
	 * Obt�m as strings de driver e conex�o pelo nome do Data Source
	 * @param dataSourceName - nome do Data Source
	 * @return strings de driver e conex�o
	 */
	private static String[] getDataSourceInfo(String dataSourceName) throws CatalogException
	{	
		String[] dataSourceInfo = dataSources.get(dataSourceName);
		
		if (dataSourceInfo == null)
		{
			throw new CatalogException ("Invalid datasource name");
		}
		else
		{
			return (dataSourceInfo);
		}
	}	
		
	public static String getServicePath(String organizationName,String serviceName) 
		throws CatalogException
	{
		return (getServiceByName(organizationName,serviceName)).getPath();
	}		
	
	public static OrganizationInfo getOrganizationByName(String organizationName) 
		throws MetaInfoException
	{
		return (containerInfo.getOrganizationByName(organizationName));
	}
	
	public static EntityInfo getEntityByName(String organizationName,String entityName) 
		throws MetaInfoException 
	{
		return (containerInfo.getOrganizationByName(organizationName).getEntityByName(entityName));
	}
	
	public static EntityInfo getEntityByService(String organizationName,String serviceName) 
		throws CatalogException
	{
		return (getServiceByName(organizationName,serviceName)).getEntity();								
	}	
	
	public static boolean hasService(String organizationName,String serviceName) 
	{
		if (services.containsKey(organizationName+"."+serviceName))
		{
			return true;
		}
		
		return false;	
	}
	
	public static ServiceInfo getServiceByName (String organizationName,String serviceName)	throws CatalogException
	{
		if (hasService(organizationName,serviceName))
		{
			return services.get(organizationName+"."+serviceName);
		}
		else
		{
			throw new CatalogException("Invalid service - " + serviceName + " - for organization - " + organizationName + " -");
		}		
	}

	public static void loadCatalog() throws CatalogException
	{
		try
		{
			Parser.parse();				
		}
		catch(ParserException e)
		{
			throw new CatalogException("Unable to parse xml files",e);
		}		
	}

	// Getters & Setters...		
	public static ContainerInfo getContainerInfo()
	{
		return containerInfo;
	}
	
	public static void setContainerInfo(ContainerInfo asInfo)
	{
		Catalog.containerInfo = asInfo;
	}
	
	public static Map<String,ServiceInfo> getServices() 
	{
		return services;
	}
	
	public static void setServices(Map<String,ServiceInfo> services) 
	{
		Catalog.services = services;
	}
	
	public static Map<String,String[]> getDataSources() 
	{
		return dataSources;
	}
	
	public static void setDataSources(Map<String,String[]> dataSources) 
	{
		Catalog.dataSources = dataSources;
	}
}
