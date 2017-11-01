

import org.automation.model.Batch;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.turqoise.automation.dataaccess.DataRepository;
import com.turqoise.automation.dataaccess.DataRepositoryImpl;

public class TestData implements CommandLineRunner{

	public void run(String... arg0) throws Exception {
		DataRepository<Batch> batchRepo = new DataRepositoryImpl<Batch>();
		Query q  = new Query();
		q.addCriteria(Criteria.where("clientId").is("1234"));
		System.out.println(batchRepo.findById("59ee24757cd4d403b405db8e", Batch.class));
		Batch b = new Batch();
		b.setClientId("Kaarthikeyan");
		//batchRepo.
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TestData.class, args);
	}
	
	

}
