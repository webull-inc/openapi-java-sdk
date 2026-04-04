package com.webull.openapi.example.trade;

import com.webull.openapi.trade.api.response.HoldingInfo;

public class Holdings {
    private Holdings() {
    }

    //Move to new holdings class
    public static double DetermineHoldingAction(HoldingInfo holding, double positionSize) {
        double buySell = 0;
        String strLastPrice;
        String strCurHoldingSize;
        double curHoldingSize = 0;
        double lastPrice = 0;
        double workingVal = 0;

        try{
            strLastPrice = holding.getLastPrice();
            strCurHoldingSize = holding.getMarketValue();
            curHoldingSize = Utils.StringToDouble(strCurHoldingSize);
            lastPrice = Utils.StringToDouble(strLastPrice);
            workingVal = (positionSize-curHoldingSize)/lastPrice;
        }
        catch (Exception ex) {
            System.out.print(ex);
        }

        if (positionSize-curHoldingSize>0) {
            buySell = Utils.RoundDown(workingVal, 0); //numToBuy
            //System.out.println("Buy: " + buySell);
        }
        else{
            buySell = Utils.RoundDown(workingVal, 0) - 1; //numToSell
            //System.out.println("Sell: " + buySell);
        }

        return buySell;
    }
}
