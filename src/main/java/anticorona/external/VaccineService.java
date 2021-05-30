
package anticorona.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="vaccine", url="http://vaccine:8080")
public interface VaccineService {

    @RequestMapping(method= RequestMethod.POST, path="/vaccines")
    public Boolean chkAndModifyStock(@RequestBody Vaccine vaccine);

}