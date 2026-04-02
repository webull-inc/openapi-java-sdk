package com.webull.openapi.example.trade;

import com.webull.openapi.example.config.Acct;
import com.webull.openapi.example.config.Env;
import com.webull.openapi.example.config.TradingAccount;
import com.webull.openapi.example.trade.HoldingAction.ActionType;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.http.HttpApiConfig;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.trade.api.TradeApiService;
import com.webull.openapi.trade.api.http.TradeHttpApiService;
import com.webull.openapi.trade.api.request.StockOrder;
import com.webull.openapi.trade.api.response.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Rebalancer {

    private static final Logger logger = LoggerFactory.getLogger(Rebalancer.class);

    public static void main(String[] args) {
        String acctNum = "";
        String acctId = "";
        int numPositions = 0;
        boolean actFound = false;

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

            //Determine which account the user wants to work on
            String desiredAcct = GetDesiredAccount(accounts);
                        
                //Loop through the accounts
                for (Account account : accounts) {
                    acctNum = account.getAccountNumber();
                            
                    //Get account ID
                    acctId = account.getAccountId();
            
                    if (acctNum.equals(desiredAcct)) {
                        actFound = true;

                        //Get account information
                        BalanceBase acctBalance = apiService.getAccountBalance(acctId, "");
                        String totalMarketValue = acctBalance.getTotalMarketValue();
                        String totalCashBalance = Utils.GetStringValue(acctBalance, "getTotalCashBalance");
                        double TAV = Utils.GetTotalAccountValue(totalMarketValue, totalCashBalance);
            
                        //Get and count existing positions
                        AccountPositions acctPositions = apiService.getAccountPositions(acctId,100, "");
                        List<HoldingInfo> holdings = acctPositions.getHoldings();
                        numPositions = holdings.toArray().length;
                        
                        //Get count of positions to open and close
                        System.out.println("For account: " + acctNum +"(" + Acct.GetFriendlyName(acctNum) + ")"); //TODO getAccountType is null

                                
                        List<HoldingAction> actions = GetPositionChanges();
            
                        for (HoldingAction act : actions) {
                            if (act.isBuy()) {
                                numPositions += 1;
                            }
                            else if (act.isSell()) {
                                numPositions -= 1;
                            }
                        }
            
                        double positionSize = TAV / (numPositions);
                        System.out.printf("Total Act Val:  $%8.2f\n", TAV);
                        System.out.println("# of Positions: " + numPositions);
                        System.out.printf("Position Size:  $%8.2f\n\n", positionSize);
            
                        //Get holding actions
                        holdingActions = DetermineHoldingActions(holdings, positionSize);
                    }
                }

                if (!actFound) {
                    System.out.println("Account not found!");
                }
                        
                //Sort by sell first, then buy prior to attempting to execute
                //holdingActions.sort((a, b) -> b.getAction().compareTo(a.getAction()));
                holdingActions.sort(
                Comparator.comparing(HoldingAction::getAction).thenComparing(HoldingAction::getTicker));
            
                //loop through actions for printout - DEBUG ONLY
                for (HoldingAction action : holdingActions) {
                    System.out.println(action.toString());
                }
            
                        //Execute trades
                        // After your rebalancing calculations are done...
                        // System.out.println("=== EXECUTING REBALANCE TRADES ===");
            
                        // for (HoldingAction action : holdingActions) {
                        //     // Optional safety pause / confirmation
                        //     System.out.print("Execute " + action + "? (y/n): ");
                        //     String confirm = scanner.nextLine().trim().toLowerCase();
                        //     if (confirm.equals("y")) {
                        //         executeRebalanceTrade(apiService, selectedAccountId, action);
                        //         Thread.sleep(1200); // respect rate limit (~1 order per second)
                        //     }
                        // }
            
            } catch (ClientException ex) {
                logger.error("Client error", ex);
            } catch (ServerException ex) {
                logger.error("Sever error", ex);
            } catch (Exception ex) {
                logger.error("Unknown error", ex);
            }
        }
            

        //TODO: Make it actually show the accounts and have the user select 
        private static String GetDesiredAccount(List<Account> accts) {
            String acctId;
            int counter = 1;
            TradingAccount tradingAccount;
            String acctName;

            //build account list
            List<TradingAccount> accountList = new ArrayList<TradingAccount>();

            System.out.println("Please select which account you want to work on:");

            //Show a list of all accounts for the user to pick from
            for (Account acct : accts) {
                acctId = acct.getAccountId();
                acctName = Acct.GetFriendlyName(acct.getAccountNumber());
                tradingAccount = new TradingAccount(acctName, acctId, acct.getAccountNumber(), counter);
                
                //Add to account list for lookup
                accountList.add(tradingAccount);

                //Display options for user
                System.out.println(counter + " - " + acctName);

                //Increase ID
                counter++;
            }

            //Read users account selection
            //scanner.nextLine().trim().toLowerCase();
            String selected = System.console().readLine("Type the number you want:");
            int intSelected = 0;

            try{
                intSelected = Integer.parseInt(selected);

                //Get the account code and save to acctId
                for (TradingAccount act : accountList) {
                    if (act.getCode() == intSelected) {
                        System.out.println("HERE: " + act.getAccountNumber());
                        
                        acctId = act.getAccountNumber();
                    }
                }
            }
            finally {
                //start static
                acctId = "CVT5UWB8";
            }
                    
            return acctId;
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
            shareChange = Holdings.DetermineHoldingAction(holding, positionSize);
            ticker = holding.getSymbol();
            action = null;

            if (shareChange>0) {
                action = new HoldingAction(ticker, shareChange, ActionType.BUY);
                actionsList.add(action);
            }
            else if (shareChange<0){
                //change negative number to positive while setting action type to sell
                action = new HoldingAction(ticker, shareChange*-1, ActionType.SELL);
                actionsList.add(action);
            }
        }

        return actionsList;
    }

    /**
     * User specifies which tickers to sell and which to buy.
     * The app calculates the actual number of shares needed for rebalancing.
     */
    private static List<HoldingAction> GetPositionChanges() {
        List<HoldingAction> actions = new ArrayList<>();
        HoldingAction act;

        // Step 1: Tickers to SELL (reduce or exit)
        System.out.println("Enter tickers you want to SELL (one per line, type 'done' when finished):");
        while (true) {
            System.out.print("Sell Ticker: ");
            String ticker = System.console().readLine("Ticker to sell (DONE to exit): ").trim().toUpperCase();

            if (ticker.equals("DONE") || ticker.isEmpty()) {
                break;
            }

            act = new HoldingAction(ticker, ActionType.SELL);
            actions.add(act);

            //TODO: Add logic to validate this is a valid position to remove

            // // Here you will calculate how many shares to sell based on your rebalancing logic
            // double sharesToSell = calculateSharesToSell(ticker, currentHoldings, totalPortfolioValue);

            // if (sharesToSell > 0) {
            //     actions.add(new HoldingAction(ticker, sharesToSell, HoldingAction.ActionType.SELL));
            //     System.out.printf("✓ Will SELL %.2f shares of %s%n", sharesToSell, ticker);
            // } else {
            //     System.out.println("No shares to sell for " + ticker);
            // }
        }

        System.out.println();

        // Step 2: Tickers to BUY (increase or add)
        System.out.println("Enter tickers you want to BUY (one per line, type 'done' when finished):");
        while (true) {
            System.out.print("Buy Ticker: ");
            String ticker = System.console().readLine("Ticker to buy (DONE to exit): ").trim().toUpperCase(); //scanner.nextLine().trim().toUpperCase();

            if (ticker.equals("DONE") || ticker.isEmpty()) {
                break;
            }

            act = new HoldingAction(ticker, ActionType.BUY);
            actions.add(act);
        }

        return actions;
    }

    /**
     * Executes a single rebalance trade using Webull's official Java SDK.
     * 
     * This is a MARKET order by default (safest for rebalancing).
     * You can easily change to LIMIT if you prefer price control.
     * 
     * IMPORTANT SAFETY NOTES:
     * 1. Test this in a Webull Paper Trading account first!
     * 2. Add a user confirmation prompt before calling this in production.
     * 3. Webull rate-limits order placement (max ~1 per second per app key).
     * 4. Only whole shares are supported for most stocks.
     */
    public void executeRebalanceTrade(TradeApiService apiService, 
                                    String accountId, 
                                    HoldingAction action) {

        System.out.println("Placing order: " + action);

        try {
            // 1. Generate a unique client order ID (required by Webull)
            String clientOrderId = "REBAL_" + action.getTicker() + "_" + System.currentTimeMillis();

            // 2. Determine side
            String side = action.isBuy() ? "BUY" : "SELL";

            // 3. Build the stock order (this is the model used by the SDK)
            //    Check your SDK version — the class is usually StockOrder or NewStockOrder
            StockOrder stockOrder = new StockOrder();          // ← confirm exact class name in your SDK
            stockOrder.setClientOrderId(clientOrderId);
            stockOrder.setSide(side);
            stockOrder.setOrderType("MARKET");                 // or "LIMIT" if you want to set a price
            stockOrder.setTif("DAY");                          // Day order (most common)
            stockOrder.setQty(String.valueOf(Math.floor(action.getShares()))); // whole shares only
            stockOrder.setExtendedHoursTrading(false);

            // IMPORTANT: Webull requires instrument_id (not just ticker)
            // You have two options:
            //    A) Fetch it once per ticker (recommended — cache it)
            //    B) Use a helper method you already have
            String instrumentId = ""; //getInstrumentId(action.getTicker());   // ← YOU MUST IMPLEMENT THIS
            try {
                instrumentId = apiService.getTradeInstrument(action.getTicker()).getInstrumentId(); //.getInstrument(action.getTicker());   // single ticker
            } catch (Exception ignored) {}
            stockOrder.setInstrumentId(instrumentId);

            // 4. Place the order via the SDK
            //    The exact method name in your SDK version is usually one of these:
            //    placeOrder, placeStockOrder, submitOrder, or order.placeOrder
            OrderResponse response = apiService.placeOrder(accountId, stockOrder);
            // Alternative names you might see:
            // PlaceOrderResponse response = apiService.placeStockOrder(accountId, stockOrder);

            if (response != null && response.getOrderId() != null) {
                System.out.println("✅ SUCCESS — Order placed!");
                System.out.println("   Order ID     : " + response.getOrderId());
                System.out.println("   Client Order ID : " + clientOrderId);
                System.out.println("   Ticker       : " + action.getTicker());
                System.out.println("   Shares       : " + action.getShares());
                System.out.println("   Side         : " + side);
            } else {
                System.err.println("⚠️ Order placed but response was empty.");
            }

        } catch (Exception e) {
            System.err.println("❌ FAILED to place order for " + action.getTicker());
            System.err.println("   Reason: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
