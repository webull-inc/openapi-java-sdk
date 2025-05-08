package com.webull.openapi.example.trade;

import com.google.common.reflect.TypeToken;
import com.webull.openapi.common.CustomerType;
import com.webull.openapi.example.config.Env;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.serialize.JsonSerializer;
import com.webull.openapi.trade.events.subscribe.EventClient;
import com.webull.openapi.trade.events.subscribe.Subscription;
import com.webull.openapi.trade.events.subscribe.message.EventType;
import com.webull.openapi.trade.events.subscribe.message.SubscribeRequest;
import com.webull.openapi.trade.events.subscribe.message.SubscribeResponse;

import java.util.Map;

public class TradeEvents {

    private static final Logger logger = LoggerFactory.getLogger(TradeEvents.class);

    public static void main(String[] args) {
        try (EventClient client = EventClient.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
                .regionId(Env.REGION_ID)
//                .customerType(CustomerType.INSTITUTION)
                .onMessage(TradeEvents::handleEventMessage)
                .build()) {

            SubscribeRequest request = new SubscribeRequest("<your_account_id>");

            Subscription subscription = client.subscribe(request);
            subscription.blockingAwait();

        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }

    private static void handleEventMessage(SubscribeResponse response) {
        if (SubscribeResponse.CONTENT_TYPE_JSON.equals(response.getContentType())) {
            Map<String, String> payload = JsonSerializer.fromJson(response.getPayload(),
                    new TypeToken<Map<String, String>>(){}.getType());
            if (EventType.Order.getCode() == response.getEventType() || EventType.Position.getCode() == response.getEventType()) {
                logger.info("----request_id:{}----", payload.get("request_id"));
                logger.info(payload.get("account_id"));
                logger.info(payload.get("client_order_id"));
                logger.info(payload.get("order_status"));
            }
        }
    }
}
