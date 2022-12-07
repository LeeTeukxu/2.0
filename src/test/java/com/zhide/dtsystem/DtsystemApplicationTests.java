package com.zhide.dtsystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DtsystemApplicationTests {

	@Test
	public void contextLoads() {
		Integer a=Integer.parseInt(Boolean.TRUE.toString());
		System.out.println(a);
	}

}
