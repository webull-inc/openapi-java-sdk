package com.webull.openapi.example.trade;

import com.webull.openapi.example.config.Env;
import com.webull.openapi.example.trade.HoldingAction.ActionType;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.http.HttpApiConfig;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.trade.api.TradeApiService;
import com.webull.openapi.trade.api.http.TradeHttpApiService;
import com.webull.openapi.trade.api.response.*;
//import com.webull.openapi.utils.CollectionUtils;
//import com.webull.openapi.utils.StringUtils;

//import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TradeApi {

    private static final Logger logger = LoggerFactory.getLogger(TradeApi.class);

    public static void main(String[] args) {
        String acctNum = "";
        String acctId = "";
        int numPositions = 0;
        List<HoldingAction> holdingActions = new ArrayList<>();

        try {
            HttpApiConfig apiConfig = HttpApiConfig.builder()
                    .appKey(Env.APP_KEY)
                    .appSecret(Env.APP_SECRET)
                    .regionId(Env.REGION_ID)
                    .build();
            TradeApiService apiService = new TradeHttpApiService(apiConfig);

            // get account list
            List<Account> accounts = apiService.getAccountList("");
            

            for (Account account : accounts) {
                acctNum = account.getAccountNumber();
                if (acctNum.equals(Env.ACCOUNT)) {
                    //Get account ID
                    acctId = account.getAccountId();

                    //Use AccoutnID to get balance information
                    BalanceBase acctBalance = apiService.getAccountBalance(acctId, "");
                    String totalMarketValue = acctBalance.getTotalMarketValue();
                    String totalCashBalance = getStringValue(acctBalance, "getTotalCashBalance");

                    //Get Total Account Value
                    double TAV = 0;
                    TAV = GetTotalAccountValue(totalMarketValue, totalCashBalance);

                    //Get and count existing positions
                    AccountPositions acctPositions = apiService.getAccountPositions(acctId,100, "");
                    List<HoldingInfo> holdings = acctPositions.getHoldings();
                    numPositions = holdings.toArray().length;
                    //System.out.println("Account positions: " + numPositions);

                    //Get count of positions to open
                    String strOpen = System.console().readLine("Enter # of positions to OPEN: ");
                    double numOpen = StringToDouble(strOpen);

                    //Get count of positions to close
                    String strClose = System.console().readLine("Enter # of positions to CLOSE: ");
                    double numClose = StringToDouble(strClose);

                    double positionSize = TAV / (numPositions + numOpen - numClose);
                    System.out.println("Position Size: $" + positionSize);

                    //Get holding actions
                    holdingActions = DetermineHoldingActions(holdings, positionSize);
                }
            }
            
            //Sort by sell first, then buy prior to attempting to execute
            holdingActions.sort((a, b) -> b.getAction().compareTo(a.getAction()));

            //loop through actions for printout - DEBUG ONLY
            for (HoldingAction action : holdingActions) {
                System.out.println("Ticker: " + action.getTicker() + " " + action.getAction() + " " + action.getShares());
            }
        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }

    private static List<HoldingAction> DetermineHoldingActions(List<HoldingInfo> holdings, double positionSize) {
        //Use to get a list of all holding actions
        List<HoldingAction> actionsList = new ArrayList<>();
        HoldingAction action;
        double shareChange;
        String ticker;

        //Loop through positions and determine next steps
        for (HoldingInfo holding : holdings) {
            //Determine holding action
            shareChange = DetermineHoldingAction(holding, positionSize);
            ticker = holding.getSymbol();
            action = null; //reset

            if (shareChange>0) {
                action = new HoldingAction(ticker, shareChange, ActionType.BUY);
                //System.out.println("Buy " + shareChange + ticker);

                //Add the action to the list
                actionsList.add(action);
            }
            else if (shareChange<0){
                //change negative number to positive while setting action type to sell
                action = new HoldingAction(ticker, shareChange*-1, ActionType.SELL);

                //Add the action to the list
                actionsList.add(action);
            }
        }

        return actionsList;
    }

    private static double DetermineHoldingAction(HoldingInfo holding, double positionSize) {
        double buySell = 0;
        String strLastPrice = holding.getLastPrice();
        String strCurHoldingSize = holding.getMarketValue();
        double curHoldingSize = StringToDouble(strCurHoldingSize);
        double lastPrice = StringToDouble(strLastPrice);
        double workingVal = (positionSize-curHoldingSize)/lastPrice;

        if (positionSize-curHoldingSize>0) {
            buySell = roundDown(workingVal, 0); //numToBuy
            //System.out.println("Buy: " + buySell);
        }
        else{
            buySell = roundDown(workingVal, 0) + 1; //numToSell
            //System.out.println("Sell: " + buySell);
        }

        return buySell;
    }

    private static Double GetTotalAccountValue(String totalMarketValue, String totalCashBalance) {
        double tav = 0;
        
        double mrktVal = StringToDouble(totalMarketValue);
        double ttlCash = StringToDouble(totalCashBalance);
        
        tav = mrktVal + ttlCash;

        return tav;
    }

    private static Double StringToDouble(String val) {
        double cur = 0;
        if (val != null && !val.trim().isEmpty()) {
            try {
                cur = Double.parseDouble(val.trim());
                return cur;
            } catch (NumberFormatException e) {
                System.err.println("❌ Failed to convert cash string to number: " + val);
            }
        } 
        return cur;
    }

    private static double roundDown(double value, int decimalPlaces) {
        if (decimalPlaces < 0) throw new IllegalArgumentException("Decimal places must be >= 0");
        
        BigDecimal bd = new BigDecimal(Double.toString(value));
        return bd.setScale(decimalPlaces, RoundingMode.DOWN).doubleValue();
    }

    private static String getStringValue(Object obj, String methodName) {
        try {
            java.lang.reflect.Method method = obj.getClass().getMethod(methodName);
            Object result = method.invoke(obj);
            return result == null ? "null" : result.toString();
        } catch (Exception e) {
            return "No such method: " + methodName;
        }
    }
}
