package bo;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBoImpl;
import bo.custom.impl.OrderBOImpl;
import bo.custom.impl.OrderDetailsBoImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        if(boFactory == null){
            boFactory= new BOFactory();
        }
        return boFactory;
    }

    public SuperBo getBO(BoTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBoImpl();
            case ORDER:
                return new OrderBOImpl();
            case ORDER_DETAILS:
                return new OrderDetailsBoImpl();
            default:
                return null;
        }
    }
   public enum  BoTypes{
       CUSTOMER,ITEM,ORDER,ORDER_DETAILS
   }


}
