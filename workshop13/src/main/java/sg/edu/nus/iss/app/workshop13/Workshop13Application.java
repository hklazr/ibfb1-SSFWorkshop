package sg.edu.nus.iss.app.workshop13;

import java.util.List;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static sg.edu.nus.iss.app.workshop13.util.IOUtil.*;

@SpringBootApplication
public class Workshop13Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Workshop13Application.class);
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		List<String> opsVal  = appArgs.getOptionValues("dataDir");
		System.out.println("opsVal" +opsVal);
		if(null != opsVal) { 
			createDir((String)opsVal.get(0));
		}else{
			System.exit(1);
		}
		app.run(args);
	}

}
