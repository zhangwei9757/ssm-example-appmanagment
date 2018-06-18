package cn.appsys.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MyBatisTest {

	@Test
	public void testMbg() throws Exception {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File("resources/mbg.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
				callback, warnings);
		myBatisGenerator.generate(null);
	}

	@Test
	public void snakeSurround() {
		int row_ = 10, col_ = 10;// 10行10列
		int row = row_, col = col_;// 记录还剩余的行与列数
		int index = 1;// 数字
		int x = 0;// 行
		int y = 1;// 列
		int[][] snake = new int[row_][col_];// 10行10列的二维数组

		while (index <= (row_ * col_)) {
			for (int i = 0; i < col; i++) {
				y += -1;
				snake[Math.abs(x)][Math.abs(y)] = index++;
			}
			col--;
			row--;
			for (int i = 0; i < row; i++) {
				x += -1;
				snake[Math.abs(x)][Math.abs(y)] = index++;
			}
			x = -x;
			y = -y;
		}

		this.print(snake);
	}

	public void print(int[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
