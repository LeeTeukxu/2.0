
package com.zhide.dtsystem;

import com.aspose.words.License;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@EnableCaching
@SpringBootApplication()
@MapperScan(basePackages = "com.zhide.dtsystem.mapper")
@EnableJpaRepositories(basePackages = "com.zhide.dtsystem.repositorys")
@EnableJpaAuditing
public class DtsystemApplication  extends SpringBootServletInitializer  {
	public static void main(String[] args) {
		getLicense();
		SpringApplication.run(DtsystemApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		getLicense();
		return builder.sources(DtsystemApplication.class);
	}
	protected static boolean getLicense(){
		boolean result = false;
		try {

			System.out.println("开始破解........");
			InputStream is = DtsystemApplication.class.getClassLoader().getResourceAsStream("license.xml");
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result =aposeLic.getIsLicensed();
			System.out.println("破解完成........,结果:"+Boolean.toString(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
