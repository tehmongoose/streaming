import com.efx.pet.utility.PetEnvPropertyConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.IOException;


@ContextConfiguration(classes = {PetEnvPropertyConfiguration.class, PropertyTest.Config.class})
@RunWith(SpringRunner.class)
public class PropertyTest {

    @Configuration
    public static class Config {
        @Bean
        public DataSource dataSource() {

            // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            EmbeddedDatabase db = builder
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("ddl/h2/schema.sql")
                    .addScript("dml/h2/data.sql")
                    .build();
            return db;
        }
    }


    @Autowired
    DataSource dataSource;

    @Autowired
    Environment environment;

    @Value("${canHasDataSource}")
    String canHasDataSource;

    @Test
    public void test() throws IOException {
        System.out.println("canHasDataSource:" + this.canHasDataSource);
    }

}
