

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;


public class GeneratorSqlmap {

	public void generator() throws Exception{
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File(System.getProperty("user.dir")+"/src/main/resources/generatorConfig.xml"); 
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		deleteXmlFiles(config);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
				callback, warnings);
		myBatisGenerator.generate(null);
	} 
	
	/** main 方法
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		try {
			GeneratorSqlmap generatorSqlmap = new GeneratorSqlmap();
			generatorSqlmap.generator();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	String dir;
	public void deleteXmlFiles(Configuration config) {
		Context context = config.getContext("testTables");
		SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = context.getSqlMapGeneratorConfiguration();
		String targetPackage = sqlMapGeneratorConfiguration.getTargetPackage();
		String targetProject = sqlMapGeneratorConfiguration.getTargetProject();
		dir = System.getProperty("user.dir")+ targetProject+"/"+targetPackage.replaceAll("\\.", "/");
		
		List<TableConfiguration> tableConfigurations = context.getTableConfigurations();
		List list = new ArrayList();
		for (TableConfiguration tc : tableConfigurations) {
			String domainObjectName = tc.getDomainObjectName();
			list.add(domainObjectName);
		}
//		Dom4jUtil.deleteXMLMapper(dir,list);
	}

}
