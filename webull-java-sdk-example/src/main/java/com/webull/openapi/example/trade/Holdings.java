package com.webull.openapi.example.trade;

import com.webull.openapi.trade.api.response.HoldingInfo;

public class Holdings {
    private Holdings() {
    }

    //Move to new holdings class
    public static double DetermineHoldingAction(HoldingInfo holding, double positionSize) {
        double buySell = 0;
        String strLastPrice = holding.getLastPrice();
        String strCurHoldingSize = holding.getMarketValue();
        double curHoldingSize = Utils.StringToDouble(strCurHoldingSize);
        double lastPrice = Utils.StringToDouble(strLastPrice);
        double workingVal = (positionSize-curHoldingSize)/lastPrice;

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
