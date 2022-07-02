package bo.custom;



import bo.SuperBo;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public interface OrderDetailsBO  extends SuperBo {
    JsonArrayBuilder getAllOrderDetails() throws SQLException;

}
