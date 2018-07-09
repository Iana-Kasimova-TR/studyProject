import entities.DailyAgenda;
import org.junit.Test;
import services.DailyAgendaService;
import services.DailyAgendaServiceImpl;

/**
 * Created by Iana_Kasimova on 09-Jul-18.
 */
public class DailyAgendaserviceTest {

    @Test
    public void createDailyAgendaTest(){

        DailyAgendaService agendaService = new DailyAgendaServiceImpl();
        DailyAgenda agenda = agendaService.createDailyAgenda();
    }

}
