package com.webull.openapi.example.trade;

import com.webull.openapi.trade.api.response.HoldingInfo;

public class Holding {
    private double buySell;
    private String strCurHoldingSize;
    private double curHoldingSize = 0;
    private double lastPrice = 0;
    

    public  Holding() {
    }

    public Holding(HoldingInfo holdingInfo) {
        try{
            String strLastPrice = holdingInfo.getLastPrice();
            this.strCurHoldingSize = holdingInfo.getMarketValue();
            this.curHoldingSize = Utils.StringToDouble(strCurHoldingSize);
            this.lastPrice = Utils.StringToDouble(strLastPrice);
        }
        catch (Exception ex) {
            System.out.print(ex);
        }
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public double getLastPrice() {
        return this.lastPrice;
    }

    public void setHoldingSize(double holdingSize) {
        this.curHoldingSize = holdingSize;
    }

    public double getCurrentHoldingSize() {
        return this.curHoldingSize;
    }

    //Move to new holdings class
    public double DetermineAction(double positionSize) {
        double workingVal = 0;

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
